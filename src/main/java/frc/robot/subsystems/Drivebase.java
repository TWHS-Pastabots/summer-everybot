package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Ports;

public class Drivebase {
    public static CANSparkMax rightSparkController1;
    public static CANSparkMax rightSparkController2;
    public static CANSparkMax leftSparkController1;
    public static CANSparkMax leftSparkController2;
    private static Drivebase instance;

    private static final double MAX_SPEED = 0.75;

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

    public void drive(double left, double right) {
        double leftSpeed = left * MAX_SPEED;
        double rightSpeed = right * MAX_SPEED;

        leftSparkController1.set(leftSpeed);
        leftSparkController2.set(leftSpeed);

        rightSparkController1.set(rightSpeed);
        rightSparkController2.set(rightSpeed);
    }

    public static Drivebase getInstance() {
        if (instance == null) {
            instance = new Drivebase();
        }
        return instance;
    }
}
