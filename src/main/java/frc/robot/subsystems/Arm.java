package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

//import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Ports;

public class Arm {
    private static Arm instance;
    public static CANSparkMax armController;
    static final int ARM_CURRENT_LIMIT_A = 5;
    static double ARM_OUTPUT_POWER = 1.6;
    private ArmState state = ArmState.RETRACTED;
    private PIDController armPID = new PIDController(2.5, 1, 0.0);
    private double reqPosition = 7;

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
        SmartDashboard.putNumber("ARM POSITION", armController.getEncoder().getPosition());
        if (state == ArmState.RETRACTED) {
            reqPosition = 14;
        } else if (state == ArmState.EXTENDED) {
            reqPosition = -18;
        }

        double reqPower = armPID.calculate(armController.getEncoder().getPosition(), reqPosition);

        SmartDashboard.putNumber("Arm Requested Power", reqPower);

        // armController.set(ARM_OUTPUT_POWER);
        // if (state == ArmState.EXTENDED) {
        // armController.set(ARM_OUTPUT_POWER);
        // } else if (state == ArmState.RETRACTED) {
        // armController.set(-ARM_OUTPUT_POWER);
        // }

        armController.setVoltage(reqPower);

        if (state == ArmState.RETRACTED && armController.getEncoder().getPosition() > reqPosition) {
            armController.setVoltage(0.0);
        } else if (state == ArmState.EXTENDED && armController.getEncoder().getPosition() < reqPosition) {
            armController.setVoltage(0.0);
        }
    }

    public void setState(ArmState state) {
        this.state = state;
    }
}
