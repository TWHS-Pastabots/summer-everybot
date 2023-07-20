package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Ports;

import com.revrobotics.CANSparkMax.IdleMode;

public class Arm {
    private static Arm instance;
    private CANSparkMax arm;

    private static final double ARM_OUTPUT_POWER = 0.5;

    public enum ArmState {
        RAISE,
        LOWER,
    }

    public Arm() {
        arm = new CANSparkMax(Ports.ARM, MotorType.kBrushless);
        arm.setInverted(true);
        arm.setIdleMode(IdleMode.kBrake);
    }

    public void setPower(double power) {
        arm.set(power * ARM_OUTPUT_POWER);
    }

    public static Arm getInstance() {
        if (instance == null) {
            instance = new Arm();
        }
        return instance;
    }
}
