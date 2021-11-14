package io.example.robot;

import java.util.Objects;

public class GridBounds {
    private final int width;
    private final int height;

    public GridBounds(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GridBounds)) return false;
        GridBounds gridBounds = (GridBounds) o;
        return width == gridBounds.width && height == gridBounds.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }

    @Override
    public String toString() {
        return "Grid{" +
            "width=" + width +
            ", height=" + height +
            '}';
    }
}
