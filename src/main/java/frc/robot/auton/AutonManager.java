package frc.robot.auton;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.auton.commands.Ansh;

public class AutonManager {
    private CommandBase[] commands;

    public AutonManager() {
        commands = new CommandBase[1];
        commands[0] = new Ansh();
    }

    public void runTheStuff() {
        commands[0].execute();
    }
}
