package io.example.robot;

import java.util.List;


public class MarsRobotService {
    private GridBounds bounds;

    public void init(GridBounds bounds) {
        this.bounds = bounds;
    }

    public RobotPosition moveRobot(RobotPosition position, List<MoveInstruction> instructions) {
        return position;
    }

}
