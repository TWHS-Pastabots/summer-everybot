package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Drivebase extends SubsystemBase {
    
    private VictorSPX rightVictor; 
    private MotorControllerGroup rightMotorControl;

    private VictorSPX leftVictor;
    private MotorControllerGroup leftMotorControl;
    
    
    private DifferentialDrive drive;
    
    
    public Drivebase() {   

        rightVictor = new VictorSPX(0);
            addChild("rightVictor", (Sendable) rightVictor);
            rightVictor.setInverted(false);
 

        leftVictor = new VictorSPX(1);
            addChild("leftVictor", (Sendable) leftVictor);
            leftVictor.setInverted(false);

        rightMotorControl = new MotorControllerGroup((MotorController)rightVictor);  
        leftMotorControl = new MotorControllerGroup((MotorController)leftVictor);

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
