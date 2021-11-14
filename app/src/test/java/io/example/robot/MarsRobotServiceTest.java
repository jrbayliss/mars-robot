package io.example.robot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static io.example.robot.MoveInstruction.FORWARD;
import static io.example.robot.MoveInstruction.LEFT;
import static io.example.robot.MoveInstruction.RIGHT;
import static io.example.robot.Orientation.EAST;
import static io.example.robot.Orientation.NORTH;
import static io.example.robot.Orientation.SOUTH;
import static io.example.robot.Orientation.WEST;
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

    @ParameterizedTest
    @MethodSource("rightInstructionApplied")
    void givenRightInstructionApplied_robotIsRotatedRight90Degrees(RobotPosition initialPosition, List<MoveInstruction> instructions, RobotPosition finalPosition) {
        init(grid1x1());
        assertThat(singleRobotMission(initialPosition, instructions)).isEqualTo(finalPosition);
    }

    @ParameterizedTest
    @MethodSource("leftInstructionApplied")
    void givenLeftInstructionApplied_robotIsRotatedLeft90Degrees(RobotPosition initialPosition, List<MoveInstruction> instructions, RobotPosition finalPosition) {
        init(grid1x1());
        assertThat(singleRobotMission(initialPosition, instructions)).isEqualTo(finalPosition);
    }

    @ParameterizedTest
    @MethodSource("forwardInstructionApplied")
    void givenForwardInstructionApplied_robotMovesInTheDirectionItIsFacing(RobotPosition initialPosition, List<MoveInstruction> instructions, RobotPosition finalPosition) {
        init(grid3x3());
        assertThat(singleRobotMission(initialPosition, instructions)).isEqualTo(finalPosition);
    }

    private static Stream<Arguments> rightInstructionApplied() {
        return Stream.of(
            Arguments.of(position(0, 0, NORTH), instructions(RIGHT), position(0, 0, EAST)),
            Arguments.of(position(0, 0, EAST), instructions(RIGHT), position(0, 0, SOUTH)),
            Arguments.of(position(0, 0, SOUTH), instructions(RIGHT), position(0, 0, WEST)),
            Arguments.of(position(0, 0, WEST), instructions(RIGHT), position(0, 0, NORTH))
        );
    }

    private static Stream<Arguments> leftInstructionApplied() {
        return Stream.of(
            Arguments.of(position(0, 0, NORTH), instructions(LEFT), position(0, 0, WEST)),
            Arguments.of(position(0, 0, WEST), instructions(LEFT), position(0, 0, SOUTH)),
            Arguments.of(position(0, 0, SOUTH), instructions(LEFT), position(0, 0, EAST)),
            Arguments.of(position(0, 0, EAST), instructions(LEFT), position(0, 0, NORTH))
        );
    }

    private static Stream<Arguments> forwardInstructionApplied() {
        return Stream.of(
            Arguments.of(position(1, 1, NORTH), instructions(FORWARD), position(1, 2, NORTH)),
            Arguments.of(position(1, 1, EAST), instructions(FORWARD), position(2, 1, EAST)),
            Arguments.of(position(1, 1, SOUTH), instructions(FORWARD), position(1, 0, SOUTH)),
            Arguments.of(position(1, 1, WEST), instructions(FORWARD), position(0, 1, WEST))
        );
    }

    private void init(GridBounds bounds) {
        service.init(bounds);
    }

    private static GridBounds grid1x1() {
        return new GridBounds(1,1);
    }

    private static GridBounds grid3x3() {
        return new GridBounds(3,3);
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