package frc.robot.auton.Sequences;

import frc.robot.subsystems.Drivebase;
import frc.robot.Odometery;

public class AutoDrive {

    public void aDrive() {
        autoDrivebase.drive(.75, .75);
    }

    private static AutoDrive instance;
    private static Drivebase autoDrivebase;
    private static boolean endCondition = false;
    private static Odometery odometer;

    public static AutoDrive getInstance() {
        if (instance == null) {
            instance = new AutoDrive();
        }
        return instance;
    }

    public void update() {

        if (odometer.update().getX() == 5 && odometer.update().getY() == 5) {
            endCondition = true;
        }
    }
}
