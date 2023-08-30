package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import frc.robot.subsystems.Drivebase;

public class Odometery {

    public class Rotation2d {

    }

    public Pose2d update() {

        double rotationsR = Drivebase.rightSparkController1.getEncoder().getPosition();
        double distanceR = rotationsR * (2 * (Math.PI) * 3);

        double rotationsL = Drivebase.leftSparkController1.getEncoder().getPosition();
        double distanceL = rotationsL * (2 * (Math.PI) * 3);

        DifferentialDriveOdometry odometer = new DifferentialDriveOdometry(, distanceL, distanceR);
        return odometer.getPoseMeters();
    }

}
