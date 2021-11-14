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
        init(grid1x1());
        RobotPosition currentRobotPosition = service.moveRobot(position(0, 0, NORTH), instructions());
        assertThat(currentRobotPosition).isEqualTo(position(0, 0, NORTH));
    }

    private void init(GridBounds bounds) {
        service.init(bounds);
    }

    private static GridBounds grid1x1() {
        return new GridBounds(1,1);
    }

    private static List<MoveInstruction> instructions(MoveInstruction... moves) {
        return List.of(moves);
    }

    private static RobotPosition position(int x, int y, Orientation orientation) {
        return new RobotPosition(x, y, orientation);
    }
}