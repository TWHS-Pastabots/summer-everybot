// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import frc.robot.subsystems.Drivebase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import com.revrobotics.CANSparkMax.IdleMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.robot.commands.MoveArm;
import frc.robot.commands.MoveIntake;
import frc.robot.commands.TankDrive;



/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    
    private Command m_autonomousCommand;
    private RobotContainer m_robotContainer;

    /**
     * This function is run when the robot is first started up and should be used
     * for any
     * initialization code.
     */
    @Override
    public void robotInit() {
        // Instantiate our RobotContainer. This will perform all our button bindings,
        // and put our
        // autonomous chooser on the dashboard.
        m_robotContainer = new RobotContainer();

        m_chooser.setDefaultOption("do nothing", kNothingAuto);
        m_chooser.addOption("cone and mobility", kConeAuto);
        m_chooser.addOption("cube and mobility", kCubeAuto);
        SmartDashboard.putData("Auto choices", m_chooser);

        
    }

    public void setDriveMotors(double forward, double turn){
            SmartDashboard.putNumber("drive forward power (%)", forward);
            SmartDashboard.putNumber("drive turn power (%)", turn);

            double left = forward - turn;
            double right = forward + turn;

            SmartDashboard.putNumber("drive left power (%)", left);
            SmartDashboard.putNumber("drive right power (%)", right);


            leftSpark.set(Left);
            leftVictor.set(ControlMode.percent,Left);
            rightSpark.set(Right);
            rightVictor.set(ControlMode.percent, Right);
    }

     public void setArmMotor (double percent) {

        arm.set(percent);
        SmartDashboard.putNumber("arm power (%)", percent);
        SmartDashboard.putNumber("arm motor current (amps)", arm.getOutputCurrent()); 
        SmartDashboard.putNumber("arm motor temperature (C)", arm.getMotorTemperature());
    
    }
    public void setIntake(double percent, int amps){
            intake.set(percent);
            intake.setSmartCurrentLimit(amps);
            SmartDashboard.putNumber("Intake power (%)", percent);
            SmartDashboard.putNumber("Intake motor current (amps)" , Intake.getOutputCurrent() );
            SmartDashboard.putNumber("arm motor temperature (C)" , Intake.getMotorTemperature());


    }

    

    /**
     * This function is called every robot packet, no matter the mode. Use this for
     * items like
     * diagnostics that you want ran during disabled, autonomous, teleoperated and
     * test.
     *
     * <p>
     * This runs after the mode specific periodic functions, but before LiveWindow
     * and
     * SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        // Runs the Scheduler. This is responsible for polling buttons, adding
        // newly-scheduled
        // commands, running already-scheduled commands, removing finished or
        // interrupted commands,
        // and running subsystem periodic() methods. This must be called from the
        // robot's periodic
        // block in order for anything in the Command-based framework to work.
        
        CommandScheduler.getInstance().run();
    }

    /** This function is called once each time the robot enters Disabled mode. */
    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    /**
     * This autonomous runs the autonomous command selected by your
     * {@link RobotContainer} class.
     */
    @Override
    public void autonomousInit() {
        /*m_autonomousCommand = m_robotContainer.getAutonomousCommand();

        // schedule the autonomous command (example)
        if (m_autonomousCommand != null) {
            m_autonomousCommand.schedule();
        }
        */
    }

    /** This function is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {
        
    }

    static final int CONE = 1;
    static final int CUBE = 2;
    static final int NOTHING = 3;
    int lastGamePiece;

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (m_autonomousCommand != null) {
            m_autonomousCommand.cancel();
        }

        leftSpark.setIdleMode(IdleMode.kCoast);
        leftVictor.setNeutralMode(NeutralMode.Coast);
        rightSpark.setIdleMode(IdleMode.kCoast);
        leftVictor.setNeutralMode(NeutralMode.Coast);

        lastGamePiece = NOTHING;

    }

    /** This function is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {
        //WE HAVE TO FIGURE OUT THE CONTROLS WE ARE GOING TO BE USING FOR ARM AND INTAKE
        CommandScheduler.getInstance().run();
    }

    @Override
    public void testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }

    /** This function is called periodically during test mode. */
    @Override
    public void testPeriodic() {
    }
}
