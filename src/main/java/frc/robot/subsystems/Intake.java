package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import frc.robot.Ports;

public class Intake {
    private static Intake m_instance;
    public static CANSparkMax intakeController;
    private String lastGamePiece = "";
    static final int INTAKE_CURRENT_LIMIT_A = 25;
    static final int INTAKE_HOLD_CURRENT_LIMIT_A = 5;
    static final double INTAKE_OUTPUT_POWER = 1.0;
    static final double INTAKE_HOLD_POWER = -0.07;
    private IntakeState state;

    public enum IntakeState {
        INTAKE_CU,
        INTAKE_CO,
        HOLD_CONE,
        HOLD_CUBE,
        OUTAKE_CO,
        OUTAKE_CU,
        OFF
    }

    public Intake() {
        intakeController = new CANSparkMax(Ports.INTAKE, MotorType.kBrushless);
        intakeController.setInverted(false);
        intakeController.setIdleMode(IdleMode.kBrake);
        intakeController.burnFlash();
    }

    public static Intake getInstance() {
        if (m_instance == null) {
            m_instance = new Intake();
        }
        return m_instance;
    }

    public void setState(IntakeState state) {
        this.state = state;
    }

    public void update() {
        if (state == IntakeState.OFF)
            intakeController.set(0.0);
        else if (state == IntakeState.INTAKE_CO) {
            intakeController.set(-0.7);
            lastGamePiece = "Cone";
        } else if (state == IntakeState.INTAKE_CU) {
            intakeController.set(0.7);
            lastGamePiece = "Cube";
        } else if (state == IntakeState.HOLD_CONE) {
            intakeController.set(INTAKE_HOLD_POWER);
        } else if (state == IntakeState.HOLD_CUBE) {
            intakeController.set(0.0);
        } else if (state == IntakeState.OUTAKE_CO & lastGamePiece == "Cone") {
            intakeController.set(0.7);
        } else if (state == IntakeState.OUTAKE_CU & lastGamePiece == "Cube") {
            intakeController.set(-0.7);
        }

        /*
         * if (lastGamePiece == "cone")
         * motor.set(-INTAKE_HOLD_POWER);
         * else if (lastGamePiece == "cube") {
         * motor.set(0.0);
         * }
         */

    }
}
