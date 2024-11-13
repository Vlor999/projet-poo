package map;

import java.util.*;

import enumerator.*;
import robot.*;

public class AStar {
    private static final Direction[] DIRECTIONS = {Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.NORTH}; 
    private List<Direction> listDirection = new ArrayList<>();
    private static final List<Box> EMPTY_LIST = Collections.emptyList();
    private List<Direction> finalListDirection = new ArrayList<>();

    /**
     * Find the closest box from the robot among the elements in the list
     * @param robot The robot that will move
     * @param list The list of boxes that the robot can reach
     * @return the closest box from the robot and the path
     */
    public List<Box> findBestWayTo(Robot robot, List<Box> list)
    {
        // We are going to look path for each bow and found the best one
        double minVal = Double.MAX_VALUE;
        List<Box> bestPath = EMPTY_LIST;

        for (Box endBox : list)
        {
            // minVal is the minimum value of the path. There is no need to found a path if the cost is higher than the minimum value
            List<Box> path = this.aStarSearch(Map.getCurrentMap(), robot, endBox, minVal);
            if (!path.isEmpty())
            {
                double currentVal = path.get(path.size() - 1).getGCost(); // The final cost is the G cost of the last box in the path
                if (currentVal < minVal)
                {
                    this.setFinalListDirection(this.getListDirection());
                    minVal = currentVal;
                    bestPath = path;
                }
            }
        }
        // If the best path is empty, the robot can't move then we set the robot as useless. No more path will be found for this robot
        if (bestPath.isEmpty())
        {
            robot.setIsUseless(true);
            return EMPTY_LIST;
        }
        else
        {
            return listPonderatedPath(robot, bestPath); // We return the ponderated path
        }
    }

    /**
     * Ponderate the path to have a better view of the path. This represent the time that the robot will take to go to the end box
     * We are copying the box as many time as the robot will take to go to the next box
     * @param robot The robot that will move
     * @param path The path that the robot will take
     * @return
     */
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
                res.add(current); // We add the box as many time as the robot will take to go to the next box
            }
        }
        return res;
    }

    /**
     * A* search algorithm to find the shortest path from the robot to the end box
     * @param grid that represents the map using Box objects
     * @param currentRobot the robot that will be moving on the map, realy useful to get the speed of the robot and the spec
     * @param endBox the box that the robot wants to reach
     * @param maxValue the maximum value of the cost that the robot can reach. Won't go further than this value
     * @return a list of boxes that represents the path from the robot to the end box
     */
    public List<Box> aStarSearch(Box[][] grid, Robot currentRobot, Box endBox, double maxValue) {

        for (Box[] boxes : grid) {
            for (Box box : boxes) {
                box.resetCosts();
            }
        }
        clearListDirection(); // Clear the list of directions before starting the search. To be sure that the list is empty.

        PriorityQueue<Box> openList = new PriorityQueue<>(Comparator.comparingDouble(box -> box.getFCost())); // Priority about the F cost
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

            if (current.equals(endBox) || (!(Drone.isDrone(currentRobot)) && current.distanceTo(endBox) == 1)) 
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
            if (current.getGCost() > maxValue) // If the cost is higher than the maximum value, we stop the search
            {
                return EMPTY_LIST;
            }
        }
        return EMPTY_LIST; // Return an empty list if no path is found
    }

    /**
     * Reconstruct the path from the end box to the start box
     * @param end the final Box which is the aim 
     * @return the list of boxes from the current position to end 
     */
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

    /**Check if the move is valid and the robot can move to the box
     * @param grid the map represented with boxes, 
     * @param x the coordinate x the robot wants to go 
     * @param y the coordinate y the robot wants to go 
     * @param robot the aimed robot 
     * @return a boolean which is validates (or not) the move  
     */ 
    private boolean isValidMove(Box[][] grid, int x, int y, Robot robot) {
        return x >= 0 && y >= 0 && x < grid.length && y < grid[0].length && robot.getSpecialSpeed(grid[x][y].getNature()) != 0;
    }

    public List<Direction> getListDirection() {
        return this.listDirection;
    }

    public void clearListDirection() {
        this.listDirection.clear();
    }

    /**Create a normal copy of d
     * @param d the list of directions 
     */
    public void setFinalListDirection(List<Direction> d){
        List <Direction> res =new ArrayList<>();
        for (Direction elem : d){
            res.add(elem);
        }
        this.finalListDirection = res;
    }

    /**Shows the path for a robot move (No path found if not) 
     * @param path the list of box the robot wants to do to move 
     * @return a long string sentence which contains all the path 
    */
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

    /**
    * Constructor that clear the list of directions
    */
    public AStar()
    {
        clearListDirection();
    }
}
