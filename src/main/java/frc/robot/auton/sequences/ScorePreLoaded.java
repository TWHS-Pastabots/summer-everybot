package frc.robot.auton.sequences;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.auton.commands.*;

public class ScorePreLoaded extends SequentialCommandGroup {

    public ScorePreLoaded() {
        addCommands(
                new AutoArmShoot(), // Shooting position
                new AutoOuttake(0.25), // Has scored the piece
                new AutoArmRetract());
    }
}
