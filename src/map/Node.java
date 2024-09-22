package map;
import enumerator.*;
import Robot.Robot;


public class Node implements Comparable<Node>{
    public int x, y; // Node position
    public Node parent; // Parent node in the path
    public TypeLand type; // Type of land

    public int g; // Cost from start to node
    public int h; // Heuristic cost from node to end
    public int f; // Total cost (f = g + h)

    public Node(int x, int y, TypeLand type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public Node(int x, int y) {
        this(x, y, TypeLand.STONE);
    }

    public void calculateCosts(Node start, Node end, Robot robot) {
        if (parent != null) {
            int caseSize = Map.getDataMap().getCaseSize();
            int speed = robot.getSpecialSpeed(parent.type);
            g = parent.g + (int) (caseSize / speed);
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

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}