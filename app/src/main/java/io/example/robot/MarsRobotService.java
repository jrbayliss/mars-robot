package io.example.robot;

import java.util.List;
import java.util.stream.Collectors;

import static io.example.robot.Orientation.EAST;


public class MarsRobotService {
    private GridBounds bounds;

    public void init(GridBounds bounds) {
        this.bounds = bounds;
    }

    public List<RobotPosition> robotMissions(List<RobotMission> missions) {
        return missions.stream()
            .map(mission -> moveRobot(mission.getPosition(), mission.getInstructions())).collect(Collectors.toList());
    }

    public RobotPosition moveRobot(RobotPosition position, List<MoveInstruction> instructions) {
        instructions.forEach(instruction ->  {
            updatePosition(position);
        });
        return position;
    }

    private void updatePosition(RobotPosition position) {
        Orientation orientation = position.getOrientation();

        switch (orientation) {
            case NORTH:
                position.setOrientation(EAST);
                break;
        }
    }

}
