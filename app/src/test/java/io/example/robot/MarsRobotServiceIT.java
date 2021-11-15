package io.example.robot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.example.robot.MoveInstruction.FORWARD;
import static io.example.robot.MoveInstruction.LEFT;
import static io.example.robot.MoveInstruction.RIGHT;
import static io.example.robot.Orientation.EAST;
import static io.example.robot.Orientation.NORTH;
import static io.example.robot.Orientation.SOUTH;
import static io.example.robot.Orientation.WEST;
import static io.example.robot.TestUtil.LOST;
import static io.example.robot.TestUtil.grid6x4;
import static io.example.robot.TestUtil.instructions;
import static io.example.robot.TestUtil.mission;
import static io.example.robot.TestUtil.missions;
import static io.example.robot.TestUtil.position;
import static org.assertj.core.api.Assertions.assertThat;

class MarsRobotServiceIT {

    private MarsRobotService service;

    @BeforeEach
    void setUp() {
        service = new MarsRobotService();
    }

    /**
     * Sample data:
     * 5 3
     * 1 1 E RFRFRFRF
     *
     * 3 2 N
     * FRRFLLFFRRFLL
     *
     * 0 3 W
     * LLFFFLFLFL
     *
     * Output:
     * 1 1 E
     * 3 3 N LOST
     * 2 3 S
     */
    @Test
    void givenAllScenarios_thenExpectTheSampleOutput() {
        // 5x3 being the upper right co-ordinates makes 6x4 grid
        init(grid6x4());
        assertThat(service.robotMissions(missions(
            mission(
                position(1, 1, EAST),
                instructions(RIGHT, FORWARD, RIGHT, FORWARD, RIGHT, FORWARD, RIGHT, FORWARD)),
            mission(
                position(3, 2, NORTH),
                instructions(FORWARD, RIGHT, RIGHT, FORWARD, LEFT, LEFT, FORWARD, FORWARD, RIGHT, RIGHT, FORWARD, LEFT, LEFT)),
            mission(
                position(0, 3, WEST),
                instructions(LEFT, LEFT, FORWARD, FORWARD, FORWARD, LEFT, FORWARD, LEFT, FORWARD, LEFT))
            ))
        ).containsExactly(
            position(1, 1, EAST),
            position(3, 3, NORTH, LOST),
            position(2, 3, SOUTH));
    }

    private void init(GridBounds bounds) {
        service.init(bounds);
    }
}