package io.example.robot;

import java.util.Objects;

public class RobotPosition {
    private GridPosition gridPosition;
    private Orientation orientation;
    private boolean isLost = false;

    public RobotPosition(GridPosition gridPosition, Orientation orientation) {
        this.gridPosition = gridPosition;
        this.orientation = orientation;
    }

    public GridPosition getPosition() {
        return gridPosition;
    }

    public void setPosition(GridPosition gridPosition) {
        this.gridPosition = gridPosition;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public void setLost(boolean lost) {
        isLost = lost;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RobotPosition)) return false;
        RobotPosition position1 = (RobotPosition) o;
        return isLost == position1.isLost && Objects.equals(gridPosition, position1.gridPosition) && orientation == position1.orientation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gridPosition, orientation, isLost);
    }

    @Override
    public String toString() {
        return "RobotPosition{" +
            "position=" + gridPosition +
            ", orientation=" + orientation +
            ", isLost=" + isLost +
            '}';
    }
}
