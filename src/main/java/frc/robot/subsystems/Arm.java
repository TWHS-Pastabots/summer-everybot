package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Ports;

public class Arm {
    private static Arm instance;

    private ArmState state = ArmState.RETRACTED;
    private ArmControl cstate = ArmControl.PID;
    private PIDController armPID = new PIDController(2.5, 1, 0.0);
    private PIDController lowerArmPID = new PIDController(1.0, 0.0, 0.0);

    private static CANSparkMax armController;
    private static CANSparkMax leftlowArmController;
    private static CANSparkMax rightlowArmController;

    public enum ArmState {
        RETRACTED(14, 6),
        EXTENDED(-18, 2),
        GROUND_INTAKE(1, 2),
        SCORE(14, 1),
        TEST1(-2, 8),
        TEST2(-2, 4);

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

    public Arm() {
        armController = new CANSparkMax(Ports.ARM, MotorType.kBrushless);
        leftlowArmController = new CANSparkMax(Ports.ARM_LOWER_LEFT, MotorType.kBrushless);
        rightlowArmController = new CANSparkMax(Ports.ARM_LOWER_RIGHT, MotorType.kBrushless);

        armController.setInverted(false);
        armController.setIdleMode(IdleMode.kBrake);
        armController.setSmartCurrentLimit(25);
        armController.burnFlash();

        rightlowArmController.setInverted(true);
        rightlowArmController.burnFlash();
    }

    private static final double MAX_V_L = 2;

    public void update(double lowerPower, double upperpower) {
        SmartDashboard.putNumber("ARM POSITION", armController.getEncoder().getPosition());

        if (cstate == ArmControl.MANUAL) {
            armController.setVoltage(upperpower);
            rightlowArmController.setVoltage(lowerPower);
        }

        if (cstate == ArmControl.PID) {
            double reqPowerU = armPID.calculate(armController.getEncoder().getPosition(),
                    state.poseU);
            double reqPowerL = lowerPower * 2;
            lowerArmPID.calculate(leftlowArmController.getEncoder().getPosition(),
                    state.poseL);

            SmartDashboard.putNumber("LOWER ARM POSITION LEFT", leftlowArmController.getEncoder().getPosition());
            SmartDashboard.putNumber("LOWER ARM POSITION RIGHT", rightlowArmController.getEncoder().getPosition());

            leftlowArmController.setVoltage(reqPowerL * 0.2);
            rightlowArmController.follow(leftlowArmController, true);

            armController.setVoltage(reqPowerU);

            reqPowerL = Math.max(-MAX_V_L, reqPowerL);
            reqPowerL = Math.min(MAX_V_L, reqPowerL);

            leftlowArmController.setVoltage(reqPowerL);
            rightlowArmController.setVoltage(reqPowerL);
        }
    }

    public void setState(ArmState state) {
        this.state = state;
    }

    public void setControlState(ArmControl cstate) {
        this.cstate = cstate;
    }

    public static Arm getInstance() {
        if (instance == null) {
            instance = new Arm();
        }
        return instance;
    }
}
