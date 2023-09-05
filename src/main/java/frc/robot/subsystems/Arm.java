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

    private ArmState state = ArmState.RETRACTED;
    private PIDController armPID = new PIDController(2.5, 1, 0.0);
    private double reqPosition = 7;

    private static CANSparkMax armController;
    private static CANSparkMax lowerArmController;

    public enum ArmState {
        RETRACTED,
        EXTENDED,
    }

    public Arm() {
        armController = new CANSparkMax(Ports.ARM, MotorType.kBrushless);
        lowerArmController = new CANSparkMax(Ports.ARM_LOWER, MotorType.kBrushless);

        armController.setInverted(false);
        armController.setIdleMode(IdleMode.kBrake);
        armController.setSmartCurrentLimit(25);
        armController.burnFlash();

        lowerArmController.setInverted(false);
        lowerArmController.setIdleMode(IdleMode.kBrake);
        lowerArmController.setSmartCurrentLimit(25);
        lowerArmController.burnFlash();
    }

    public void update() {
        SmartDashboard.putNumber("ARM POSITION", armController.getEncoder().getPosition());
        if (state == ArmState.RETRACTED) {
            reqPosition = 14;
        } else if (state == ArmState.EXTENDED) {
            reqPosition = -18;
        }

        double reqPower = armPID.calculate(armController.getEncoder().getPosition(), reqPosition);

        armController.setVoltage(reqPower);
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
