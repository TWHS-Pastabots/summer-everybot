package frc.robot.auton.sequences;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.auton.commands.*;

public class Anshton extends SequentialCommandGroup {
    /** Creates a new Anshton. */
    public Anshton() {
        addCommands(
                new ScorePreLoaded(),
                new AutoDrive(-0.5, 0.024, 2.2), // Go to pickup
                new AutoDrive(0, 0, 60));
    }
}
