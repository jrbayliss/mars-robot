package io.example.robot;

import java.util.Objects;

public class GridPosition {
    private final int x;
    private final int y;

    public GridPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GridPosition)) return false;
        GridPosition gridPosition = (GridPosition) o;
        return x == gridPosition.x && y == gridPosition.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "GridPosition{" +
            "x=" + x +
            ", y=" + y +
            '}';
    }
}
