package frc.robot.auton.sequences;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.auton.commands.Ansh;

public class MainAuto extends SequentialCommandGroup {
    public MainAuto() {
        addCommands(new Ansh());
    }
}
