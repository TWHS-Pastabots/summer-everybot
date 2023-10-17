package frc.robot.auton.sequences;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.auton.commands.AutoArmGroundIntake;

public class Test2 extends SequentialCommandGroup {
  public Test2() {
    addCommands(
        new AutoArmGroundIntake());
  }
}
