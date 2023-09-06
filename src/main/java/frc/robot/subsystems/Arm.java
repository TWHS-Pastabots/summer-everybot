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
    private PIDController armPID = new PIDController(2.5, 1, 0.0);
    private PIDController lowerArmPID = new PIDController(1.0, 0.0, 0.0);

    private static CANSparkMax armController;
    private static CANSparkMax leftlowArmController;
    private static CANSparkMax rightlowArmController;

    public enum ArmState {
        RETRACTED(14, 6),
        EXTENDED(-18, 2);

        public final double poseU, poseL;

        private ArmState(double poseU, double poseL) {
            this.poseU = poseU;
            this.poseL = poseL;
        }
    }

    public enum LowerArmState {
        GROUND_INTAKE_DOWN,
        GROUND_INTAKE_UP,
    }

    public Arm() {
        armController = new CANSparkMax(Ports.ARM, MotorType.kBrushless);
        leftlowArmController = new CANSparkMax(Ports.ARM_LOWER_LEFT, MotorType.kBrushless);
        rightlowArmController = new CANSparkMax(Ports.ARM_LOWER_RIGHT, MotorType.kBrushless);

        armController.setInverted(false);
        armController.setIdleMode(IdleMode.kBrake);
        armController.setSmartCurrentLimit(25);
        armController.burnFlash();

        // check inverted
        leftlowArmController.setInverted(true);
        leftlowArmController.setIdleMode(IdleMode.kBrake);
        leftlowArmController.setSmartCurrentLimit(25);
        leftlowArmController.burnFlash();

        // check inverted
        rightlowArmController.setInverted(false);
        rightlowArmController.setIdleMode(IdleMode.kBrake);
        rightlowArmController.setSmartCurrentLimit(25);
        rightlowArmController.burnFlash();
    }

    public void update() {
        SmartDashboard.putNumber("ARM POSITION", armController.getEncoder().getPosition());

        double reqPowerU = armPID.calculate(armController.getEncoder().getPosition(), state.poseU);
        double reqpowerL = lowerArmPID.calculate(leftlowArmController.getEncoder().getPosition(), state.poseL);

        SmartDashboard.putNumber("LOWER ARM POSITION LEFT", leftlowArmController.getEncoder().getPosition());
        SmartDashboard.putNumber("LOWER ARM POSITION right", rightlowArmController.getEncoder().getPosition());

        // leftlowArmController.setVoltage(reqpowerL);
        // rightlowArmController.follow(leftlowArmController, true);

        // armController.setVoltage(reqPowerU);
    }

    public void setState(ArmState state) {
        this.state = state;
    }

    public void setLowPower(double power) {
        leftlowArmController.set(power * 0.75);
        rightlowArmController.set(power * 0.75);
    }

    // public void setLowerState(LowerArmState lowstate) {
    // this.lowstate = lowstate;
    // }

    public static Arm getInstance() {
        if (instance == null) {
            instance = new Arm();
        }
        return instance;
    }
}
