package io.example.robot;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static io.example.robot.MoveInstruction.FORWARD;
import static io.example.robot.MoveInstruction.LEFT;
import static io.example.robot.MoveInstruction.RIGHT;
import static io.example.robot.Orientation.EAST;
import static io.example.robot.Orientation.NORTH;
import static io.example.robot.Orientation.SOUTH;
import static io.example.robot.Orientation.WEST;

public class MarsRobotService {
    private static final Map<Orientation, Orientation> APPLY_RIGHT_MAP = new HashMap<>();
    private static final Map<Orientation, Orientation> APPLY_LEFT_MAP = new HashMap<>();
    private static final Map<Orientation, GridPosition> APPLY_FORWARD_MAP = new HashMap<>();

    private GridBounds gridBounds;
    private Set<GridPosition> robotLostPositions = new HashSet<>();

    public MarsRobotService() {
        APPLY_RIGHT_MAP.put(NORTH, EAST);
        APPLY_RIGHT_MAP.put(EAST, SOUTH);
        APPLY_RIGHT_MAP.put(SOUTH, WEST);
        APPLY_RIGHT_MAP.put(WEST, NORTH);

        APPLY_LEFT_MAP.put(NORTH, WEST);
        APPLY_LEFT_MAP.put(WEST, SOUTH);
        APPLY_LEFT_MAP.put(SOUTH, EAST);
        APPLY_LEFT_MAP.put(EAST, NORTH);

        APPLY_FORWARD_MAP.put(NORTH, new GridPosition(0,1));
        APPLY_FORWARD_MAP.put(EAST, new GridPosition(1,0));
        APPLY_FORWARD_MAP.put(SOUTH, new GridPosition(0,-1));
        APPLY_FORWARD_MAP.put(WEST, new GridPosition(-1,0));
    }

    public void init(GridBounds bounds) {
        this.gridBounds = bounds;
    }

    public List<RobotPosition> robotMissions(List<RobotMission> missions) {
        return missions.stream()
            .map(mission -> moveRobot(mission.getPosition(), mission.getInstructions())).collect(Collectors.toList());
    }

    public RobotPosition moveRobot(RobotPosition robotPosition, List<MoveInstruction> instructions) {
        for (MoveInstruction instruction: instructions) {
            if (instruction == FORWARD) {
                GridPosition nextGridPosition = nextForwardGridPosition(robotPosition);
                if (!inBounds(nextGridPosition)) {
                    if (!robotLostPositions.contains(nextGridPosition)) {
                        robotPosition.setLost(true);
                        robotLostPositions.add(nextGridPosition);
                        return robotPosition;
                    }
                } else {
                    robotPosition.setPosition(nextGridPosition);
                }
            } else {
                applyRotation(robotPosition, instruction);
            }
        }
        return robotPosition;
    }

    private boolean inBounds(GridPosition gridPosition) {
        if (gridPosition.getX() < 0 || gridPosition.getX() >= gridBounds.getWidth() ||
            gridPosition.getY() < 0 || gridPosition.getY() >= gridBounds.getHeight()) {
            return false;
        }
        return true;
    }

    private void applyRotation(RobotPosition robotPosition, MoveInstruction instruction) {
        if (instruction == RIGHT) {
            applyRight(robotPosition);
        } else if (instruction == LEFT) {
            applyLeft(robotPosition);
        }
    }

    private void applyRight(RobotPosition robotPosition) {
        robotPosition.setOrientation(APPLY_RIGHT_MAP.get(robotPosition.getOrientation()));
    }

    private void applyLeft(RobotPosition robotPosition) {
        robotPosition.setOrientation(APPLY_LEFT_MAP.get(robotPosition.getOrientation()));
    }

    private GridPosition nextForwardGridPosition(RobotPosition robotPosition) {
        Orientation orientation = robotPosition.getOrientation();
        GridPosition gridPositionDelta = APPLY_FORWARD_MAP.get(orientation);
        GridPosition currentGridPosition = robotPosition.getPosition();
        return new GridPosition(
            currentGridPosition.getX() + gridPositionDelta.getX(),
            currentGridPosition.getY() + gridPositionDelta.getY());
    }
}
