package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import frc.robot.Ports;

public class Intake {
    private static Intake instance;

    private IntakeState state;
    private static GamePiece lastGamePiece;

    private enum GamePiece {
        NONE,
        CONE,
        CUBE,
    }

    private static final double INTAKE_OUTPUT_POWER = .5;
    private static final double INTAKE_HOLD_POWER = 0.05;

    public static CANSparkMax intakeController;

    public enum IntakeState {
        INTAKE_CUBE(-INTAKE_OUTPUT_POWER, GamePiece.CUBE),
        INTAKE_CONE(INTAKE_OUTPUT_POWER, GamePiece.CONE),
        HOLD_CONE(INTAKE_HOLD_POWER, GamePiece.CONE),
        HOLD_CUBE(-INTAKE_HOLD_POWER, GamePiece.CUBE),
        OUTAKE_CONE(-INTAKE_OUTPUT_POWER, lastGamePiece),
        OUTAKE_CUBE(INTAKE_OUTPUT_POWER, lastGamePiece),
        OFF(0.0, GamePiece.NONE);

        public final double power;
        public final GamePiece gamePiece;

        private IntakeState(double power, GamePiece gamePiece) {
            this.power = power;
            this.gamePiece = gamePiece;
        }
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

    private void updateState(boolean intakeCone, boolean intakeCube) {
        if (!intakeCone && !intakeCube && lastGamePiece == GamePiece.NONE) {
            setState(IntakeState.OFF);
        } else if (intakeCone) {
            setState(IntakeState.INTAKE_CONE);
        } else if (intakeCube) {
            setState(IntakeState.INTAKE_CUBE);
        } else if (lastGamePiece == GamePiece.CONE) {
            setState(IntakeState.HOLD_CONE);
        } else if (lastGamePiece == GamePiece.CUBE) {
            setState(IntakeState.HOLD_CUBE);
        }
    }

    public void update(boolean intakeCone, boolean intakeCube) {
        updateState(intakeCone, intakeCube);

        intakeController.set(state.power);
        lastGamePiece = state.gamePiece;
    }

    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }
        return instance;
    }
}
