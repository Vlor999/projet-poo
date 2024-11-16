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
    EAST(1, 0, "E"),
    NEUTRAL(0, 0, "NEUTRAL");

    private final int x, y;
    private final String name;

    /**
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

    /**
     * Get the x and y coordinates of the direction. Using deep copy to prevent modification of the original values.
     * @return int[] containing the x and y coordinates of the direction
     */
    public int[] getDirection() {
        return new int[]{this.x, this.y};
    }

    @Override
    /**
     * Gives the name of the direction
     */
    public String toString() {
        return this.name;
    }

    public int getX() {return x;}

    public int getY() {return y;}


    /**
     * Get the direction from the difference of 2 positions. 
     * @param diff
     * @return Direction (NORTH, SOUTH, WEST, EAST) or null if the difference is not a valid direction.
     */
    public static Direction foundTypeFromDiff(int[] diff) {
        if (diff[0] == 1 && diff[1] == 0) {
            return Direction.EAST;
        } else if (diff[0] == -1 && diff[1] == 0) {
            return Direction.WEST;
        } else if (diff[0] == 0 && diff[1] == 1) {
            return Direction.NORTH;
        } else if (diff[0] == 0 && diff[1] == -1) {
            return Direction.SOUTH;
        }
        return Direction.NEUTRAL;
    }
}
