package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import frc.robot.Ports;

public class Intake {
    private static Intake m_instance;

    private IntakeState state;
    private GamePiece lastGamePiece;

    private static final double INTAKE_OUTPUT_POWER = .75;
    private static final double INTAKE_HOLD_POWER = -0.03;

    public static CANSparkMax intakeController;

    public enum IntakeState {
        INTAKE_CUBE,
        INTAKE_CONE,
        HOLD_CONE,
        HOLD_CUBE,
        OUTAKE_CONE,
        OUTAKE_CUBE,
        OFF
    }

    private enum GamePiece {
        NONE,
        CONE,
        CUBE,
    }

    public Intake() {
        lastGamePiece = GamePiece.NONE;

        intakeController = new CANSparkMax(Ports.INTAKE, MotorType.kBrushless);
        intakeController.setInverted(false);
        intakeController.setIdleMode(IdleMode.kBrake);
        intakeController.burnFlash();
    }

    public void setState(IntakeState state) {
        this.state = state;
    }

    private void updateState(boolean intakeCone, boolean intakeCube, boolean outtake) {
        if (!intakeCone && !intakeCube && !outtake && lastGamePiece == GamePiece.NONE) {
            setState(IntakeState.OFF);
        } else if (intakeCone) {
            setState(IntakeState.INTAKE_CONE);
        } else if (intakeCube) {
            setState(IntakeState.INTAKE_CUBE);
        } else if (outtake && lastGamePiece == GamePiece.CONE) {
            setState(IntakeState.OUTAKE_CONE);
        } else if (outtake && lastGamePiece == GamePiece.CUBE) {
            setState(IntakeState.OUTAKE_CUBE);
            // } else if (lastGamePiece == GamePiece.CONE) {
            // setState(IntakeState.HOLD_CONE);
        } else if (lastGamePiece == GamePiece.CUBE) {
            setState(IntakeState.HOLD_CUBE);
        }
    }

    public void update(boolean intakeCone, boolean intakeCube, boolean outtake) {
        updateState(intakeCone, intakeCube, outtake);

        if (state == IntakeState.OFF) {
            intakeController.set(0.0);
        } else if (state == IntakeState.INTAKE_CONE) {
            intakeController.set(INTAKE_OUTPUT_POWER);
            lastGamePiece = GamePiece.CONE;
        } else if (state == IntakeState.INTAKE_CUBE) {
            intakeController.set(-INTAKE_OUTPUT_POWER);
            lastGamePiece = GamePiece.CUBE;
        } else if (state == IntakeState.HOLD_CONE) {
            intakeController.set(INTAKE_HOLD_POWER);
        } else if (state == IntakeState.HOLD_CUBE) {
            intakeController.set(-INTAKE_HOLD_POWER);
        } else if (state == IntakeState.OUTAKE_CONE) {
            intakeController.set(-INTAKE_OUTPUT_POWER);
            lastGamePiece = GamePiece.NONE;
        } else if (state == IntakeState.OUTAKE_CUBE) {
            intakeController.set(INTAKE_OUTPUT_POWER);
            lastGamePiece = GamePiece.NONE;
        }
    }

    public static Intake getInstance() {
        if (m_instance == null) {
            m_instance = new Intake();
        }
        return m_instance;
    }
}