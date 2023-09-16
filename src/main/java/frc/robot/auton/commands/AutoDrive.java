package frc.robot.auton.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivebase;

public class AutoDrive extends CommandBase {
    private Drivebase drivebase;

    private final double power = 0.5;

    private double time;
    private double turn;
    private double endTime;

    private boolean ended = false;

    /** Creates a new AutoDrive. */
    public AutoDrive(double time, double turn) {
        endTime = time;
        this.turn = turn;
    }

    public AutoDrive(double time) {
        endTime = time;
        turn = 0;
    }

    @Override
    public void initialize() {
        drivebase = Drivebase.getInstance();
        time = Timer.getFPGATimestamp();
        endTime += time;
    }

    @Override
    public void execute() {
        drivebase.drive(power, turn);

        if (time > endTime) {
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