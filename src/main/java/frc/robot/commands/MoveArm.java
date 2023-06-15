package frc.robot.commands;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivebase;
import frc.robot.subsystems.Arm;
import frc.robot;


public class MoveArm extends CommandBase{
    
    private final Arm m_Arm;
    private final PS4Controller operatorController; 
    private double m_up;
    private double m_down;
    
    public MoveArm (Arm arm, PS4Controller operatorController) {   
        m_Arm = arm;
        this.operatorController = operatorController;
        addRequirements(m_Arm);
        
    }

    @Override
    public void initialize () {
       m_up = 0; 
       m_down = 0;
    }
    
    @Override
    public void execute() {
        
        
        double armPower; 
        if (operatorController.getL2Axis(2)){
            //Lower
            armPower = -ARM_OUTPUT_POWER;
        }else if (operatorController.getR2Axis(3)){
            //Raise
            armPower = ARM_OUTPUT_POWER;
        }else{
            armPower = 0.0;
        }
        setArmMotor(armPower);
        
        
    }

    @Override
    public void end (boolean interrupted) {
       
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