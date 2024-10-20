package gui;

abstract class CenteredGraphicalElement
implements GraphicalElement
{
    private int x;
    private int y;

    public CenteredGraphicalElement(int paramInt1, int paramInt2) {
        this.x = paramInt1;
        this.y = paramInt2;
    }

    public void translate(int paramInt1, int paramInt2) {
        this.x += paramInt1;
        this.y += paramInt2;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
