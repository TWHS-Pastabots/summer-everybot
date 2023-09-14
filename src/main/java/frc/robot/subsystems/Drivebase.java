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
        rightSparkController2.follow(rightSparkController1);
        rightSparkController2.burnFlash();

        // Left motor group

        leftSparkController1 = new CANSparkMax(Ports.LEFT_SPARK1, MotorType.kBrushed);
        leftSparkController1.setSmartCurrentLimit(25);
        leftSparkController1.burnFlash();

        leftSparkController2 = new CANSparkMax(Ports.LEFT_SPARK2, MotorType.kBrushed);
        leftSparkController2.follow(leftSparkController1);
        leftSparkController2.burnFlash();

    }

    public void drive(double forward, double turn) {
        double leftSpeed = (forward - turn) * MAX_SPEED * 6;
        double rightSpeed = (forward + turn) * MAX_SPEED * 6;

        leftSparkController1.setVoltage(leftSpeed);

        rightSparkController1.setVoltage(rightSpeed);

        SmartDashboard.putNumber("Left Spark 1", leftSparkController1.getAppliedOutput());
        SmartDashboard.putNumber("Right Spark 1", rightSparkController1.getAppliedOutput());
    }

    public static Drivebase getInstance() {
        if (instance == null) {
            instance = new Drivebase();
        }
        return instance;
    }
}
