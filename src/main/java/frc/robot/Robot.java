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
//import frc.robot.subsystems.Arm.ArmState;
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
  private String lastGP;
  private Drivebase drivebase;
  private Intake intake;
  private Arm arm;

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

    // boolean sparkFailure = false;
    // boolean victorFailure = false;

    // CANSparkMax[] sparkMaxes = { Arm.armController,
    // Drivebase.leftSparkController, Drivebase.rightSparkController,
    // Intake.intakeController };
    // VictorSPX[] victors = { Drivebase.leftVictorController,
    // Drivebase.rightVictorController };

    // for (CANSparkMax sm : sparkMaxes) {
    // sm.set(0.001);

    // if (sm.getAppliedOutput() < 0.0005) {
    // sparkFailure = true;
    // }

    // SmartDashboard.putNumber(sm.getDeviceId() + " Spark Velocity",
    // sm.getEncoder().getVelocity());
    // SmartDashboard.putNumber(sm.getDeviceId() + " Spark Temperature",
    // sm.getMotorTemperature());
    // }
    // for (VictorSPX vs : victors) {
    // vs.set(ControlMode.PercentOutput, 0.0001);

    // if (vs.getMotorOutputPercent() <= 0.0005) {
    // victorFailure = true;
    // }

    // SmartDashboard.putNumber(vs.getDeviceID() + " Victor Temperature",
    // vs.getTemperature());
    // }

    // SmartDashboard.putBoolean("Spark Failure", sparkFailure);
    // SmartDashboard.putBoolean("Victor Failure", victorFailure);
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
    // drive
    drivebase.drive(-driveController.getLeftY(), driveController.getRightY());

    // intake
    if (operatorController.getCircleButton()) {
      intake.setState(IntakeState.INTAKE_CO);
      lastGP = "Cone";
    } else if (operatorController.getTriangleButton()) {
      intake.setState(IntakeState.INTAKE_CU);
      lastGP = "Cube";
    } else if (lastGP == "Cone") {
      intake.setState(IntakeState.HOLD_CONE);
    } else if (lastGP == "Cube") {
      intake.setState(IntakeState.HOLD_CUBE);
    } else if (operatorController.getSquareButton() & lastGP == "Cone") {
      intake.setState(IntakeState.OUTAKE_CO);
    } else if (operatorController.getSquareButton() & lastGP == "Cube") {
      intake.setState(IntakeState.OUTAKE_CU);
    } else if (operatorController.getSquareButton() & lastGP == null) {
      intake.setState(IntakeState.OFF);
    }
    intake.update();

    // arm
    if (operatorController.getR1Button()) {
      arm.setState(ArmState.EXTENDED);
    } else if (operatorController.getL1Button()) {
      arm.setState(ArmState.RETRACTED);
    }
    arm.update();
    // arm.setPower(operatorController.getLeftY());
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