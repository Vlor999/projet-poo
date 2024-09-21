package map;

import enumerator.TypeLand;

/**
 * The {@code Case} class represents a specific cell or square in a grid, characterized by
 * its row, column, and the type of terrain it contains, which is represented by {@code TypeLand}.
 * This class is useful in map-based systems, games, or simulations where each case in a grid has distinct properties.
 */
public class Box {
    // The row and column where this Case is located
    private int row;
    private int column;

    private boolean isFire = false;
    private int intensity = 0;

    private boolean isRobot = false;

    // The type of terrain for this Case
    private TypeLand typeLand;

    /**
     * Constructs a {@code Case} object with the specified row, column, and terrain type.
     *
     * @param row the row number where this Case is located
     * @param column the column number where this Case is located
     * @param typeLand the type of terrain associated with this Case
     */
    public Box(int row, int column, TypeLand typeLand) {
        this.row = row;
        this.column = column;
        this.typeLand = typeLand;
    }

    /**
     * Gets the row (row number) of this Case.
     *
     * @return the row number.
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Gets the column number of this Case.
     *
     * @return the column number.
     */
    public int getColumn() {
        return this.column;
    }

    /**
     * Gets the type of terrain ({@code TypeLand}) of this Case.
     *
     * @return the type of terrain.
     */
    public TypeLand getNature() {
        return this.typeLand;
    }

    /**
     * Inform if the case is on fire
     * 
     * @return true if the case is on fire
     */
    public boolean getFire() 
    {
        return this.isFire;
    }
    /**
     * Set the case on fire
     * 
     * @param isFire
     */
    public void setFire(boolean isFire)
    {
        this.isFire = isFire;
    }

    /**
     * Get the intensity of the fire
     * 
     * @return the intensity of the fire
     */
    public int getIntensity()
    {
        return this.intensity;
    }

    /**
     * Set the intensity of the fire
     * 
     * @param intensity
     */
    public void setIntensity(int intensity)
    {
        this.intensity = intensity;
    }

    /**
     * Inform if the case is a robot
     * 
     * @return true if the case is a robot
     */
    public boolean getRobot()
    {
        return this.isRobot;
    }

    /**
     * Returns a random case with random coordinates and a random terrain type. 
     * Must be static to be called without creating an instance of the class.
     * 
     * @return a random Case object.
     */
    public static Box randomCase() {
        int row = (int) (Math.random() * 10);
        int column = (int) (Math.random() * 10);
        TypeLand typeLand = TypeLand.randomTypeLand();
        return new Box(row, column, typeLand);
    }
    
    /**
     * Returns a string representation of this Case object.
     * 
     * @return a string representation of this object.
     */
    public String toString() 
    {
        return this.toString(0);
    }
    public String toString(int t)
    {
        String tab = "";
        for (int i = 0; i < t; i++)
        {
            tab += "\t";
        }
        return   tab + "* Row: "           + this.row 
        + "\n" + tab + "* Column: "       + this.column 
        + "\n" + tab + "* Terrain type: " + this.typeLand;
    }
}
