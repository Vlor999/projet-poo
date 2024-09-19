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
}
