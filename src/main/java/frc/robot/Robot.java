// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.subsystems.Drivebase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Arm.*;
import frc.robot.subsystems.Drivebase.DriveSpeed;
import frc.robot.subsystems.Arm;
import frc.robot.auton.sequences.*;
import edu.wpi.first.wpilibj.SPI;
import com.kauailabs.navx.frc.AHRS;

public class Robot extends TimedRobot {
    private PS4Controller driver; // blue
    private XboxController operator; // red

    private Drivebase drivebase;
    private Intake intake;
    private Arm arm;

    private boolean outtake;
    private boolean cycle;
    private boolean manual;

    private static final String kDefaultAuto = "Test1";
    private static final String kCustomAuto = "Test2";
    private String m_autoSelected;
    private final SendableChooser<String> m_chooser = new SendableChooser<>();

    private Test1 test1;
    private Test2 test2;

    private static final AHRS gyro = new AHRS(SPI.Port.kMXP);

    private RobotMode robotMode = RobotMode.EXHIBITION;

    public enum RobotMode {
        COMPETITION,
        EXHIBITION;
    }

    @Override
    public void robotInit() {

        m_chooser.setDefaultOption("Test1", kDefaultAuto);
        m_chooser.addOption("Test2", kCustomAuto);
        SmartDashboard.putData("AUTON SEQUENCES", m_chooser);

        drivebase = Drivebase.getInstance();
        intake = Intake.getInstance();
        arm = Arm.getInstance();
        gyro.calibrate();
    }

    @Override
    public void robotPeriodic() {
        SmartDashboard.putString("Robot mode", robotMode.toString());
        SmartDashboard.putBoolean("Manual:", manual);
        SmartDashboard.putNumber("Gyro Angle:", gyro.getAngle());
    }

    @Override
    public void autonomousInit() {
        m_autoSelected = m_chooser.getSelected();
        System.out.println("Auto selected: " + m_autoSelected);

        arm.setControlState(ArmControlState.PID);

        test1 = new Test1();
        test2 = new Test2();

        test1.initialize();
        test2.initialize();

    }

    @Override
    public void autonomousPeriodic() {
        arm.update(0, 0);

        switch (m_autoSelected) {
            case kCustomAuto:
                test2.execute();
                break;
            case kDefaultAuto:
            default:
                test1.execute();
                break;
        }

    }

    @Override
    public void teleopInit() {
        driver = new PS4Controller(0);
        operator = new XboxController(1);
    }

    @Override
    public void teleopPeriodic() {

        if (operator.getRawButton(Controller.XBOX_RB)) {
            robotMode = RobotMode.COMPETITION;
        } else if (operator.getRawButton(Controller.XBOX_SHARE)) {
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
            if (operator.getRawButton(Controller.XBOX_LB)) {
                arm.setControlSpeed(ArmControlSpeed.FINE);
            } else {
                arm.setControlSpeed(ArmControlSpeed.FULL);
            }

            // manage arm control states
            if (driver.getTriangleButtonPressed()) {
                if (arm.controlState == ArmControlState.MANUAL) {
                    arm.setControlState(ArmControlState.PID);
                    manual = false;
                } else {
                    arm.setControlState(ArmControlState.MANUAL);
                    manual = true;
                }
            }

            // // manage arm PID states & update
            // // the logic for whether or not the PID/manual mode actually runs is in
            // Arm.java

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

            /* Joystick */

            // arm
            if (operator.getRawButton(Controller.XBOX_x)) {
                arm.setState(ArmState.SHOOT);
            } else if (operator.getRawButton(Controller.XBOX_Y)) {
                arm.setState(ArmState.GROUND_INTAKE);
            } else if (operator.getRawButton(Controller.XBOX_LB)) {
                arm.setState(ArmState.RETRACTED);
            }

            if (arm.state == ArmState.RETRACTED) {
                drivebase.drive(operator.getLeftY() * 0.5, operator.getLeftX() * 0.4);
            } else {
                drivebase.drive(0, 0);
            }

            if (arm.state != ArmState.RETRACTED) {
                outtake = operator.getRawButton(Controller.XBOX_A);
            } else {
                outtake = false;
            }

            boolean intakeButton = operator.getRawButton(Controller.XBOX_B);
            intake.update(outtake, intakeButton);

            /* Drive Controls */

            // slow driving while holding left bumper, fast while holding right bumper
            if (driver.getRawButton(Controller.PS_L1)) {
                drivebase.setDriveSpeed(DriveSpeed.SLOW);
            } else {
                drivebase.setDriveSpeed(DriveSpeed.FAST);
            }

            arm.update(0, 0);

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
