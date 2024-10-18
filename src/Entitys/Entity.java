package Entitys;
import Utils.Point;

public class Entity {
    protected Point position;

    public Entity(int x, int y) {
        this.position = new Point(x, y);
    }

    public Entity(Point point) {
        this.position = point;
    }

    public Point getPosition() { return position; }

    public void setPosition(int x, int y) {
        this.position.setX(x);
        this.position.setY(y);
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}