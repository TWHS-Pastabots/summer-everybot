package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Misc;
import frc.robot.Ports;

public class Arm {
    private static Arm instance;

    private ArmControl controlState = ArmControl.PID;
    private ControlSpeed controlSpeed = ControlSpeed.FULL;

    private PIDController armPID = new PIDController(1, 0, 0.0);
    private PIDController lowerArmPID = new PIDController(1.1, 0.5, 0.0);

    private CANSparkMax armController;
    private CANSparkMax lowArmController;

    private static final double MAX_VOLTS = 2;

    public ArmState state = ArmState.RETRACTED;

    public enum ArmState {
        RETRACTED(0, 0),
        EXTENDED(-37, -7),
        GROUND_INTAKE_CONE(-16, 27),
        GROUND_INTAKE_CUBE(-25, 70);

        public final double poseU, poseL;

        private ArmState(double poseU, double poseL) {
            this.poseU = poseU;
            this.poseL = poseL;
        }

        public final ArmState next() {
            return values()[(ordinal() + 1) % values().length];
        }

        public final ArmState prev() {
            return values()[(ordinal() - 1 + values().length) % values().length];
        }
    }

    public enum ArmControl {
        MANUAL,
        PID,
    }

    public enum ControlSpeed {
        FINE(0.1),
        FULL(0.25);

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
        SmartDashboard.putString("Arm State", state.toString());

        if (controlState == ArmControl.MANUAL) {
            armController.set(upperPower * controlSpeed.speed);
            lowArmController.set(lowerPower * controlSpeed.speed);
        } else if (controlState == ArmControl.PID) {
            double reqPowerUpper = armPID.calculate(armController.getEncoder().getPosition(), state.poseU);
            double reqPowerLower = lowerArmPID.calculate(lowArmController.getEncoder().getPosition(), state.poseL);

            reqPowerUpper = Misc.clamp(reqPowerUpper, -MAX_VOLTS, MAX_VOLTS);
            reqPowerLower = Misc.clamp(reqPowerLower, -MAX_VOLTS, MAX_VOLTS);

            armController.setVoltage(reqPowerUpper);
            if (armController.getEncoder().getPosition() - 2 >= state.poseU) {
                lowArmController.setVoltage(reqPowerLower);
            }
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
