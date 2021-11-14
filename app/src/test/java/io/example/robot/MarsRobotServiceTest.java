package io.example.robot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.example.robot.MoveInstruction.RIGHT;
import static io.example.robot.Orientation.EAST;
import static io.example.robot.Orientation.NORTH;
import static org.assertj.core.api.Assertions.assertThat;

class MarsRobotServiceTest {

    private MarsRobotService service;

    @BeforeEach
    void setUp() {
        service = new MarsRobotService();
    }

    @Test
    void givenNoMoves_thenRobotPositionIsSameAsInput() {
        init(grid1x1());
        assertThat(singleRobotMission(position(0, 0, NORTH), instructions())).isEqualTo(position(0, 0, NORTH));
    }

    @Test
    void givenRightInstructionApplied_robotIsRotated90Degrees() {
        init(grid1x1());
        assertThat(singleRobotMission(position(0, 0, NORTH), instructions(RIGHT))).isEqualTo(position(0, 0, EAST));
    }

    private void init(GridBounds bounds) {
        service.init(bounds);
    }

    private static GridBounds grid1x1() {
        return new GridBounds(1,1);
    }

    private RobotPosition singleRobotMission(RobotPosition position, List<MoveInstruction> instructions) {
        List<RobotPosition> robotPositions = service.robotMissions(missions(mission(position, instructions)));
        assertThat(robotPositions).hasSize(1);
        assertThat(robotPositions.get(0)).isNotNull();
        return robotPositions.get(0);
    }

    private static List<MoveInstruction> instructions(MoveInstruction... moves) {
        return List.of(moves);
    }

    private static RobotPosition position(int x, int y, Orientation orientation) {
        return new RobotPosition(x, y, orientation);
    }

    private static List<RobotMission> missions(RobotMission... missions) {
        return List.of(missions);
    }

    private static RobotMission mission(RobotPosition position, List<MoveInstruction> instructions) {
        return new RobotMission(position, instructions);
    }
}