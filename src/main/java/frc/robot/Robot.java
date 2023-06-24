// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Drivebase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Arm.ArmState;
import frc.robot.subsystems.Intake.IntakeState;
import frc.robot.subsystems.Arm;

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

  private PS4Controller driveController;
  private PS4Controller operatorController;

  private Drivebase drivebase;
  private Intake intake;
  private GamePiece lastGamePiece;
  private Arm arm;

  private enum GamePiece {
    CONE,
    CUBE
  }

  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

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
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  @Override
  public void teleopInit() {
    driveController = new PS4Controller(0);
    operatorController = new PS4Controller(1);
  }

  @Override
  public void teleopPeriodic() {
    drivebase.drive(driveController.getRightX(), driveController.getLeftY());

    boolean squareButton = operatorController.getSquareButton();
    boolean circleButton = operatorController.getCircleButton();

    if (squareButton) {
      intake.setState(IntakeState.INTAKE);
      lastGamePiece = GamePiece.CUBE;
    } else if (circleButton) {
      intake.setState(IntakeState.OUTTAKE);
      lastGamePiece = GamePiece.CONE;
    } else if (lastGamePiece == GamePiece.CUBE)
      intake.setState(IntakeState.HOLD_CUBE);
    else if (lastGamePiece == GamePiece.CONE)
      intake.setState(IntakeState.HOLD_CONE);
    else
      intake.setState(IntakeState.OFF);
    intake.update();

    if (operatorController.getL2Button())
      arm.setState(ArmState.LOWER);
    else if (operatorController.getR2Button())
      arm.setState(ArmState.RAISE);
    arm.update();
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
