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
    private PIDController lowerArmPID = new PIDController(0.0, 0.0, 0.0);
    private double reqPositionU = 7;
    private double reqPositionL = 4;

    private static CANSparkMax armController;
    private static CANSparkMax leftlowArmController;
    private static CANSparkMax rightlowArmController;

    public enum ArmState {
        RETRACTED,
        EXTENDED,
        GROUND_INTAKE_DOWN,
        GROUND_INTAKE_UP
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
        leftlowArmController.setInverted(false);
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
        if (state == ArmState.RETRACTED) {
            reqPositionU = 14;
        } else if (state == ArmState.EXTENDED) {
            reqPositionU = -18;
        }

        double reqPowerU = armPID.calculate(armController.getEncoder().getPosition(), reqPositionU);
        double reqpowerL = lowerArmPID.calculate(leftlowArmController.getEncoder().getPosition(), reqPositionL);

        SmartDashboard.putNumber("INTAKE POSITION", leftlowArmController.getEncoder().getPosition());
        if (state == ArmState.GROUND_INTAKE_DOWN) {
            reqPositionL = 6;
        } else if (state == ArmState.GROUND_INTAKE_UP) {
            reqPositionL = 2;
        }

        leftlowArmController.setVoltage(reqpowerL);
        rightlowArmController.setVoltage(reqpowerL);

        armController.setVoltage(reqPowerU);
    }

    public void setState(ArmState state) {
        this.state = state;
    }

    public static Arm getInstance() {
        if (instance == null) {
            instance = new Arm();
        }
        return instance;
    }
}
