package frc.robot.auton.sequences;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.auton.commands.*;

public class Test extends SequentialCommandGroup {
  /** Creates a new Test. */
  public Test() {

    addCommands(
        new AutoDrive(-0.75, 0.015, 0.4),
        new AutoArmShoot(), // Shooting position
        new AutoOuttake(0.25), // Has scored the piece
        new AutoArmRetract(),
        new AutoDrive(-0.75, .024, 2.4), // Gone to pickup
        new AutoDrive(0, 0, 0.5),
        new AutoDrive(0, 1, 1.11643297543),
        new IntakeWhileDriving()); // Picks it up

  }
}
