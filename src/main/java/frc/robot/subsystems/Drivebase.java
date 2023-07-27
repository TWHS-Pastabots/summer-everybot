package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Ports;

public class Drivebase {
    public static VictorSPX rightVictorController;
    public static VictorSPX leftVictorController;
    public static CANSparkMax rightSparkController;
    public static CANSparkMax leftSparkController;
    private static Drivebase instance;

    private static final double MAX_SPEED = 0.5;

    public Drivebase() {
        // Right motor group
        rightVictorController = new VictorSPX(Ports.RIGHT_VICTOR);
        rightVictorController.setInverted(false);
        rightVictorController.setNeutralMode(NeutralMode.Brake);

        rightSparkController = new CANSparkMax(Ports.RIGHT_SPARK, MotorType.kBrushed);
        rightSparkController.setInverted(true);
        rightSparkController.setIdleMode(IdleMode.kBrake);

        // Left motor group
        leftVictorController = new VictorSPX(Ports.LEFT_VICTOR);
        leftVictorController.setInverted(false);
        leftVictorController.setNeutralMode(NeutralMode.Brake);

        leftSparkController = new CANSparkMax(Ports.LEFT_SPARK, MotorType.kBrushed);
        leftSparkController.setInverted(true);
        leftSparkController.setIdleMode(IdleMode.kBrake);
    }

    public void drive(double left, double right) {
        double leftSpeed = left * MAX_SPEED;
        double rightSpeed = right * MAX_SPEED;

        leftVictorController.set(ControlMode.PercentOutput, leftSpeed);
        leftSparkController.set(leftSpeed);

        rightVictorController.set(ControlMode.PercentOutput, rightSpeed);
        rightSparkController.set(rightSpeed);
    }

    public static Drivebase getInstance() {
        if (instance == null) {
            instance = new Drivebase();
        }
        return instance;
    }
}
