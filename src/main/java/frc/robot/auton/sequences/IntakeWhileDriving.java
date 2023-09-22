package frc.robot.auton.sequences;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.auton.commands.AutoArmGroundIntake;
import frc.robot.auton.commands.AutoDrive;
import frc.robot.auton.commands.AutoIntake;

public class IntakeWhileDriving extends ParallelCommandGroup {
  /** Creates a new IntakeWhileDriving. */
  public IntakeWhileDriving() {
    addCommands(
        new AutoArmGroundIntake(),
        new AutoIntake(3),
        new AutoDrive(0.75, 0, 1.0));
  }
}
