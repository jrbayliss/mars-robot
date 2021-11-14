package io.example.robot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.example.robot.MoveInstruction.FORWARD;
import static io.example.robot.MoveInstruction.LEFT;
import static io.example.robot.MoveInstruction.RIGHT;
import static io.example.robot.Orientation.EAST;
import static io.example.robot.Orientation.NORTH;
import static io.example.robot.Orientation.SOUTH;
import static io.example.robot.Orientation.WEST;
import static io.example.robot.TestUtil.grid5x3;
import static io.example.robot.TestUtil.instructions;
import static io.example.robot.TestUtil.lostPosition;
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
     * Sample data 1:
     * Input:
     * 5 3
     * 1 1 E RFRFRFRF
     *
     * Output:
     * 1 1 E
     */
    @Test
    void givenScenario1_thenExpect_1_1_E() {
        init(grid5x3());
        assertThat(singleRobotMission(
            position(1, 1, EAST),
            instructions(RIGHT, FORWARD, RIGHT, FORWARD, RIGHT, FORWARD, RIGHT, FORWARD))
        ).isEqualTo(position(1, 1, EAST));
    }

    /**
     * Sample data 2:
     * 3 2 N
     * FRRFLLFFRRFLL
     *
     * Output:
     * 3 3 N LOST
     */
    @Test
    void givenScenario2_thenExpect_3_3_N_LOST() {
        init(grid5x3());
        assertThat(singleRobotMission(
            position(3, 2, NORTH),
            instructions(FORWARD, RIGHT, RIGHT, FORWARD, LEFT, LEFT, FORWARD, FORWARD, RIGHT, RIGHT, FORWARD, LEFT, LEFT))
        ).isEqualTo(lostPosition(3, 3, NORTH));
    }

    /**
     * Sample data 3:
     * 0 3 W
     * LLFFFLFLFL
     *
     * Output:
     * 2 3 S
     */
    @Disabled("need to implement scent")
    @Test
    void givenScenario3_thenExpect_2_3_S() {
        init(grid5x3());
        assertThat(singleRobotMission(
            position(0, 3, WEST),
            instructions(LEFT, LEFT, FORWARD, FORWARD, FORWARD, LEFT, FORWARD, LEFT, FORWARD, LEFT))
        ).isEqualTo(position(2, 3, SOUTH));
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