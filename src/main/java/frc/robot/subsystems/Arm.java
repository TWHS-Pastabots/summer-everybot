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




public class Arm extends SubsystemBase {
    
    private static Arm m_instance;
    private CANSparkMax m_armMotor;
    
    
    public Arm() {   

        CANSparkMax arm = new CANSparkMax(5,MotorType.kBrushless);

         arm.setInverted(true);
    }
    public static arm getInstance(){
        if (m_instance == null){
            m_instance = new arm;
        }
        return m_instance;
    }

    public void runMotor(){
        m_armMotor.set(0.5);
    }
    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    @

    
}
