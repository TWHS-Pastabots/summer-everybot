// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.auton.sequences;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.auton.commands.*;

public class ChargingStation extends SequentialCommandGroup {
  /** Creates a new ChargimgStation. */
  public ChargingStation() {

    addCommands(
        new ScorePreLoaded(),
        new AutoDrive(0, 0, 60));
  }
}
