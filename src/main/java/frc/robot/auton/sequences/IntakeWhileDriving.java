package frc.robot.auton.sequences;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.auton.commands.*;

public class IntakeWhileDriving extends ParallelCommandGroup {

    public IntakeWhileDriving() {
        addCommands(
                new AutoArmExtend(),
                new AutoDrive(0.75, 0, 0),
                new AutoIntake(5));
        // Intake runs while driving
        // W code
    }
}
