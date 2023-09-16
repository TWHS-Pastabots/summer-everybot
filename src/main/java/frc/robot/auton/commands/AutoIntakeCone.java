package frc.robot.auton.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class AutoIntakeCone extends CommandBase {
    private Intake intake;
    private double time;
    private double endTime;

    private boolean ended = false;

    public AutoIntakeCone() {
        time = Timer.getFPGATimestamp();
        endTime = time + 1;
    }

    @Override
    public void initialize() {
        intake = Intake.getInstance();
    }

    @Override
    public void execute() {

        intake.update(true, false, false);

        if (time == endTime) {
            ended = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return ended;
    }
}