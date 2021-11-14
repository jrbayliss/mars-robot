package io.example.robot;

import java.util.List;
import java.util.Objects;

public class RobotMission {
    private final RobotPosition position;
    private final List<MoveInstruction> instructions;

    public RobotMission(RobotPosition position, List<MoveInstruction> instructions) {
        this.position = position;
        this.instructions = instructions;
    }

    public RobotPosition getPosition() {
        return position;
    }

    public List<MoveInstruction> getInstructions() {
        return instructions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RobotMission)) return false;
        RobotMission that = (RobotMission) o;
        return Objects.equals(position, that.position) && Objects.equals(instructions, that.instructions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, instructions);
    }

    @Override
    public String toString() {
        return "RobotInstructions{" +
            "position=" + position +
            ", instructions=" + instructions +
            '}';
    }
}
