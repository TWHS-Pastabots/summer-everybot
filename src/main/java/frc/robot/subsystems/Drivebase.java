package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;

public class Drivebase extends SubsystemBase {
    
    private PWMVictorSPX rightVictor; 
    private MotorControllerGroup rightMotorControl;

    private PWMVictorSPX leftVictor;
    private MotorControllerGroup leftMotorControl;
    
    
    private DifferentialDrive drive;
    
    
    public Drivebase() {   

        rightVictor = new PWMVictorSPX(0);
            addChild("rightVictor", rightVictor);
            rightVictor.setInverted(false);
 

        leftVictor = new PWMVictorSPX(1);
            addChild("leftVictor", leftVictor);
            leftVictor.setInverted(false);

        rightMotorControl = new MotorControllerGroup(rightVictor);  
        leftMotorControl = new MotorControllerGroup(leftVictor);

        
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
