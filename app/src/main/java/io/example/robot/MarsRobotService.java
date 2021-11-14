package io.example.robot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.example.robot.Orientation.EAST;
import static io.example.robot.Orientation.NORTH;
import static io.example.robot.Orientation.SOUTH;
import static io.example.robot.Orientation.WEST;


public class MarsRobotService {
    private GridBounds bounds;
    private static final Map<Orientation, Orientation> APPLY_RIGHT_MAP = new HashMap<>();
    private static final Map<Orientation, Orientation> APPLY_LEFT_MAP = new HashMap<>();

    public MarsRobotService() {
        APPLY_RIGHT_MAP.put(NORTH, EAST);
        APPLY_RIGHT_MAP.put(EAST, SOUTH);
        APPLY_RIGHT_MAP.put(SOUTH, WEST);
        APPLY_RIGHT_MAP.put(WEST, NORTH);

        APPLY_LEFT_MAP.put(NORTH, WEST);
        APPLY_LEFT_MAP.put(WEST, SOUTH);
        APPLY_LEFT_MAP.put(SOUTH, EAST);
        APPLY_LEFT_MAP.put(EAST, NORTH);
    }

    public void init(GridBounds bounds) {
        this.bounds = bounds;
    }

    public List<RobotPosition> robotMissions(List<RobotMission> missions) {
        return missions.stream()
            .map(mission -> moveRobot(mission.getPosition(), mission.getInstructions())).collect(Collectors.toList());
    }

    public RobotPosition moveRobot(RobotPosition position, List<MoveInstruction> instructions) {
        instructions.forEach(instruction ->  {
            updatePosition(position, instruction);
        });
        return position;
    }

    private void updatePosition(RobotPosition position, MoveInstruction instruction) {
        if (instruction == MoveInstruction.RIGHT) {
            applyRight(position);
        } else if (instruction == MoveInstruction.LEFT) {
            applyLeft(position);
        } else {
            applyForward(position);
        }
    }

    private void applyRight(RobotPosition position) {
        position.setOrientation(APPLY_RIGHT_MAP.get(position.getOrientation()));
    }

    private void applyLeft(RobotPosition position) {
        position.setOrientation(APPLY_LEFT_MAP.get(position.getOrientation()));
    }

    private void applyForward(RobotPosition position) {
        Orientation orientation = position.getOrientation();
        switch (orientation) {
            case NORTH:
                position.setY(position.getY() + 1);
                break;
            case EAST:
                position.setX(position.getX() + 1);
                break;
            case SOUTH:
                position.setY(position.getY() - 1);
                break;
            case WEST:
                position.setX(position.getX() - 1);
                break;
        }
    }

}
