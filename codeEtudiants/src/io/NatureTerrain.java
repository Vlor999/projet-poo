package io;
enum NatureTerrain {
    /*
     * It may be interesting to change value and order them to know if there is something special
     * And also which categorie it is
     */
    WATER (0, "water"), FOREST (1, "forest"), ROCHE(2, "stone"), FIELD(3, "field"), HABITATION(4, "habitation");

    private final int valueTerrain;
    private final String name;

    NatureTerrain(int valueTerrain, String name) {
        this.valueTerrain = valueTerrain;
        this.name = name;
    }

    public int getValueTerrain() {
        return this.valueTerrain;
    }

    @Override
    public String toString() {
        return "The terrain value is : " + this.valueTerrain + " and the name is : " + this.name;
    }
}
