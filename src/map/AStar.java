package map;

import Robot.*;
import java.util.*;

import enumerator.*;

public class AStar {
    private static final Direction[] DIRECTIONS = {Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.NORTH}; 
    public List<Direction> listDirection = new ArrayList<>();
    private static final List<Box> EMPTY_LIST = Collections.emptyList();
    private List<Direction> finalListDirection = new ArrayList<>();

    public List<Box> findBestWayTo(Robot robot, List<Box> list)
    {
        if (robot.getIsUseless())
        {
            return EMPTY_LIST;
        }

        double minVal = Double.MAX_VALUE;
        List<Box> bestPath = EMPTY_LIST;

        for (Box endBox : list)
        {
            List<Box> path = this.aStarSearch(Map.getCurrentMap(), robot, endBox, minVal);
            if (!path.isEmpty())
            {
                double currentVal = path.get(path.size() - 1).getGCost();
                if (currentVal < minVal)
                {
                    this.setFinalListDirection(this.getListDirection());
                    minVal = currentVal;
                    bestPath = path;
                }
            }
        }
        if (bestPath.isEmpty())
        {
            System.out.println("No path for the robot :  : " + robot.getType());
            robot.setIsUseless(true);
            return EMPTY_LIST;
        }
        else
        {
            return listPonderatedPath(robot, bestPath);
        }
    }

    public static List<Box> listPonderatedPath(Robot robot, List<Box> path)
    {
        List<Box> res = new ArrayList<>();
        Box current;
        double caseSize = Map.getDataMap().getCaseSize();
        for (int i = 0; i < path.size(); i+=1)
        {
            current = path.get(i);
            double currentSpeed = robot.getSpecialSpeed(current.getNature());
            double time = 1 * (caseSize / currentSpeed);
            for (int j = 0; j <= (int)time; j++)
            {
                res.add(current);
            }
        }
        return res;
    }

    /**
     * A* search algorithm to find the shortest path from the robot to the end box
     * @param grid that represents the map using Box objects
     * @param currentRobot the robot that will be moving on the map, realy useful to get the speed of the robot and the spec
     * @param endBox the box that the robot wants to reach
     * @return a list of boxes that represents the path from the robot to the end box
     */
    public List<Box> aStarSearch(Box[][] grid, Robot currentRobot, Box endBox, double maxValue) {

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
            return EMPTY_LIST;
        }

        // Set up start and end boxes
        startBox.calculateCosts(startBox, endBox, currentRobot);
        openList.add(startBox);

        while (!openList.isEmpty()) {
            Box current = openList.poll();

            if (current.equals(endBox) || (!currentRobot.getType().equals("Drone") && current.distanceTo(endBox) == 1)) 
            {
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
            if (current.getGCost() > maxValue)
            {
                return EMPTY_LIST;
            }
        }
        return EMPTY_LIST; // Return an empty list if no path is found
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

    public String showInfo(List<Box> path) {
        if (path.isEmpty()) {
            return "No path found.";
        }

        StringBuilder info = new StringBuilder("Path found: " + finalListDirection + "\n\t* ");
        double finalCost = path.get(path.size() - 1).getGCost(); // The final cost is the G cost of the last box in the path
        
        for (Box node : path) {
            info.append("(").append(node.getRow()).append(", ").append(node.getColumn()).append(") -> ");
        }

        info.setLength(info.length() - 4); // Remove the last " -> "
        info.append("\nFinal cost: ").append(finalCost);

        return info.toString();
    }

    public AStar()
    {
        clearListDirection();
    }
}
