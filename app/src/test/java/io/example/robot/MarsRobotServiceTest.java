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
import static io.example.robot.TestUtil.LOST;
import static io.example.robot.TestUtil.grid3x3;
import static io.example.robot.TestUtil.instructions;
import static io.example.robot.TestUtil.mission;
import static io.example.robot.TestUtil.missions;
import static io.example.robot.TestUtil.position;
import static org.assertj.core.api.Assertions.assertThat;

class MarsRobotServiceTest {

    private MarsRobotService service;

    @BeforeEach
    void setUp() {
        service = new MarsRobotService();
    }

    @Test
    void givenNoMoves_thenRobotPositionIsSameAsInput() {
        init(TestUtil.grid1x1());
        assertThat(singleRobotMission(position(0, 0, NORTH), instructions())).isEqualTo(position(0, 0, NORTH));
    }

    @ParameterizedTest
    @MethodSource("rightInstructionApplied")
    void givenRightInstructionApplied_robotIsRotatedRight90Degrees(RobotPosition initialPosition, List<MoveInstruction> instructions, RobotPosition finalPosition) {
        init(TestUtil.grid1x1());
        assertThat(singleRobotMission(initialPosition, instructions)).isEqualTo(finalPosition);
    }

    @ParameterizedTest
    @MethodSource("leftInstructionApplied")
    void givenLeftInstructionApplied_robotIsRotatedLeft90Degrees(RobotPosition initialPosition, List<MoveInstruction> instructions, RobotPosition finalPosition) {
        init(TestUtil.grid1x1());
        assertThat(singleRobotMission(initialPosition, instructions)).isEqualTo(finalPosition);
    }

    @ParameterizedTest
    @MethodSource("forwardInstructionApplied")
    void givenForwardInstructionApplied_robotMovesInTheDirectionItIsFacing(RobotPosition initialPosition, List<MoveInstruction> instructions, RobotPosition finalPosition) {
        init(grid3x3());
        assertThat(singleRobotMission(initialPosition, instructions)).isEqualTo(finalPosition);
    }

    @ParameterizedTest
    @MethodSource("lostInstructionsApplied")
    void givenTwoForwardInstructionsAreApplied_robotIsOutOfBoundsAndDeemedLost(RobotPosition initialPosition, List<MoveInstruction> instructions, RobotPosition finalPosition) {
        init(grid3x3());
        assertThat(singleRobotMission(initialPosition, instructions)).isEqualTo(finalPosition);
    }

    @Test
    void givenTwoMissions_thenTwoMissionAreReturned() {
        init(grid3x3());
        assertThat(service.robotMissions(missions(
            mission(
                position(1, 1, NORTH),
                instructions(FORWARD)),
            mission(
                position(1, 1, EAST),
                instructions(FORWARD))))
        ).containsExactly(
            position(1, 2, NORTH),
            position(2, 1, EAST));
    }

    @Test
    void givenFirstMissionRobotIsLost_thenSecondMissionWillUseScentAndNotBeLost() {
        init(grid3x3());
        assertThat(service.robotMissions(missions(
            mission(
                position(1, 1, NORTH),
                instructions(FORWARD, FORWARD)),
            mission(
                position(1, 1, NORTH),
                instructions(FORWARD, FORWARD))))
        ).containsExactly(
            position(1, 2, NORTH, LOST),
            position(1, 2, NORTH));
    }

    private static Stream<Arguments> rightInstructionApplied() {
        return Stream.of(
            Arguments.of(position(0, 0, NORTH), instructions(RIGHT), position(0, 0, EAST)),
            Arguments.of(position(0, 0, EAST),  instructions(RIGHT), position(0, 0, SOUTH)),
            Arguments.of(position(0, 0, SOUTH), instructions(RIGHT), position(0, 0, WEST)),
            Arguments.of(position(0, 0, WEST),  instructions(RIGHT), position(0, 0, NORTH))
        );
    }

    private static Stream<Arguments> leftInstructionApplied() {
        return Stream.of(
            Arguments.of(position(0, 0, NORTH), instructions(LEFT), position(0, 0, WEST)),
            Arguments.of(position(0, 0, WEST),  instructions(LEFT), position(0, 0, SOUTH)),
            Arguments.of(position(0, 0, SOUTH), instructions(LEFT), position(0, 0, EAST)),
            Arguments.of(position(0, 0, EAST),  instructions(LEFT), position(0, 0, NORTH))
        );
    }

    private static Stream<Arguments> forwardInstructionApplied() {
        return Stream.of(
            Arguments.of(position(1, 1, NORTH), instructions(FORWARD), position(1, 2, NORTH)),
            Arguments.of(position(1, 1, EAST),  instructions(FORWARD), position(2, 1, EAST)),
            Arguments.of(position(1, 1, SOUTH), instructions(FORWARD), position(1, 0, SOUTH)),
            Arguments.of(position(1, 1, WEST),  instructions(FORWARD), position(0, 1, WEST))
        );
    }

    private static Stream<Arguments> lostInstructionsApplied() {
        return Stream.of(
            Arguments.of(position(1, 1, NORTH), instructions(FORWARD, FORWARD), position(1, 2, NORTH, LOST)),
            Arguments.of(position(1, 1, EAST),  instructions(FORWARD, FORWARD), position(2, 1, EAST, LOST)),
            Arguments.of(position(1, 1, SOUTH), instructions(FORWARD, FORWARD), position(1, 0, SOUTH, LOST)),
            Arguments.of(position(1, 1, WEST),  instructions(FORWARD, FORWARD), position(0, 1, WEST, LOST))
        );
    }

    private void init(GridBounds bounds) {
        service.init(bounds);
    }

    private RobotPosition singleRobotMission(RobotPosition position, List<MoveInstruction> instructions) {
        List<RobotPosition> robotPositions = service.robotMissions(missions(mission(position, instructions)));
        assertThat(robotPositions).hasSize(1);
        assertThat(robotPositions.get(0)).isNotNull();
        return robotPositions.get(0);
    }
}