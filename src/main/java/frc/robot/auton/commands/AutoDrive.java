package frc.robot.auton.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Drivebase;

public class AutoDrive extends CommandBase {
    private Drivebase drivebase;

    private double time;
    private double turn;
    private double forward;
    private double endTime;

    private boolean ended = false;

    public AutoDrive(double forward, double turn, double time) {
        endTime = time;
        this.turn = turn;
        this.forward = -forward;
    }

    @Override
    public void initialize() {
        drivebase = Drivebase.getInstance();
        time = Timer.getFPGATimestamp();
        endTime += time;
    }

    @Override
    public void execute() {
        drivebase.drive(forward, turn);
        time = Timer.getFPGATimestamp();

        if (time >= endTime) {
            ended = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        drivebase.drive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return ended;
    }
}
