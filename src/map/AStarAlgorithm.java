package map;

import Robot.*;
import java.util.*;
import enumerator.*;
import io.Data;


class Node implements Comparable<Node>{
    public int x, y; // Node position
    public Node parent; // Parent node in the path

    public int g; // Cost from start to node
    public int h; // Heuristic cost from node to end
    public int f; // Total cost (f = g + h)

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void calculateCosts(Node start, Node end) {
        if (parent != null) {
            g = parent.g + 1; // Assuming a uniform grid where each move costs 1
        } else {
            g = 0;
        }
        h = Math.abs(x - end.x) + Math.abs(y - end.y); // Manhattan distance heuristic
        f = g + h;
    }

    @Override
    public int compareTo(Node aNode) {
        return Integer.compare(this.f, aNode.f);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Node)) return false;
        Node other = (Node) obj;
        return this.x == other.x && this.y == other.y;
    }

    public int[] compareNode(Node aNode) {
        return new int[]{this.x - aNode.x, this.y - aNode.y};
    }
}

public class AStarAlgorithm {
    private static final Direction[] DIRECTIONS = { Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.NORTH }; // Right, Down, Left, Up
    public static List<Direction> listDirection = new ArrayList<>();
    
    public List<Node> aStarSearch(int[][] grid, Robot currenRobot, Box endBox) {
        PriorityQueue<Node> openList = new PriorityQueue<>();
        Set<Node> closedList = new HashSet<>();
        
        Box currentBox = currenRobot.getPositionRobot();
        Node end = new Node(endBox.getRow(), endBox.getColumn());
        Node start = new Node(currentBox.getRow(), currentBox.getColumn());
        start.calculateCosts(start, end);
        openList.add(start);

        while (!openList.isEmpty()) {
            Node current = openList.poll();

            if (current.equals(end)) {
                return reconstructPath(current);
            }

            closedList.add(current);
            
            for (Direction direction : DIRECTIONS) {
                int newX = current.x + direction.getX();
                int newY = current.y + direction.getY();
                
                if (isValidMove(grid, newX, newY) && !closedList.contains(new Node(newX, newY))) {
                    Node neighbor = new Node(newX, newY);
                    neighbor.parent = current;
                    neighbor.calculateCosts(start, end);

                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
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
                if (diff[0] == 1 && diff[1] == 0) {
                    listDirection.add(Direction.EAST);
                } else if (diff[0] == -1 && diff[1] == 0) {
                    listDirection.add(Direction.WEST);
                } else if (diff[0] == 0 && diff[1] == 1) {
                    listDirection.add(Direction.NORTH);
                } else if (diff[0] == 0 && diff[1] == -1) {
                    listDirection.add(Direction.SOUTH);
                }
            }
            current = current.parent;
        }
        Collections.reverse(path);
        Collections.reverse(listDirection);
        return path;
    }

    private boolean isValidMove(int[][] grid, int x, int y) {
        return x >= 0 && y >= 0 && x < grid.length && y < grid[0].length && grid[x][y] == 0; // 0 represents walkable path
    }

    public static void main(String[] args) {
        // 0 represents open path, 1 represents obstacles
        int[][] grid = {
            {0, 0, 0, 0, 0},
            {1, 1, 0, 1, 1},
            {0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1},
            {0, 0, 0, 0, 0}
        };
        // Lecture grille : -> et v. Donc faire attention au cardinaux.

        Box endBox = new Box(4, 4, TypeLand.WATER);
        Box currentCase = new Box(0, 0, TypeLand.WATER);
        
        AStarAlgorithm aStar = new AStarAlgorithm();
        
        Data mapData = new Data(grid.length, grid[0].length, 1);
                
        Drone drone = new Drone(mapData, currentCase);

        List<Node> path = aStar.aStarSearch(grid, drone, endBox);
        if (path.isEmpty()) {
            System.out.println("No path found.");
        } else {
            System.out.println("Path found:" + listDirection);
            for (Node node : path) {
                System.out.println("(" + node.x + ", " + node.y + ")");
            }
        }
    }
}
