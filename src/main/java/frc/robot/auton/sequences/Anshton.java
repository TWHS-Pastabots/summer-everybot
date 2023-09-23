package frc.robot.auton.sequences;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.auton.commands.*;

public class Anshton extends SequentialCommandGroup {
    /** Creates a new Anshton. */
    public Anshton() {
        addCommands(
                // new AutoDrive(-0.75, 0.015, 0.4),
                new AutoArmShoot(), // Shooting position
                new AutoOuttake(0.25), // Has scored the piece
                new AutoArmRetract(),
                new AutoDrive(-1, 0.028, 2.2), // Gone to pickup
                new AutoDrive(0, 0, 60));
    }
}
