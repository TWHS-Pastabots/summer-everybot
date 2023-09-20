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
        time = Timer.getFPGATimestamp();
        endTime = time + duration;
    }

    @Override
    public void initialize() {
        intake = Intake.getInstance();
    }

    @Override
    public void execute() {
        intake.update(false, true);

        time = Timer.getFPGATimestamp();
        if (time >= endTime) {
            ended = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        intake.update(false, false);
    }

    @Override
    public boolean isFinished() {
        return ended;
    }
}
