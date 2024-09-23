package map;

import Robot.*;
import java.util.*;

import enumerator.*;

public class AStar {
    private static final Direction[] DIRECTIONS = {Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.NORTH}; 
    public List<Direction> listDirection = new ArrayList<>();

    private List<Direction> finalListDirection = new ArrayList<>();
    /**
     * A* search algorithm to find the shortest path from the robot to the end box
     * @param grid that represents the map using Box objects
     * @param currentRobot the robot that will be moving on the map, realy useful to get the speed of the robot and the spec
     * @param endBox the box that the robot wants to reach
     * @return a list of boxes that represents the path from the robot to the end box
     */
    public List<Box> aStarSearch(Box[][] grid, Robot currentRobot, Box endBox) {

        for (Box[] boxes : grid) {
            for (Box box : boxes) {
                box.resetCosts();
            }
        }
        clearListDirection();

        PriorityQueue<Box> openList = new PriorityQueue<>(Comparator.comparingDouble(box -> box.getFCost()));
        Set<Box> closedList = new HashSet<>();

        Box startBox = currentRobot.getPositionRobot();
        if (currentRobot.getSpecialSpeed(startBox.getNature()) == 0) {
            return Collections.emptyList();
        }

        // Set up start and end boxes
        startBox.calculateCosts(startBox, endBox, currentRobot);
        openList.add(startBox);

        while (!openList.isEmpty()) {
            Box current = openList.poll();

            if (current.equals(endBox) || (!currentRobot.getType().equals("Drone") && current.distanceTo(endBox) == 1)) {
                return reconstructPath(current);
            }

            closedList.add(current);

            for (Direction direction : DIRECTIONS) {
                int newX = current.getRow() + direction.getX();
                int newY = current.getColumn() + direction.getY();
                
                if (isValidMove(grid, newX, newY, currentRobot)) {
                    Box neighbor = grid[newX][newY];
                    
                    if (!closedList.contains(neighbor)) {
                        double tentativeGCost = current.getGCost() + currentRobot.getSpecialSpeed(neighbor.getNature());
                        if (tentativeGCost < neighbor.getGCost()) {
                            neighbor.setParent(current);
                            neighbor.calculateCosts(startBox, endBox, currentRobot);
                            if (!openList.contains(neighbor)) {
                                openList.add(neighbor);
                            }
                        }
                    }
                }
            }
        }
        
        return Collections.emptyList(); // Return an empty list if no path is found
    }

    private List<Box> reconstructPath(Box end) {
        List<Box> path = new ArrayList<>();
        Box current = end;
        while (current != null) {
            path.add(current);
            if (current.getParent() != null) {
                int[] diff = current.compareBox(current.getParent());
                Direction type = Direction.foundTypeFromDiff(diff);
                listDirection.add(type);
            }
            current = current.getParent();
        }
        Collections.reverse(path);
        Collections.reverse(listDirection);
        return path;
    }

    private boolean isValidMove(Box[][] grid, int x, int y, Robot robot) {
        return x >= 0 && y >= 0 && x < grid.length && y < grid[0].length && robot.getSpecialSpeed(grid[x][y].getNature()) != 0;
    }

    public List<Direction> getListDirection() {
        return this.listDirection;
    }

    public void clearListDirection() {
        this.listDirection.clear();
    }

    public void setFinalListDirection(List<Direction> d){
        List <Direction> res =new ArrayList<>();
        for (Direction elem : d){
            res.add(elem);
        }
        this.finalListDirection = res;
    }
    public String showInfo(List<Box> path)
    {
        String info = "";
        if (path.isEmpty()) {
            info += "No path found.";
        } else {
            info += "Path found:" + this.finalListDirection + "\n\t* ";
            double finalCost = path.get(path.size() - 1).getFCost();
            for (Box node : path) {
                info += "(" + node.getRow() + ", " + node.getColumn() + ") -> ";
            }
            info = info.substring(0, info.length() - 4) + "\n";
            info += "Final cost: " + finalCost;
        }
        return info;
    }

    public AStar()
    {
        clearListDirection();
    }
}
