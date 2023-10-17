package frc.robot.auton.sequences;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.auton.commands.*;

public class Test1 extends SequentialCommandGroup {
  /** Creates a new Test. */
  public Test1() {

    addCommands(
        new AutoArmExtend());

  }
}
