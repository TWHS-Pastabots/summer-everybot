package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Ports;

public class Intake {
    private static Intake instance;
    private CANSparkMax intake;

    static final double INTAKE_OUTPUT_POWER = 1.0;
    static final double INTAKE_HOLD_POWER = 0.07;
    private IntakeState state;

    public enum IntakeState {
        INTAKE,
        OUTTAKE,
        HOLD_CONE,
        HOLD_CUBE,
        OFF
    }

    public Intake() {
        intake = new CANSparkMax(Ports.INTAKE, MotorType.kBrushless);
        intake.setInverted(false);

    }

    public void setState(IntakeState state) {
        this.state = state;
    }

    public void update() {
        if (state == IntakeState.OFF)
            intake.set(0.0);
        else if (state == IntakeState.HOLD_CUBE)
            intake.set(INTAKE_HOLD_POWER);
        else if (state == IntakeState.HOLD_CONE)
            intake.set(-INTAKE_HOLD_POWER);
        else if (state == IntakeState.INTAKE)
            intake.set(INTAKE_OUTPUT_POWER);
        else if (state == IntakeState.OUTTAKE)
            intake.set(-INTAKE_OUTPUT_POWER);
    }

    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }
        return instance;
    }
}
