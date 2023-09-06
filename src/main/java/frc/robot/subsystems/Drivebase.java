package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import frc.robot.Ports;

public class Drivebase {
    public static CANSparkMax rightSparkController1;
    public static CANSparkMax rightSparkController2;
    public static CANSparkMax leftSparkController1;
    public static CANSparkMax leftSparkController2;
    private static Drivebase instance;
    public static DifferentialDriveOdometry odometer;
    public static Pose2d angle;
    public Pose2d position;

    private static final double MAX_SPEED = 0.5;

    public Drivebase() {
        // Right motor group

        rightSparkController1 = new CANSparkMax(Ports.RIGHT_SPARK1, MotorType.kBrushed);
        rightSparkController1.setInverted(true);
        rightSparkController1.setIdleMode(IdleMode.kBrake);

        rightSparkController2 = new CANSparkMax(Ports.RIGHT_SPARK2, MotorType.kBrushed);
        rightSparkController2.setInverted(true);
        rightSparkController2.setIdleMode(IdleMode.kBrake);

        // Left motor group

        leftSparkController1 = new CANSparkMax(Ports.LEFT_SPARK1, MotorType.kBrushed);
        leftSparkController1.setInverted(true);
        leftSparkController1.setIdleMode(IdleMode.kBrake);

        leftSparkController2 = new CANSparkMax(Ports.LEFT_SPARK2, MotorType.kBrushed);
        leftSparkController2.setInverted(true);
        leftSparkController2.setIdleMode(IdleMode.kBrake);
    }

    public void drive(double forward, double turn) {
        double leftSpeed = (forward - turn) * MAX_SPEED;
        double rightSpeed = (forward + turn) * MAX_SPEED;

        leftSparkController1.set(leftSpeed);
        leftSparkController2.set(leftSpeed);

        rightSparkController1.set(rightSpeed);
        rightSparkController2.set(rightSpeed);
    }

    public void update() {
        angle.getRotation().fromDegrees(0);//Later used

        double rotationsR = rightSparkController1.getEncoder().getPosition();
        double distanceR = rotationsR * ((Math.PI) * 3);

        double rotationsL = leftSparkController1.getEncoder().getPosition();
        double distanceL = rotationsL * ((Math.PI) * 3);

        odometer = new DifferentialDriveOdometry(angle.getRotation(), distanceL, distanceR);
        position = odometer.getPoseMeters();
    }

    public void autoDrive(double distance, double speed) {
        while (position.getY() < position.getY() + distance) {
            drive(speed, 0);
        }
    }

    public static Drivebase getInstance() {
        if (instance == null) {
            instance = new Drivebase();
        }
        return instance;
    }
}
