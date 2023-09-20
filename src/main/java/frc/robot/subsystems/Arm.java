package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Misc;
import frc.robot.Ports;

public class Arm {
    private static Arm instance;

    public ArmControlState controlState = ArmControlState.PID;

    private ArmControlSpeed controlSpeed = ArmControlSpeed.FULL;

    private PIDController armPID = new PIDController(1.3, 0, 0.0);
    private PIDController lowerArmPID = new PIDController(.4, 0.0, 0.0);
    // ^alternative p term = .05

    private CANSparkMax armController;
    private CANSparkMax lowArmController;

    public ArmState state = ArmState.RETRACTED;

    public enum ArmState {
        RETRACTED(0, -10, true, 5),
        EXTENDED(-37, -30, true, 6),
        GROUND_INTAKE(-16, 50, true, 4),
        SHOOT(-37, -75, true, 5);
        // - low values move the arm higher
        // + high values move the arm lower

        public final double poseU, poseL, volts;
        public final boolean lowerFirst;

        private ArmState(double poseU, double poseL, boolean lowerFirst, double volts) {
            this.poseU = poseU;
            this.poseL = poseL;
            this.volts = volts;
            this.lowerFirst = lowerFirst;
        }
    }

    public enum ArmControlState {
        MANUAL,
        PID,
    }

    public enum ArmControlSpeed {
        FINE(0.1),
        FULL(0.25);

        public final double speed;

        private ArmControlSpeed(double speed) {
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

        if (controlState == ArmControlState.MANUAL) {
            armController.set(upperPower * controlSpeed.speed);
            lowArmController.set(lowerPower * controlSpeed.speed);
        } else if (controlState == ArmControlState.PID) {
            double reqPowerUpper = armPID.calculate(armController.getEncoder().getPosition(), state.poseU);
            double reqPowerLower = lowerArmPID.calculate(lowArmController.getEncoder().getPosition(), state.poseL);

            reqPowerUpper = Misc.clamp(reqPowerUpper, -state.volts, state.volts);
            reqPowerLower = Misc.clamp(reqPowerLower, -state.volts, state.volts);

            SmartDashboard.putNumber("Upper Arm Volts", reqPowerUpper);
            SmartDashboard.putNumber("Lower Arm Volts", reqPowerLower);

            if (state.lowerFirst) {
                if (Math.abs(getLowerPose() - state.poseL) <= 2) {
                    armController.setVoltage(reqPowerUpper);
                }
                lowArmController.setVoltage(reqPowerLower);
            } else {
                if (Math.abs(getUpperPose() - state.poseU) <= 2) {
                    lowArmController.setVoltage(reqPowerLower);
                }
                armController.setVoltage(reqPowerUpper);
            }
        }

    }

    public void setState(ArmState state) {

        this.state = state;
    }

    public void setControlState(ArmControlState controlState) {
        this.controlState = controlState;
    }

    public void setControlSpeed(ArmControlSpeed controlSpeed) {
        this.controlSpeed = controlSpeed;
    }

    public double getUpperPose() {
        return armController.getEncoder().getPosition();
    }

    public double getLowerPose() {
        return lowArmController.getEncoder().getPosition();
    }

    public static Arm getInstance() {
        if (instance == null) {
            instance = new Arm();
        }
        return instance;
    }
}