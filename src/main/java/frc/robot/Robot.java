// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//import com.ctre.phoenix.motorcontrol.ControlMode;
//import com.ctre.phoenix.motorcontrol.can.VictorSPX;
//import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Drivebase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Arm.ArmState;
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
  // private String lastGP;
  private Drivebase drivebase;
  private Intake intake;
  private Arm arm;

  private Anshton anshton;

  // private enum GamePiece {
  // CONE,
  // CUBE
  // }

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
    // switch (m_autoSelected) {
    // case kCustomAuto:
    // // Put custom auto code here
    // break;
    // case kDefaultAuto:
    // default:
    // // Put default auto code here
    // break;
    // }
  }

  @Override
  public void teleopInit() {
    driver = new PS4Controller(0);
    operator = new PS4Controller(1);
  }

  @Override
  public void teleopPeriodic() {
    // drive
    drivebase.drive(-driver.getLeftY(), driver.getRightX());
    // drivebase.update();

    // intake
    intake.update(operator.getCircleButton(), operator.getTriangleButton(), operator.getSquareButton());

    // arm
    if (operator.getR1Button()) {
      arm.setState(ArmState.EXTENDED);
    } else if (operator.getL1Button()) {
      arm.setState(ArmState.RETRACTED);
    }

    // lower arm
    // if (operator.getR2Button()) {
    // arm.setState(ArmState.EXTENDED);
    // } else if (operator.getL2Button()) {
    // arm.setState(ArmState.RETRACTED);
    // }

    arm.update(operator.getRightY());

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