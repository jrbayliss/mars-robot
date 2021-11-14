package io.example.robot;

import java.util.Objects;

public class RobotPosition {
    private int x;
    private int y;
    private Orientation orientation;

    public RobotPosition(int x, int y, Orientation orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

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

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RobotPosition)) return false;
        RobotPosition robotPosition = (RobotPosition) o;
        return x == robotPosition.x && y == robotPosition.y && orientation == robotPosition.orientation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, orientation);
    }

    @Override
    public String toString() {
        return "Robot{" +
            "x=" + x +
            ", y=" + y +
            ", orientation=" + orientation +
            '}';
    }
}
