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

    private boolean outtake;
    private boolean cycle;

    private Anshton anshton;
    private ChargingStation chargingStation;
    private Bump bump;

    private RobotMode robotMode = RobotMode.EXHIBITION;

    public enum RobotMode {
        COMPETITION,
        EXHIBITION;
    }

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
        arm.setControlState(ArmControlState.PID);

        anshton = new Anshton();
        chargingStation = new ChargingStation();
        bump = new Bump();
        chargingStation.initialize();
        anshton.initialize();
        bump.initialize();

    }

    @Override
    public void autonomousPeriodic() {
        arm.update(0, 0);
        // chargingStation.execute();
        // bump.execute();
        anshton.execute();

    }

    @Override
    public void teleopInit() {
        driver = new PS4Controller(0);
        operator = new PS4Controller(1);
    }

    @Override
    public void teleopPeriodic() {

        if (driver.getRawButton(Controller.PS_CIRCLE)) {
            robotMode = RobotMode.COMPETITION;
        } else if (driver.getRawButton(Controller.PS_SQUARE)) {
            robotMode = RobotMode.EXHIBITION;
        }

        if (robotMode == RobotMode.COMPETITION) {
            /* Drive Controls */

            // slow driving while holding left bumper, fast while holding right bumper
            if (driver.getRawButton(Controller.PS_L1)) {
                drivebase.setDriveSpeed(DriveSpeed.SLOW);
            } else {
                drivebase.setDriveSpeed(DriveSpeed.FAST);
            }

            double forward = driver.getRawAxis(Controller.PS_AXIS_RIGHT_Y);
            double turn = driver.getRawAxis(Controller.PS_AXIS_LEFT_X);

            drivebase.drive(forward, turn);

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
            } else if (operator.getRawButtonPressed(Controller.PS_TRIANGLE)) {
                if (cycle) {
                    arm.setState(ArmState.MID);
                    cycle = false;
                } else {
                    arm.setState(ArmState.LOW);
                    cycle = true;
                }
            }

            arm.update(operator.getRawAxis(Controller.PS_AXIS_RIGHT_Y) * .5,
                    operator.getRawAxis(Controller.PS_AXIS_LEFT_Y) * .5);
        } else {

            /* Drive Controls */

            // slow driving while holding left bumper, fast while holding right bumper
            if (driver.getRawButton(Controller.PS_L1)) {
                drivebase.setDriveSpeed(DriveSpeed.SLOW);
            } else {
                drivebase.setDriveSpeed(DriveSpeed.FAST);
            }

            double turn = driver.getRawAxis(Controller.PS_AXIS_LEFT_X);

            drivebase.drive(0, turn * 0.4);

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

            if (operator.getRawButton(Controller.PS_CROSS)) {
                arm.setState(ArmState.RETRACTED);
            } else if (operator.getRawButtonPressed(Controller.PS_TRIANGLE)) {
                arm.setState(ArmState.MID);
            } else if (operator.getRawButton(Controller.PS_R1)) {
                arm.setState(ArmState.SHOOT);
            }

            arm.update(operator.getRawAxis(Controller.PS_AXIS_RIGHT_Y) * .5,
                    operator.getRawAxis(Controller.PS_AXIS_LEFT_Y) * .5);

        }

        /* Intake Controls */

        if (arm.state != ArmState.RETRACTED) {
            outtake = operator.getRawButton(Controller.PS_CIRCLE);
        } else {
            outtake = false;
        }
        boolean intakeButton = operator.getRawButton(Controller.PS_SQUARE);

        intake.update(outtake, intakeButton);
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
