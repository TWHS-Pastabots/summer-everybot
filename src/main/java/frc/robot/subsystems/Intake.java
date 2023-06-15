package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;

public class Intake {
    private static Intake m_instance;
    private CANSparkMax motor;
    static final int INTAKE_CURRENT_LIMIT_A = 25;
    static final int INTAKE_HOLD_CURRENT_LIMIT_A = 5;
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
        motor = new CANSparkMax(6, MotorType.kBrushless);
        motor.setInverted(false);
        motor.setIdleMode(IdleMode.kBrake);
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

    public void update () {
        if (state == IntakeState.OFF)
            motor.set(0.0);
        else if(state == IntakeState.HOLD_CUBE)
            motor.set(INTAKE_HOLD_POWER);
        else if (state == IntakeState.HOLD_CONE)
            motor.set(-INTAKE_HOLD_POWER);
        else if(state == IntakeState.INTAKE)
            motor.set(INTAKE_OUTPUT_POWER);
        else if(state == IntakeState.OUTTAKE)
            motor.set(-INTAKE_OUTPUT_POWER);
    }
}
