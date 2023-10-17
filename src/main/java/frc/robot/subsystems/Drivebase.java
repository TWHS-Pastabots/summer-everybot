package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Misc;
import frc.robot.Ports;

public class Drivebase {

    private DriveSpeed driveSpeed = DriveSpeed.FAST;

    private static CANSparkMax rightSparkController1;
    private static CANSparkMax rightSparkController2;
    private static CANSparkMax leftSparkController1;
    private static CANSparkMax leftSparkController2;

    private static Drivebase instance;

    public enum DriveSpeed {
        SLOW(0.1),
        FAST(0.25);

        public final double speed;

        private DriveSpeed(double speed) {
            this.speed = speed;
        }
    }

    public Drivebase() {
        /* Right motor group */

        rightSparkController1 = new CANSparkMax(Ports.DRIVE_RIGHT_1, MotorType.kBrushed);
        rightSparkController1.setInverted(true);
        rightSparkController1.setOpenLoopRampRate(0.5);
        rightSparkController1.setClosedLoopRampRate(0.5);
        rightSparkController1.burnFlash();

        rightSparkController2 = new CANSparkMax(Ports.DRIVE_RIGHT_2, MotorType.kBrushed);
        rightSparkController2.setInverted(true);
        rightSparkController2.setOpenLoopRampRate(0.5);
        rightSparkController2.setClosedLoopRampRate(0.5);
        rightSparkController2.burnFlash();

        /* Left motor group */
        leftSparkController1 = new CANSparkMax(Ports.DRIVE_LEFT_1, MotorType.kBrushed);
        leftSparkController1.setInverted(false);
        leftSparkController1.setOpenLoopRampRate(0.5);
        leftSparkController1.setClosedLoopRampRate(0.5);
        leftSparkController1.burnFlash();

        leftSparkController2 = new CANSparkMax(Ports.DRIVE_LEFT_2, MotorType.kBrushed);
        leftSparkController2.setInverted(false);
        leftSparkController2.setOpenLoopRampRate(0.5);
        leftSparkController2.setClosedLoopRampRate(0.5);
        leftSparkController2.burnFlash();

    }

    public void drive(double forward, double turn) {
        double leftSpeed = Misc.clamp((forward * driveSpeed.speed) - turn, -1, 1);
        double rightSpeed = Misc.clamp((forward * driveSpeed.speed) + turn, -1, 1);

        SmartDashboard.putNumber("RIGHT SPEED", (int) (rightSpeed * 100));
        SmartDashboard.putNumber("LEFT SPEED", (int) (leftSpeed * 100));
        SmartDashboard.putNumber("LEFT 1 OUTPUT", leftSparkController1.getOutputCurrent());
        SmartDashboard.putNumber("LEFT 2 OUTPUT", leftSparkController1.getOutputCurrent());
        SmartDashboard.putNumber("RIGHT 1 OUTPUT", leftSparkController1.getOutputCurrent());
        SmartDashboard.putNumber("RIGHT 2 OUTPUT", leftSparkController1.getOutputCurrent());

        leftSparkController1.set(leftSpeed);
        leftSparkController2.set(leftSpeed);

        rightSparkController1.set(rightSpeed);
        rightSparkController2.set(rightSpeed);
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
