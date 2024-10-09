package map;

import enumerator.*;
import robot.*;

public class Box {
    private int row;
    private int column;
    private TypeLand typeLand;

    private Box parent = null;
    private double gCost; // Cost from the start to this box
    private double hCost; // Heuristic cost to the end box

    public Box(int row, int column, TypeLand typeLand) {
        this.row = row;
        this.column = column;
        this.typeLand = typeLand;
        this.gCost = Double.MAX_VALUE; // Default to max value until calculated
        this.hCost = 0;
    }

    /**
     * Generate a random case usefull for testing
     * @return a random Box
     */
    public static Box randomCase() {
        int row = (int) (Math.random() * 10);
        int column = (int) (Math.random() * 10);
        TypeLand typeLand = TypeLand.randomTypeLand();
        return new Box(row, column, typeLand);
    }

    // Getters 
    public int getRow() { return this.row; }
    public int getColumn() { return this.column; }
    public TypeLand getNature() { return this.typeLand; }
    public double getGCost() { return gCost; }
    public double getHCost() { return hCost; }
    public double getFCost() { return gCost + hCost; }
    public Box getParent() { return parent; }

    public void setParent(Box parent) { this.parent = parent; }

    /**
     * Calculate the costs of the box depending on the robot that you are using
     * @param start
     * @param end
     * @param robot
     */
    public void calculateCosts(Box start, Box end, Robot robot) {
        double caseSize = Map.getDataMap().getCaseSize();
        double speed = robot.getSpecialSpeed(this.getNature());
        if (this == start) {
            this.gCost = 0;
        }
        else {
            // Equivalent as a time
            this.gCost = parent.gCost + (caseSize / speed);
        }

        // must be equivalent as a time
        this.hCost = (Math.abs(end.getRow() - this.getRow()) + Math.abs(end.getColumn() - this.getColumn())) / speed;
    }

    /**
     * Reset the costs of the box. To be sure that the box is not already used.
     */
    public void resetCosts() {
        this.gCost = Double.MAX_VALUE;
        this.hCost = 0;
        this.parent = null;
    }

    /**
     * Compare the box with another box. It will be usefull onto Direction enum
     * @param other
     * @return
     */
    public int[] compareBox(Box other) {
        return new int[] { this.row - other.row, this.column - other.column };
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Box)) {
            return false;
        }
        Box other = (Box) obj;
        return this.row == other.row && this.column == other.column;
    }

    @Override
    public String toString() {
        return "* Position: (" + this.row + ", " + this.column + ") \n* Terrain type: " + this.typeLand;
    }

    /**
     * Return the string of the box with a tabulation
     * @param t
     * @return
     */
    public String toString(int t)
    {
        String tab = "";
        for (int i = 0; i < t; i++)
        {
            tab += "\t";
        }
        return   tab + "* Position : (" + this.row + ", " + this.column + ")"
        + "\n" + tab + "* Terrain type: " + this.typeLand;
    }

    /**
     * Calculate the distance between two boxes
     * @param otherBox 
     * @return the distance between the two boxes squared
     */
    public double distanceTo(Box otherBox) {
        double dx = this.row - otherBox.row;
        double dy = this.column - otherBox.column;
        return dx * dx + dy * dy;
    }
}
