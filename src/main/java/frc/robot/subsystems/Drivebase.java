package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Drivebase {
    private VictorSPX rightVictor;
    private VictorSPX leftVictor;
    private CANSparkMax rightSpark;
    private CANSparkMax leftSpark;
    private static Drivebase instance;

    public Drivebase() {
        // Right motor group
        rightVictor = new VictorSPX(0);
        rightVictor.setInverted(false);
        rightSpark = new CANSparkMax(1, MotorType.kBrushed);
        rightSpark.setInverted(false);
        rightSpark.setSmartCurrentLimit(10);
        // Left motor group
        leftVictor = new VictorSPX(2);
        leftVictor.setInverted(true);
        leftSpark = new CANSparkMax(3, MotorType.kBrushed);
        leftSpark.setInverted(true);
        leftSpark.setSmartCurrentLimit(10);
        
    }

    public void drive (double y, double x) {
        double leftOutput = y + x;
        double rightOutput = y - x;
        
        leftVictor.set(ControlMode.PercentOutput, leftOutput);
        leftSpark.set(leftOutput);
        rightVictor.set(ControlMode.PercentOutput, rightOutput);
        rightSpark.set(rightOutput);
    }

    public static Drivebase getInstance() {
        if (instance == null) {
            instance = new Drivebase();
        }
        return instance;
    }
}
