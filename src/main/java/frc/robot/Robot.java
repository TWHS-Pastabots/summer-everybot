// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.subsystems.Drivebase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Arm.ArmControl;
import frc.robot.subsystems.Arm.ArmState;
import frc.robot.subsystems.Arm.ControlSpeed;
import frc.robot.subsystems.Drivebase.DriveSpeed;
import frc.robot.subsystems.Arm;
import frc.robot.auton.sequences.*;

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
    private PS4Controller driver;
    private PS4Controller operator;

    private Drivebase drivebase;
    private Intake intake;
    private Arm arm;

    private Anshton anshton;

    @Override
    public void robotInit() {
        drivebase = Drivebase.getInstance();
        intake = Intake.getInstance();
        arm = Arm.getInstance();
    }

    @Override
    public void robotPeriodic() {
    }

    @Override
    public void autonomousInit() {
        anshton = new Anshton();
        anshton.initialize();
    }

    @Override
    public void autonomousPeriodic() {
        anshton.execute();
    }

    @Override
    public void teleopInit() {
        driver = new PS4Controller(0);
        operator = new PS4Controller(1);
    }

    @Override
    public void teleopPeriodic() {
        /* Drive Controls */

        // slow driving while holding square
        if (driver.getSquareButton()) {
            drivebase.setDriveSpeed(DriveSpeed.SLOW);
        } else {
            drivebase.setDriveSpeed(DriveSpeed.FULL);
        }

        drivebase.drive(driver.getRightY(), driver.getLeftX());

        /* Intake Controls */

        // separate these into different variables for readability
        boolean intakeCone = operator.getTriangleButton();
        boolean intakeCube = operator.getSquareButton();
        boolean outtake = operator.getCircleButton();

        intake.update(intakeCone, intakeCube, outtake);

        /* Arm Controls */

        // finer control when holding L1
        if (operator.getL1Button()) {
            arm.setControlSpeed(ControlSpeed.FINE);
        } else {
            arm.setControlSpeed(ControlSpeed.FULL);
        }

        // manage arm control states
        if (operator.getShareButtonPressed()) {
            arm.setControlState(ArmControl.MANUAL);
        } else if (operator.getOptionsButtonPressed()) {
            arm.setControlState(ArmControl.PID);
        }

        // manage arm PID states & update
        // logic for whether or not the PID/manual mode actually runs is in Arm.java
        if (operator.getR2Button()) {
            arm.setState(ArmState.EXTENDED);
        } else if (operator.getL2Button()) {
            arm.setState(ArmState.RETRACTED);
        } else if (operator.getR1Button()) {
            arm.setState(ArmState.GROUND_INTAKE);
        }
        arm.update(operator.getLeftY(), operator.getRightY());
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void testInit() {
    }

    @Override
    public void testPeriodic() {
    }
}
