package frc.robot.auton.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.*;

public class AutoIntake extends CommandBase {
    private Intake intake;
    private double time;
    private double endTime;

    private boolean ended = false;

    public AutoIntake(double duration) {
        endTime = duration;
    }

    @Override
    public void initialize() {
        intake = Intake.getInstance();
        time = Timer.getFPGATimestamp();
        endTime += time;
    }

    @Override
    public void execute() {
        intake.update(false, true);

        time = Timer.getFPGATimestamp();
        if (time >= endTime) {
            ended = true;
            intake.update(false, false);
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
