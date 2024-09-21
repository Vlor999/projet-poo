package enumerator;

/**
 * The {@code Direction} enum represents four cardinal directions (NORTH, SOUTH, WEST, and EAST).
 * Each direction is associated with specific x and y coordinates to denote movement in a 2D plane.

 * This enum can be used to represent directions in games, maps, or any system requiring directional movement.
 */
public enum Direction {
    NORTH(0, 1, "N"),
    SOUTH(0, -1, "S"),
    WEST(-1, 0, "W"),
    EAST(1, 0, "E");

    private final int x, y;
    private final String name;

    /**
     * Constructor for the Direction enum.
     * Initializes the direction with given x and y values.
     * @param x the x-coordinate representing horizontal movement
     * @param y the y-coordinate representing vertical movement
     * @param name the name of the direction
     */
    Direction(int x, int y, String name) 
    {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public int[] getDirection() {
        return new int[]{this.x, this.y};
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int getX() {return x;}

    public int getY() {return y;}
}
