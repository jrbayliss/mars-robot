package io.example.robot;

import java.util.List;


public class MarsRobotService {
    private GridBounds bounds;

    public void init(GridBounds bounds) {
        this.bounds = bounds;
    }

    public List<RobotPosition> robotMissions(List<RobotMission> missions) {
        return List.of(missions.get(0).getPosition());
    }

}
