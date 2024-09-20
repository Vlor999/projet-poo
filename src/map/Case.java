package map;

import enumerator.TypeLand;

/**
 * The {@code Case} class represents a specific cell or square in a grid, characterized by
 * its row, column, and the type of terrain it contains, which is represented by {@code TypeLand}.
 * This class is useful in map-based systems, games, or simulations where each case in a grid has distinct properties.
 */
public class Case {
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
    public Case(int row, int column, TypeLand typeLand) {
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
    public int getColumne() {
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
     * Set the case as a robot
     * 
     * @param isRobot
     */
    public void setRobot(boolean isRobot)
    {
        this.isRobot = isRobot;
    }

    /**
     * Returns a string representation of this Case object.
     * 
     * @return a string representation of this object.
     */
    public String toString() 
    {
        return "Row:" + this.row + ", column: " + this.column + " terrain type: " + this.typeLand;
    }
}
