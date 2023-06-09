package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class Tankdrive extends CommandBase {

    public final DriveBase m_drivebase;

    public Tankdrive (DriveBase drivebase) {   
        m_drivebase = drivebase;
        addRequirements(m_drivebase);
    }
    @Override
    public void initialize () {
    }
    
    @Override
    public void execute() {
        m_drivebase.drive(m_left.getAsDouble(), m_right.getAsDouble());
    }
    @Override
    public void end () {
        m_drivebase.drive(0.0, 0.0); 
    }
    @Override
    public boolean isFinished () {
        return false;
    }
    
    @Override
    public void runsWhenDisabled(){
        return false; 
    }   
}