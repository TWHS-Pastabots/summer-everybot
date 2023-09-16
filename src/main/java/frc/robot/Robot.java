// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.hal.simulation.RoboRioDataJNI;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Drivebase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Arm.ArmControl;
import frc.robot.subsystems.Arm.ArmState;
import frc.robot.subsystems.Arm.ControlSpeed;
import frc.robot.subsystems.Drivebase.DriveSpeed;
import frc.robot.subsystems.Intake.IntakeState;
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
    private static final String kDefaultAuto = "Default";
    private static final String kCustomAuto = "My Auto";
    private String m_autoSelected;
    private final SendableChooser<String> m_chooser = new SendableChooser<>();

    private PS4Controller driver;
    private PS4Controller operator;

    private Drivebase drivebase;
    private Intake intake;
    private Arm arm;

    private Anshton anshton;

    private boolean manualArm = true;

    @Override
    public void robotInit() {
        m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
        m_chooser.addOption("My Auto", kCustomAuto);

        drivebase = Drivebase.getInstance();
        intake = Intake.getInstance();
        arm = Arm.getInstance();
    }

    @Override
    public void robotPeriodic() {
        SmartDashboard.putNumber("RIO Current", RoboRioDataJNI.getVInCurrent());
        SmartDashboard.putNumber("RIO Voltage", RoboRioDataJNI.getVInVoltage());
    }

    @Override
    public void autonomousInit() {
        m_autoSelected = m_chooser.getSelected();
        m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
        System.out.println("Auto selected: " + m_autoSelected);

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
        // drive

        if (driver.getSquareButton()) {
            drivebase.setDriveSpeed(DriveSpeed.SLOW);
        } else {
            drivebase.setDriveSpeed(DriveSpeed.FULL);
        }

        drivebase.drive(driver.getRightY(), driver.getLeftY());
        SmartDashboard.putNumber("Forward", driver.getRightY());
        SmartDashboard.putNumber("Turn", driver.getLeftX());

        // intake

        intake.update(operator.getTriangleButton(), operator.getSquareButton(), operator.getCircleButton());

        if (operator.getL1Button()) {
            intake.setState(IntakeState.HOLD_CONE);
        }

        // arm

        if (operator.getL1Button()) {
            arm.setControlSpeed(ControlSpeed.FINE);
        } else {
            arm.setControlSpeed(ControlSpeed.FULL);
        }

        // manual
        if (operator.getShareButtonPressed()) {
            manualArm = true;
        } else if (operator.getOptionsButtonPressed()) {
            manualArm = false;
        }

        if (manualArm) {
            arm.setControlState(ArmControl.MANUAL);
            arm.update(operator.getLeftY(), operator.getRightY());
        } else {
            // PID
            arm.setControlState(ArmControl.PID);

            if (operator.getR2Button()) {
                arm.setState(ArmState.EXTENDED);
            } else if (operator.getL2Button()) {
                arm.setState(ArmState.RETRACTED);
            }

            // lower arm
            if (operator.getR1Button()) {
                arm.setState(ArmState.GROUND_INTAKE);
            }
        }
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