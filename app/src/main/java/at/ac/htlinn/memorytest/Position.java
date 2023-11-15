package at.ac.htlinn.memorytest;

public class Position {
    public int x = 0;
    public int y = 0;

    public Position(int x, int y) {
        this.x =x;
        this.y = y;
    }

    public Position(int id, int rowLength, boolean _) {
        this.y = id / rowLength;
        this.x = id % rowLength;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean equals(Object obj){
    return false;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
