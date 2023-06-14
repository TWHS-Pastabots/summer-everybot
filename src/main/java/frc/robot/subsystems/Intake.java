package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import frc.robot.commands.TankDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;




public class Arm extends SubsystemBase {
    
    
    private CANSparkMax m_Intake;
    static final int INTAKE_CURRENT_LIMIT_A = 25;
    static final int INTAKE_HOLD_CURRENT_LIMIT_A = 5;
    static final double INTAKE_OUTPUT_POWER = 1.0;
    static final double INTAKE_HOLD_POWER = 0.07;
    
    public Intake() {   

        CANSparkMax Intake = new CANSparkMax(6,MotorType.kBrushless);
        Intake.setInverted(false);
        Intake.setIdleMode(IdleMode.kBrake);
    }

    public static intake getInstance() {
        if (m_instance == null){
            m_instance = new Intake;
        }
        return m_instance;
    }

   


    public void runMotor(){
        m_Intake.set(0.5);
    }
    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    @

    
}
