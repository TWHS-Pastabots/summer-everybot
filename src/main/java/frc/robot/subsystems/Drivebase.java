package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Misc;
import frc.robot.Ports;

public class Drivebase {

    private DriveSpeed driveSpeed = DriveSpeed.FULL;

    private static CANSparkMax rightSparkController1;
    private static CANSparkMax rightSparkController2;
    private static CANSparkMax leftSparkController1;
    private static CANSparkMax leftSparkController2;

    private static Drivebase instance;

    public enum DriveSpeed {
        SLOW(0.25),
        FULL(0.5);

        public final double speed;

        private DriveSpeed(double speed) {
            this.speed = speed;
        }
    }

    public Drivebase() {
        /* Right motor group */
        rightSparkController1 = new CANSparkMax(Ports.DRIVE_RIGHT_1, MotorType.kBrushed);
        rightSparkController1.setInverted(true);
        rightSparkController1.burnFlash();

        rightSparkController2 = new CANSparkMax(Ports.DRIVE_RIGHT_2, MotorType.kBrushed);
        rightSparkController2.setInverted(true);
        rightSparkController2.burnFlash();

        /* Left motor group */
        leftSparkController1 = new CANSparkMax(Ports.DRIVE_LEFT_1, MotorType.kBrushed);
        leftSparkController1.setInverted(false);
        leftSparkController1.burnFlash();

        leftSparkController2 = new CANSparkMax(Ports.DRIVE_LEFT_2, MotorType.kBrushed);
        leftSparkController2.setInverted(false);
        leftSparkController2.burnFlash();

    }

    public void drive(double forward, double turn) {
        double leftSpeed = Misc.clamp(forward - turn, -1, 1) * driveSpeed.speed;
        double rightSpeed = Misc.clamp(forward + turn, -1, 1) * driveSpeed.speed;

        leftSparkController1.set(leftSpeed);
        leftSparkController2.set(leftSpeed);

        rightSparkController1.set(rightSpeed);
        rightSparkController2.set(rightSpeed);

        SmartDashboard.putNumber("Left Spark 1", leftSparkController1.getAppliedOutput());
        SmartDashboard.putNumber("Left Spark 2", leftSparkController2.getAppliedOutput());
        SmartDashboard.putNumber("Right Spark 1", rightSparkController1.getAppliedOutput());
        SmartDashboard.putNumber("Right Spark 2", rightSparkController2.getAppliedOutput());
    }

    public void setDriveSpeed(DriveSpeed driveSpeed) {
        this.driveSpeed = driveSpeed;
    }

    public static Drivebase getInstance() {
        if (instance == null) {
            instance = new Drivebase();
        }
        return instance;
    }
}
