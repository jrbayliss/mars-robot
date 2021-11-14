package io.example.robot;

import java.util.List;

public class TestUtil {
    static GridBounds grid1x1() {
        return new GridBounds(1, 1);
    }

    static GridBounds grid3x3() {
        return new GridBounds(3, 3);
    }

    static GridBounds grid6x4() {
        return new GridBounds(6, 4);
    }

    static List<MoveInstruction> instructions(MoveInstruction... moves) {
        return List.of(moves);
    }

    static RobotPosition position(int x, int y, Orientation orientation) {
        return new RobotPosition(gridPosition(x, y), orientation);
    }

    static RobotPosition lostPosition(int x, int y, Orientation orientation) {
        RobotPosition position = new RobotPosition(gridPosition(x, y), orientation);
        position.setLost(true);
        return position;
    }

    static GridPosition gridPosition(int x, int y) {
        return new GridPosition(x, y);
    }

    static List<RobotMission> missions(RobotMission... missions) {
        return List.of(missions);
    }

    static RobotMission mission(RobotPosition position, List<MoveInstruction> instructions) {
        return new RobotMission(position, instructions);
    }
}
