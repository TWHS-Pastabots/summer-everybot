// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.subsystems.Drivebase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Arm.*;
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
    private PS4Controller driver; // blue
    private PS4Controller operator; // red

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
        if (driver.getR1Button()) {
            drivebase.setDriveSpeed(DriveSpeed.SLOW);
        } else {
            drivebase.setDriveSpeed(DriveSpeed.FULL);
        }

        drivebase.drive(driver.getRawAxis(Controller.PS_AXIS_RIGHT_Y), driver.getRawAxis(Controller.PS_AXIS_LEFT_X));

        /* Intake Controls */

        // separate these into different variables for readability
        boolean outtake = operator.getRawButton(Controller.PS_CIRCLE);
        boolean intakeButton = operator.getRawButton(Controller.PS_SQUARE);

        intake.update(outtake, intakeButton);

        /* Arm Controls */

        // finer control when holding L1
        if (operator.getL1Button()) {
            arm.setControlSpeed(ArmControlSpeed.FINE);
        } else {
            arm.setControlSpeed(ArmControlSpeed.FULL);
        }

        // manage arm control states
        if (driver.getTriangleButtonPressed()) {
            if (arm.controlState == ArmControlState.MANUAL) {
                arm.setControlState(ArmControlState.PID);
            } else {
                arm.setControlState(ArmControlState.MANUAL);
            }
        }

        // manage arm PID states & update
        // the logic for whether or not the PID/manual mode actually runs is in Arm.java

        if (operator.getRawButton(Controller.PS_R1)) {
            arm.setState(ArmState.EXTENDED);
        } else if (operator.getRawButton(Controller.PS_L1)) {
            arm.setState(ArmState.GROUND_INTAKE);
        } else if (operator.getRawButton(Controller.PS_CROSS)) {
            arm.setState(ArmState.RETRACTED);
        } else if (operator.getRawButton(Controller.PS_TRIANGLE)) {
            arm.setState(ArmState.SHOOT);
        }

        arm.update(operator.getRawAxis(Controller.PS_AXIS_RIGHT_Y), operator.getRawAxis(Controller.PS_AXIS_LEFT_Y));
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
