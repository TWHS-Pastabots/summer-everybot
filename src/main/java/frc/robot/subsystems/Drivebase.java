package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.util.sendable.Sendable;

public class Drivebase extends SubsystemBase {
    
    private VictorSPX rightFrontVictor; 
    private VictorSPX rightBackVictor;
    private MotorControllerGroup rightMotorControl;

    private VictorSPX leftBackVictor;
    private VictorSPX leftFrontVictor;
    private MotorControllerGroup leftMotorControl;
    
    private DifferentialDrive drive;
    
    public Drivebase() {   
        rightFrontVictor = new VictorSPX(0);
            addChild("rightFrontVictor", (Sendable) rightFrontVictor);
            rightFrontVictor.setInverted(false);

        rightBackVictor = new VictorSPX(1);
            addChild("rightBackVictor", (Sendable) rightBackVictor);
            rightBackVictor.setInverted(false);
        
        leftBackVictor = new VictorSPX(2);
            addChild("leftBackVictor", (Sendable) leftBackVictor);
            leftBackVictor.setInverted(false);
        
        leftFrontVictor = new VictorSPX(3);
            addChild("leftFrontVictor", (Sendable) leftFrontVictor);
            leftFrontVictor.setInverted(false);

        rightMotorControl = new MotorControllerGroup((MotorController)rightBackVictor, (MotorController)rightFrontVictor);  
        leftMotorControl = new MotorControllerGroup((MotorController)leftFrontVictor, (MotorController)leftBackVictor);

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
