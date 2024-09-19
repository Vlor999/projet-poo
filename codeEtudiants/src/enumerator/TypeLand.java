package enumerator;

/**
 * The {@code TypeLand} enum represents different types of terrain (WATER, FOREST, STONE, FIELD, HABITATION).
 * Each terrain type has an associated integer value and a descriptive name.
 * <ul>
 *     <li>WATER: Terrain with value 0 and name "water".</li>
 *     <li>FOREST: Terrain with value 1 and name "forest".</li>
 *     <li>STONE: Terrain with value 2 and name "stone".</li>
 *     <li>FIELD: Terrain with value 3 and name "field".</li>
 *     <li>HABITATION: Terrain with value 4 and name "habitation".</li>
 * </ul>
 * This enum can be useful in map generation, categorizing different land types, or any system that uses various terrain features.
 */
public enum TypeLand {
    /**
     * Represents a water terrain with value 0.
     */
    WATER(0, "water"),

    /**
     * Represents a forest terrain with value 1.
     */
    FOREST(1, "forest"),

    /**
     * Represents a rocky terrain with value 2 (STONE means stone).
     */
    STONE(2, "stone"),

    /**
     * Represents a field terrain with value 3.
     */
    FIELD(3, "field"),

    /**
     * Represents a habitation terrain with value 4.
     */
    HABITATION(4, "habitation");

    // Terrain value and name
    private final int valueTerrain;
    private final String name;

    /**
     * Constructor for the TypeLand enum.
     * Initializes the terrain type with a specific integer value and name.
     *
     * @param valueTerrain the integer value representing the terrain type
     * @param name the name of the terrain type
     */
    TypeLand(int valueTerrain, String name) {
        this.valueTerrain = valueTerrain;
        this.name = name;
    }

    /**
     * Gets the value associated with the terrain type.
     *
     * @return the integer value of the terrain type.
     */
    public int getValueTerrain() {
        return this.valueTerrain;
    }

    /**
     * Returns a string representation of the terrain.
     *
     * @return a string in the format "The terrain value is: {value} and the name is: {name}".
     */
    public String toStringComplete() {
        return "The terrain value is : " + this.valueTerrain + " and the name is : " + this.name;
    }

    /**
     * Returns a string representation of the terrain.
     *
     * @return a string in the format "The terrain value is: {value} and the name is: {name}".
     */
    public String toString() {
        return "" + this.valueTerrain;
    }


}
