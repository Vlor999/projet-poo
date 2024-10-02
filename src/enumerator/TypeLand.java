package enumerator;

import java.awt.Color;

/**
 * The {@code TypeLand} enum represents different types of terrain (WATER, FOREST, STONE, FIELD and HABITATION).
 * Each terrain type has an associated integer value and a descriptive name.
 * This enum can be useful in map generation, categorizing different land types, or any system that uses various terrain features.
 */
public enum TypeLand {
    WATER(0, "water", Color.BLUE, new String[] {"images/d2.png"}),
    FOREST(1, "forest", Color.GREEN, new String[] {"images/dd1.png"}),
    STONE(2, "stone", Color.GRAY, new String[] {"images/d7.png"}),
    FIELD(3, "field", Color.YELLOW, new String[] {"images/d1.png"}),
    HABITATION(4, "habitation", Color.ORANGE, new String[] {"images/d3.png"});

    // Terrain value and name
    private final int valueTerrain;
    private final String name;
    private final Color color;
    private final String[] listLand;

    /**
     * Constructor for the TypeLand enum.
     * Initializes the terrain type with a specific integer value and name.
     *
     * @param valueTerrain the integer value representing the terrain type
     * @param name the name of the terrain type
     */
    TypeLand(int valueTerrain, String name, Color color, String[] listLand) {
        this.valueTerrain = valueTerrain;
        this.name = name;
        this.color = color;
        this.listLand = listLand;
    }

    public int getValueTerrain() {return this.valueTerrain;}

    public Color getColor() {return this.color;}

    public String toStringComplete() {
        return "The terrain value is : " + this.valueTerrain + " and the name is : " + this.name;
    }

    public static TypeLand randomTypeLand() {
        return TypeLand.values()[(int) (Math.random() * TypeLand.values().length)];
    }

    /**
     * Convert a string to a TypeLand
     * @param chaineNature wich is the string to convert
     * @return the TypeLand corresponding to the string
     */
    public static TypeLand convertStringToTypeLand(String chaineNature){
        switch (chaineNature) {
            case "TERRAIN_LIBRE":
                return TypeLand.FIELD;
            case "EAU":
                return TypeLand.WATER;
            case "HABITAT":
                return TypeLand.HABITATION;
            case "FORET":
                return TypeLand.FOREST;
            case "ROCHE":
                return TypeLand.STONE;
            default:
                throw new IllegalArgumentException("Unknown terrain type: " + chaineNature);
        }
    }

    public static String infos()
    {
        String info = "";
        for (TypeLand typeLand : TypeLand.values())
        {
            info += typeLand.toStringComplete() + "\n";
        }
        return info;
    }

    public String[] getFiles()
    {
        return this.listLand;
    }

}
