package io;
enum Direction {
    NORD(0, 1), SUD(0, -1), OUEST(-1, 0), EST(1, 0);

    private final int x, y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int[] getDirection() {
        return new int[] {this.x, this.y};
    }

    @Override
    public String toString() {
        return "x : " + this.x + ", y : " + this.y;
    }
}
