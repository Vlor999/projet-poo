import io.*;
import enumerator.*;
import Robot.*;
import map.*;

public class TestRobot {
    public static void main(String[] args) {
        // Access the NORD enum constant
        Direction d = Direction.EAST;
        System.out.println("Direction: " + d);
        TypeLand n = TypeLand.WATER;
        System.out.println(n);

        Data mapData = new Data(10, 10, 1);
        Box currentCase = new Box(5, 7, TypeLand.FIELD);

        // Create a new Drone object
        Drone drone = new Drone(mapData, Box.randomCase());
        System.out.println(drone);
        WheeledRobot wheeledRobot = new WheeledRobot(mapData, currentCase);
        System.out.println(wheeledRobot);
        LeggedRobot leggedRobot = new LeggedRobot(mapData, Box.randomCase());
        System.out.println(leggedRobot);
        CaterpillarRobot caterpillarRobot = new CaterpillarRobot(mapData, currentCase);
        System.out.println(caterpillarRobot);
        System.out.println("Number of robots: " + Robot.getRobotCount());
    }
}
