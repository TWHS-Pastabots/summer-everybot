package frc.robot.auton.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Arm.ArmControl;
import frc.robot.subsystems.Arm.ArmState;

public class AutoArmExtend extends CommandBase {

    private Arm arm;
    private boolean ended = false;

    public AutoArmExtend() {

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        arm = Arm.getInstance();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        arm.setControlState(ArmControl.PID);
        arm.setState(ArmState.EXTENDED);
        arm.update(0, 0);
        if (arm.armPosition() >= -18) {
            ended = true;
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return ended;
    }
}