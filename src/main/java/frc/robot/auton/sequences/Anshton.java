package frc.robot.auton.sequences;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.auton.commands.AutoDrive;

public class Anshton extends SequentialCommandGroup {
    /** Creates a new Anshton. */
    public Anshton() {
        addCommands(
                new AutoDrive(0.75, 0, .2),
                new AutoDrive(-0.75, 0, 2));
    }
}
