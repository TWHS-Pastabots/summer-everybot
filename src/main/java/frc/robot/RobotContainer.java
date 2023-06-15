// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PS4Controller;
import frc.robot.commands.TankDrive;
import frc.robot.subsystems.Drivebase;
import frc.robot.commands.drivetrain.DriveCommand;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

    private final PS4Controller driveController;
    private final PS4Controller operatorController;
    private final Drivebase drivebase;
    private final Arm armBulid;
    private final Intake intakeBulid;
    private final TankDrive tankDrive;
    private final MoveIntake intakeMove;
    private final MoveArm armMove;

    
    public RobotContainer() {
        driveController = new PS4Controller(0);
        operatorController = new PS4Controller(1);
        drivebase = new Drivebase();
        armBulid = new Arm();
        intakeBulid = new Intake();
        tankDrive = new TankDrive(drivebase, driveController);
        armMove = new MoveArm(armBulid,operatorController);
        intakeMove = new MoveIntake(intakeBulid,operatorController);
        configureButtonBindings();
        defaultCommand();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
     * it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() { 
        
         
            
    }

    private void defaultCommand () {
        drivebase.setDefaultCommand(tankDrive);
        armBulid.setDefaultCommand(armMove);
        intakeBulid.setDefaultCommand(intakeMove);

        
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    
     /*
     public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return m_autoCommand;
    }
     */
}
