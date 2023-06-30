package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;

public class Arm {
    private static Arm instance;
    private CANSparkMax motor;
    static final int ARM_CURRENT_LIMIT_A = 20;
    static final double ARM_OUTPUT_POWER = 0.4;
    private ArmState state;

    public enum ArmState {
        RAISE,
        LOWER,
    }

    public Arm() {
        motor = new CANSparkMax(5, MotorType.kBrushless);

        motor.setInverted(true);
        motor.setIdleMode(IdleMode.kBrake);
        motor.setSmartCurrentLimit(ARM_CURRENT_LIMIT_A);
        
    }

    public static Arm getInstance() {
        if (instance == null) {
            instance = new Arm();
        }
        return instance;
    }

    public void update() {
        if(state == ArmState.RAISE)
            motor.set(ARM_OUTPUT_POWER);
        else if (state == ArmState.LOWER)
            motor.set(-ARM_OUTPUT_POWER);
        else
            motor.set(0.0);
    }

    public void setState(ArmState state) {
        this.state = state;
    }
}
