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
                new AutoDrive(-0.75, 0, 1),
                // turn robot
                new AutoDrive(0, 1, 0),
                // drive to and intake second piece
                new IntakeWhileDriving(),
                // drive backwards to community
                new AutoDrive(-0.75, 0, 0),
                // turn robot
                new AutoDrive(0, 1, 0),
                // drive to goal
                new AutoDrive(0.75, 0, 0),
                // position arm to score on ground node
                new AutoArmGroundIntake(),
                // score 2nd piece
                new Outtake());
    }
}
