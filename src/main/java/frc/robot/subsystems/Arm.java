package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Ports;

public class Arm {
    private static Arm instance;

    private ArmState state = ArmState.RETRACTED;
    private ArmControl cstate = ArmControl.MANUAL;
    private ControlSpeed sstate = ControlSpeed.FULL;

    private PIDController armPID = new PIDController(2.5, 1, 0.0);
    private PIDController lowerArmPID = new PIDController(1.0, 0.0, 0.0);

    private static CANSparkMax armController;
    private static CANSparkMax lowArmController;

    private double MAX_SPEED = 1.0;
    private double tippingPoint = 8.0;

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
        PID;
    }

    public enum ControlSpeed {
        FINE,
        FULL;
    }

    public Arm() {

        armController = new CANSparkMax(Ports.ARM, MotorType.kBrushless);
        lowArmController = new CANSparkMax(Ports.LOWER_ARM, MotorType.kBrushless);

        armController.setInverted(false);
        armController.burnFlash();

        lowArmController.setInverted(true);
        lowArmController.burnFlash();
    }

    private static final double MAX_V_L = 2;

    public void update(double lowerPower, double upperPower) {

        SmartDashboard.putNumber("ARM POSITION", armController.getEncoder().getPosition());

        if (sstate == ControlSpeed.FINE) {
            MAX_SPEED = 0.5;
        } else {
            MAX_SPEED = 1.0;
        }

        if (cstate == ArmControl.MANUAL) {

            armController.set(upperPower * MAX_SPEED);
            lowArmController.set(lowerPower * MAX_SPEED);

        }

        double reqPowerU = armPID.calculate(armController.getEncoder().getPosition(), state.poseU);
        double reqPowerL = lowerArmPID.calculate(lowArmController.getEncoder().getPosition(), state.poseL);

        SmartDashboard.putNumber("LOWER ARM POSITION LEFT",
                lowArmController.getEncoder().getPosition());
        SmartDashboard.putNumber("LOWER ARM POSITION RIGHT",
                lowArmController.getEncoder().getPosition());

        reqPowerU = (Math.max(-MAX_V_L, reqPowerU)) * MAX_SPEED;
        reqPowerU = (Math.min(MAX_V_L, reqPowerU)) * MAX_SPEED;

        if (cstate == ArmControl.PID) {
            armController.setVoltage(reqPowerU);

            reqPowerL = (Math.max(-MAX_V_L, reqPowerL)) * MAX_SPEED;
            reqPowerL = (Math.min(MAX_V_L, reqPowerL)) * MAX_SPEED;

            // lowArmController.setVoltage(reqPowerL);
        }

        // stability
        if (armPosition() > tippingPoint && Drivebase.isDriving()) {
            setState(ArmState.RETRACTED);
        }

    }

    public void setState(ArmState state) {
        this.state = state;
    }

    public void setControlState(ArmControl cstate) {
        this.cstate = cstate;
    }

    public void setControlSpeed(ControlSpeed sstate) {
        this.sstate = sstate;
    }

    public double armPosition() {
        return armController.getEncoder().getPosition();
    }

    public static Arm getInstance() {
        if (instance == null) {
            instance = new Arm();
        }
        return instance;
    }
}