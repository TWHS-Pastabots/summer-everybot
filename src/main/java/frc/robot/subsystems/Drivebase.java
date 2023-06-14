package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Drivebase extends SubsystemBase {
    
    private VictorSPX rightVictor;
    private CANSparkMax rightSpark;
    private MotorControllerGroup rightMotorControl;

    private VictorSPX leftVictor;
    private CANSparkMax leftSpark;
    private MotorControllerGroup leftMotorControl;
    
    
    private DifferentialDrive drive;
    
    
    public Drivebase() {   
        //Right motor group
        rightVictor = new VictorSPX(0);
            rightVictor.setInverted(false);
        rightSpark = new CANSparkMax(1, MotorType.kBrushed);
            rightSpark.setInverted(false);
        //Left motor group
        leftVictor = new VictorSPX(2);
            leftVictor.setInverted(true);
        leftSpark = new CANSparkMax(3, MotorType.kBrushed); 
            leftSpark.setInverted(true);

        rightMotorControl = new MotorControllerGroup((MotorController)rightVictor, (MotorController)rightSpark);  
        leftMotorControl = new MotorControllerGroup((MotorController)leftVictor, (MotorController)leftSpark);

        drive = new DifferentialDrive(rightMotorControl, leftMotorControl);
            addChild("drive", drive);
            
            drive.setSafetyEnabled(true);
            drive.setExpiration(0.1);
            drive.setMaxOutput(1.0);
    }


    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

    public void drive(double left, double right) {
        drive.tankDrive(left, right);
    }
}
