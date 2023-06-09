package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
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
        rightVictor.configVoltageCompSaturation(6);
        rightVictor.enableVoltageCompensation(true);
        rightSpark = new CANSparkMax(1, MotorType.kBrushed);
        rightSpark.setInverted(false);
        rightSpark.setSmartCurrentLimit(5);
        
    
        // Left motor group
        leftVictor = new VictorSPX(2);
        leftVictor.setInverted(true);
        leftVictor.configVoltageCompSaturation(6);
        leftVictor.enableVoltageCompensation(true);
        leftSpark = new CANSparkMax(3, MotorType.kBrushed);
        leftSpark.setInverted(true);
        leftSpark.setSmartCurrentLimit(5);

        rightSpark.setIdleMode(IdleMode.kCoast);
        rightVictor.setNeutralMode(NeutralMode.Coast);
        leftSpark.setIdleMode(IdleMode.kCoast);
        leftVictor.setNeutralMode(NeutralMode.Coast);



        
    }

    public void drive (double forward, double turn) {
        double leftOutput = forward;
        double rightOutput = turn;
        
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
