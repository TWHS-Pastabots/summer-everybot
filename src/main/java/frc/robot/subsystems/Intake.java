package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.revrobotics.CANSparkMax.IdleMode;
import frc.robot.Ports;
import frc.robot.subsystems.Arm.ArmState;

public class Intake {
    private static Intake instance;

    private IntakeState state;

    public static CANSparkMax intakeController;

    public enum IntakeState {
        INTAKE(0.5),
        OUTTAKE(-0.5),
        SHOOT(-1),
        OFF(0);

        public final double power;

        private IntakeState(double power) {
            this.power = power;

        }
    }

    public Intake() {
        intakeController = new CANSparkMax(Ports.INTAKE, MotorType.kBrushless);
        intakeController.setInverted(false);
        intakeController.setIdleMode(IdleMode.kBrake);
        intakeController.burnFlash();
    }

    public void setState(IntakeState state) {
        this.state = state;
    }

    private void updateState(boolean outtake, boolean intakeCube) {
        if (outtake) {
            if (Arm.getInstance().state == ArmState.SHOOT) {
                setState(IntakeState.SHOOT);
            } else {
                setState(IntakeState.OUTTAKE);
            }
        } else if (intakeCube) {
            setState(IntakeState.INTAKE);
        } else {
            setState(IntakeState.OFF);
        }
    }

    public void update(boolean outtake, boolean intake) {
        updateState(outtake, intake);
        intakeController.set(state.power);
    }

    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }
        return instance;
    }
}
