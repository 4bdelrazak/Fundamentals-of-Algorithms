package DataStructures;

import java.awt.*;

public class MyPoint  {
    private Point point;

    public MyPoint(int x, int y) {
        this.point = new Point(x, y);
    }

    public int getX() {
        return point.x;
    }

    public int getY() {
        return point.y;
    }

    @Override
    public String toString() {
        return "(" + point.x + ", " + point.y + ")";
    }
}
