package frc.robot.auton.sequences;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.auton.commands.*;

public class Bump extends SequentialCommandGroup {
  /** Creates a new Anshton. */
  public Bump() {
    addCommands(
        new ScorePreLoaded(),
        new AutoDrive(-0.5, 0.028, 2.4), // Gone to pickup
        new AutoDrive(0, 0, 60));
  }
}
