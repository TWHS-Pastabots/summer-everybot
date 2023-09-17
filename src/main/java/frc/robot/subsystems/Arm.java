package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Misc;
import frc.robot.Ports;

public class Arm {
    private static Arm instance;

    private ArmState state = ArmState.RETRACTED;
    private ArmControl controlState = ArmControl.MANUAL;
    private ControlSpeed controlSpeed = ControlSpeed.FULL;

    private PIDController armPID = new PIDController(2.5, 1, 0.0);
    private PIDController lowerArmPID = new PIDController(1.0, 0.0, 0.0);

    private CANSparkMax armController;
    private CANSparkMax lowArmController;

    private static final double MAX_VOLTS = 2;

    public enum ArmState {
        RETRACTED(14, 6),
        EXTENDED(-18, 2),
        GROUND_INTAKE(1, 2);

        public final double poseU, poseL;

        private ArmState(double poseU, double poseL) {
            this.poseU = poseU;
            this.poseL = poseL;
        }
    }

    public enum ArmControl {
        MANUAL,
        PID,
    }

    public enum ControlSpeed {
        FINE(0.5),
        FULL(1.0);

        public final double speed;

        private ControlSpeed(double speed) {
            this.speed = speed;
        }
    }

    public Arm() {
        armController = new CANSparkMax(Ports.ARM, MotorType.kBrushless);
        lowArmController = new CANSparkMax(Ports.LOWER_ARM, MotorType.kBrushless);

        armController.setInverted(false);
        armController.burnFlash();

        lowArmController.setInverted(true);
        lowArmController.burnFlash();
    }

    public void update(double lowerPower, double upperPower) {
        SmartDashboard.putNumber("ARM POSITION", armController.getEncoder().getPosition());
        SmartDashboard.putNumber("LOWER ARM POSITION", lowArmController.getEncoder().getPosition());

        if (controlState == ArmControl.MANUAL) {
            armController.set(upperPower * controlSpeed.speed);
            lowArmController.set(lowerPower * controlSpeed.speed);
        } else if (controlState == ArmControl.PID) {
            double reqPowerUpper = armPID.calculate(armController.getEncoder().getPosition(), state.poseU);
            double reqPowerLower = lowerArmPID.calculate(lowArmController.getEncoder().getPosition(), state.poseL);

            reqPowerUpper = Misc.clamp(reqPowerUpper, -MAX_VOLTS, MAX_VOLTS);
            reqPowerLower = Misc.clamp(reqPowerLower, -MAX_VOLTS, MAX_VOLTS);

            armController.setVoltage(reqPowerUpper);
            lowArmController.setVoltage(reqPowerLower);
        }
    }

    public void setState(ArmState state) {
        this.state = state;
    }

    public void setControlState(ArmControl controlState) {
        this.controlState = controlState;
    }

    public void setControlSpeed(ControlSpeed controlSpeed) {
        this.controlSpeed = controlSpeed;
    }

    public double getArmPose() {
        return armController.getEncoder().getPosition();
    }

    public static Arm getInstance() {
        if (instance == null) {
            instance = new Arm();
        }
        return instance;
    }
}
