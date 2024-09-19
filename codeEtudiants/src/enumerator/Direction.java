package enumerator;

/**
 * The {@code Direction} enum represents four cardinal directions (NORTH, SOUTH, WEST, and EAST).
 * Each direction is associated with specific x and y coordinates to denote movement in a 2D plane.
 * <ul>
 *     <li>NORTH: Moves up (0, 1)</li>
 *     <li>SOUTH: Moves down (0, -1)</li>
 *     <li>WEST: Moves left (-1, 0)</li>
 *     <li>EST: Moves right (1, 0)</li>
 * </ul>
 * This enum can be used to represent directions in games, maps, or any system requiring directional movement.
 */
public enum Direction {
    /**
     * Represents the North direction with coordinates (0, 1).
     */
    NORTH(0, 1),

    /**
     * Represents the South direction with coordinates (0, -1).
     */
    SOUTH(0, -1),

    /**
     * Represents the West direction with coordinates (-1, 0).
     */
    WEST(-1, 0),

    /**
     * Represents the East direction with coordinates (1, 0).
     */
    EAST(1, 0);

    private final int x, y;

    /**
     * Constructor for the Direction enum.
     * Initializes the direction with given x and y values.
     *
     * @param x the x-coordinate representing horizontal movement
     * @param y the y-coordinate representing vertical movement
     */
    Direction(int x, int y) 
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the directional movement as an array of integers.
     * 
     * @return an array of two integers, where the first element is the x-coordinate and 
     *         the second element is the y-coordinate.
     */
    public int[] getDirection() {
        return new int[]{this.x, this.y};
    }

    /**
     * Returns a string representation of the direction.
     * 
     * @return a string in the format "x : {x-value}, y : {y-value}".
     */
    @Override
    public String toString() {
        return "x : " + this.x + ", y : " + this.y;
    }
}
