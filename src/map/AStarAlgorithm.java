package map;

import Robot.*;

import java.util.*;

import enumerator.*;

public class AStarAlgorithm {
    private static final Direction[] DIRECTIONS = { Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.NORTH }; 
    public static List<Direction> listDirection = new ArrayList<>();
    
    public List<Node> aStarSearch(Box[][] grid, Robot currenRobot, Box endBox) {
        PriorityQueue<Node> lookingList = new PriorityQueue<>();
        Set<Node> closedList = new HashSet<>();
        
        Box currentBox = currenRobot.getPositionRobot();
        Node end = new Node(endBox.getRow(), endBox.getColumn());
        Node start = new Node(currentBox.getRow(), currentBox.getColumn());

        if (currenRobot.getSpecialSpeed(grid[0][0].getNature()) == 0)
        {
            return Collections.emptyList();
        }
        start.calculateCosts(start, end, currenRobot);
        lookingList.add(start);

        while (!lookingList.isEmpty()) {
            Node current = lookingList.poll();

            if (current.equals(end)) {
                return reconstructPath(current);
            }

            closedList.add(current);
            
            for (Direction direction : DIRECTIONS) {
                int newX = current.x + direction.getX();
                int newY = current.y + direction.getY();
                
                if (isValidMove(grid, newX, newY, currenRobot) && !closedList.contains(new Node(newX, newY))) {
                    Node neighbor = new Node(newX, newY);
                    neighbor.parent = current;
                    neighbor.calculateCosts(start, end, currenRobot);
                    if (!lookingList.contains(neighbor)) 
                    {
                        lookingList.add(neighbor);
                    }
                }
            }
        }
        
        return Collections.emptyList(); // Return an empty list if no path is found
    }
    
    private List<Node> reconstructPath(Node end) {
        List<Node> path = new ArrayList<>();
        Node current = end;
        while (current != null) {
            path.add(current);
            if (current.parent != null) {
                int[] diff = current.compareNode(current.parent);
                Direction type = Direction.foundTypeFromDiff(diff);
                listDirection.add(type);
            }
            current = current.parent;
        }
        Collections.reverse(path);
        Collections.reverse(listDirection);
        return path;
    }

    private boolean isValidMove(Box[][] grid, int x, int y, Robot robot) {
        return x >= 0 && y >= 0 && x < grid.length && y < grid[0].length && robot.getSpecialSpeed(grid[x][y].getNature()) != 0;
    }

    public static List<Direction> getListDirection() {
        return listDirection;
    }
}
