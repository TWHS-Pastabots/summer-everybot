package frc.robot.auton.sequences;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.auton.commands.*;

public class Test extends SequentialCommandGroup {
    /** Creates a new Test. */
    public Test() {

        addCommands(
                new AutoDrive(-0.75, 0, 0.4),
                new AutoArmShoot(),//Shooting position
                new AutoOuttake(2), //Has scored the piece
                new AutoArmRetract(),
                new AutoDrive(-0.75, .015, 2.9));//Gone to pickup
                new AutoIntake(1.5);//Picks it up
                
                
    }
}
