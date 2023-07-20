package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Ports;

public class Drivebase {
    private VictorSPX rightVictor;
    private VictorSPX leftVictor;
    private CANSparkMax rightSpark;
    private CANSparkMax leftSpark;

    private static Drivebase instance;

    private final double MAX_SPEED = 0.5;

    public Drivebase() {
        // Right motor group
        rightVictor = new VictorSPX(Ports.RIGHT_VICTOR);
        rightVictor.setInverted(false);

        rightSpark = new CANSparkMax(Ports.RIGHT_SPARK, MotorType.kBrushed);
        rightSpark.setInverted(false);

        // Left motor group
        leftVictor = new VictorSPX(Ports.LEFT_VICTOR);
        leftVictor.setInverted(true);

        leftSpark = new CANSparkMax(Ports.LEFT_SPARK, MotorType.kBrushed);
        leftSpark.setInverted(true);
    }

    public void drive(double left, double right) {
        double leftOutput = left * MAX_SPEED;
        double rightOutput = right * MAX_SPEED;

        leftVictor.set(ControlMode.PercentOutput, leftOutput);
        leftSpark.set(leftOutput);
        rightVictor.set(ControlMode.PercentOutput, rightOutput);
        rightSpark.set(rightOutput);
    }

    public static Drivebase getInstance() {
        if (instance == null) {
            instance = new Drivebase();
        }
        return instance;
    }
}
