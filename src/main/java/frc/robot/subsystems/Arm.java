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
    
    private static Arm m_instance;
    private CANSparkMax m_armMotor;
    static final int ARM_CURRENT_LIMIT_A = 20;
    static final double ARM_OUTPUT_POWER = 0.4;

    
    public Arm() {   

        CANSparkMax arm = new CANSparkMax(5,MotorType.kBrushless);

            arm.setInverted(true);
            arm.setIdleMode(IdleMode.kBrake);
            arm.setSmartCurrentLimit(ARM_CURRENT_LIMIT_A);
    }

    public static arm getInstance() {
        if (m_instance == null){
            m_instance = new Arm;
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