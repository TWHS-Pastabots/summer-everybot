package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Ports;

public class Drivebase {
    public static CANSparkMax rightSparkController1;
    public static CANSparkMax rightSparkController2;
    public static CANSparkMax leftSparkController1;
    public static CANSparkMax leftSparkController2;
    private static Drivebase instance;

    private static final double MAX_SPEED = 1;

    public Drivebase() {
        // Right motor group

        rightSparkController1 = new CANSparkMax(Ports.RIGHT_SPARK1, MotorType.kBrushed);
        rightSparkController1.setSmartCurrentLimit(25);
        rightSparkController1.burnFlash();

        rightSparkController2 = new CANSparkMax(Ports.RIGHT_SPARK2, MotorType.kBrushed);
        rightSparkController2.setSmartCurrentLimit(25);
        rightSparkController2.burnFlash();

        // Left motor group

        leftSparkController1 = new CANSparkMax(Ports.LEFT_SPARK1, MotorType.kBrushed);
        leftSparkController1.setSmartCurrentLimit(25);
        leftSparkController1.burnFlash();

        leftSparkController2 = new CANSparkMax(Ports.LEFT_SPARK2, MotorType.kBrushed);
        leftSparkController2.setSmartCurrentLimit(25);
        leftSparkController2.burnFlash();
    }

    public void drive(double forward, double turn) {
        double leftSpeed = (forward - turn) * MAX_SPEED * 12;
        double rightSpeed = (forward + turn) * MAX_SPEED * 12;

        leftSparkController1.setVoltage(leftSpeed);
        leftSparkController2.setVoltage(leftSpeed);

        rightSparkController1.setVoltage(rightSpeed);
        rightSparkController2.setVoltage(rightSpeed);

        SmartDashboard.putNumber("Left Spark 1", leftSparkController1.getEncoder().getVelocity());
        SmartDashboard.putNumber("Left Spark 2", leftSparkController2.getEncoder().getVelocity());
        SmartDashboard.putNumber("Right Spark 1", rightSparkController1.getEncoder().getVelocity());
        SmartDashboard.putNumber("Right Spark 2", rightSparkController2.getEncoder().getVelocity());
    }

    public static Drivebase getInstance() {
        if (instance == null) {
            instance = new Drivebase();
        }
        return instance;
    }
}
