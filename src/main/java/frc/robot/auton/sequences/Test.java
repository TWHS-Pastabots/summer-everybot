package frc.robot.auton.sequences;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.auton.commands.*;

public class Test extends SequentialCommandGroup {
  /** Creates a new Test. */
  public Test() {

    addCommands(
        new AutoDrive(-1, 0.023, 2),
        new AutoDrive(0, 0, 1),
        new AutoDrive(0, .5, 5)); // Gone to pickup

  }
}
