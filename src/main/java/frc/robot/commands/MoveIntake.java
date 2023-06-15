package frc.robot.commands;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivebase;
import frc.robot.subsystems.Arm;
import frc.robot;


public class MoveIntake extends CommandBase{
    
    
    private final PS4Controller operatorController; 
    private final Intake m_Intake;
    private double m_in;
    private double m_out;
    private boolean squareButton; 
    private boolean circleButton; 


    
    public MoveIntake (Intake intake, PS4Controller operatorController) {   
        
        m_Intake = intake;
        this.operatorController = operatorController;
        addRequirements(m_Intake);
        
    }

    @Override
    public void initialize () {
       m_in = 0.0;
       m_out = 0.0;
    }
    
    @Override
    public void execute() {
        boolean squareButton = PS4Controller.getRawButton();
        boolean circleButton = PS4Controller.getRawButton();
        double intakePower; 
        int intakeAmps; 

        if(squareButton){
            //Picks up cube or cone out
            intakePower = INTAKE_OUTPUT_POWER;
            intakeAmps = INTAKE_CURRENT_LIMIT_A;
            lastGamePiece = CUBE;
        }else if(circleButton){
            //Picks up cone or cube out
            intakePower = -INTAKE_OUTPUT_POWER;
            intakeAmps = INTAKE_CURRENT_LIMIT_A;
            lastGamePiece = CONE;
        }else if(lastGamePiece == CUBE){
            //holding a cube
            intakePower = INTAKE_HOLD_POWER;
            intakeAmps = INTAKE_HOLD_CURRENT_LIMIT_A;

        }else if(lastGamePiece == CONE){
            //holding a cone
            intakePower = -INTAKE_HOLD_POWER;
            intakeAmps = INTAKE_HOLD_CURRENT_LIMIT_A;
        }else{
            //if nothing
            intakePower = 0.0;
            intakeAmps = 0;
        }
        setIntake(intakePower,intakeAmps);
        

        
        
        
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