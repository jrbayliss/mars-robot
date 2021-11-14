package io.example.robot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        GridBounds gridBounds = new GridBounds(1,1);
        RobotPosition robotPosition = new RobotPosition(0, 0, NORTH);
        List<MoveInstruction> directionsToMoves = List.of();

        service.init(gridBounds);
        RobotPosition currentRobotPosition = service.moveRobot(robotPosition, directionsToMoves);

        assertThat(currentRobotPosition).isEqualTo(new RobotPosition(0, 0, NORTH));
    }
}