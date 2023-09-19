package frc.robot.auton.sequences;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.auton.commands.*;

public class Anshton extends SequentialCommandGroup {
    /** Creates a new Anshton. */
    public Anshton() {
        addCommands(
                // position for scoring 1st piece
                new ScorePreLoaded(),
                // score 1st piece
                new Outtake(),
                // drive backwards out of community
                new AutoDrive(-0.75, 0, 2),
                // turn robot
                new AutoDrive(0, 1, 0.75),
                // drive to 2nd piece
                new AutoDrive(0.75, 0, 4),
                // position arm for 2nd piece
                new AutoArmGroundIntake(),
                // intake 2nd piece
                new AutoIntake(),
                // drive backwards to community
                new AutoDrive(-0.75, 0, 4),
                // turn robot
                new AutoDrive(0, 1, 0.75),
                // drive to goal
                new AutoDrive(0.75, 0, 2),
                // position arm to score on ground node
                new AutoArmGroundIntake(),
                // score 2nd piece
                new Outtake());
    }
}
