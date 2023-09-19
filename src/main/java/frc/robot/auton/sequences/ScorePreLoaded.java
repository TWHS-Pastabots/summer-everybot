package frc.robot.auton.sequences;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.auton.commands.*;

public class ScorePreLoaded extends ParallelCommandGroup {

  public ScorePreLoaded() {
    addCommands(
        new AutoDrive(0.75, 0, .2),
        new AutoArmExtend());
  }
}
