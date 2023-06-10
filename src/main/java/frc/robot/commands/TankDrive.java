package frc.robot.commands;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivebase;


public class TankDrive extends CommandBase{
    
    private final Drivebase m_drivebase;
    private final PS4Controller driveController;
    private SlewRateLimiter leftLimiter= new SlewRateLimiter(1);
    private SlewRateLimiter rightLimiter = new SlewRateLimiter(1);
    private double m_left;
    private double m_right;

    public TankDrive (Drivebase drivebase, PS4Controller driveController) {   
        m_drivebase = drivebase;
        this.driveController = driveController;

        addRequirements(m_drivebase);
    }

    @Override
    public void initialize () {
        m_left = 0;
        m_right = 0;
    }
    
    @Override
    public void execute() {
        m_left = driveController.getRightY() + driveController.getRightX();
        m_right = driveController.getRightY() - driveController.getRightX();
        m_drivebase.drive(leftLimiter.calculate(m_left), rightLimiter.calculate(m_right));
    }

    @Override
    public void end (boolean interrupted) {
        m_drivebase.drive(0.0, 0.0); 
    }

    @Override
    public boolean isFinished () {
        return false;
    }
    
    @Override
    public boolean runsWhenDisabled(){
        return false; 
    }   
}