package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

//import edu.wpi.first.math.MathUtil;
//import edu.wpi.first.math.controller.PIDController;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Ports;

public class Arm {
    private static Arm instance;
    public static CANSparkMax armController;
    static final int ARM_CURRENT_LIMIT_A = 20;
    static final double ARM_OUTPUT_POWER = 0.9;
    private ArmState state = ArmState.RETRACTED;
    // private PIDController armController = new PIDController(1, 0.1, 0.0);
    // private double reqPosition = 1.5;

    public enum ArmState {
        RETRACTED,
        EXTENDED,
    }

    public Arm() {
        armController = new CANSparkMax(Ports.ARM, MotorType.kBrushless);

        armController.setInverted(false);
        armController.setIdleMode(IdleMode.kBrake);
        armController.setSmartCurrentLimit(ARM_CURRENT_LIMIT_A);
        armController.burnFlash();
    }

    public void setPower(double power) {
        armController.set(power * ARM_OUTPUT_POWER);
    }

    public static Arm getInstance() {
        if (instance == null) {
            instance = new Arm();
        }
        return instance;
    }

    public void update() {
        // SmartDashboard.putNumber("ARM POSITION", arm.getEncoder().getPosition());
        // if (state == ArmState.RETRACTED) {
        // reqPosition = 1.5;
        // } else if (state == ArmState.EXTENDED) {
        // reqPosition = -18.0;
        // }

        // double power = armController.calculate(arm.getEncoder().getPosition(),
        // reqPosition);
        // arm.set(power * 0.1);
        if (state == ArmState.EXTENDED) {
            armController.set(ARM_OUTPUT_POWER);
        } else if (state == ArmState.RETRACTED) {
            armController.set(-ARM_OUTPUT_POWER);
        }

    }

    public void setState(ArmState state) {
        this.state = state;
    }
}
