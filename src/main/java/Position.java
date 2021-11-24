public class Position {
    private int x, y;

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Position position) {
        return (this.getX() == position.getX() && this.getY() == position.getY());
    }
}
