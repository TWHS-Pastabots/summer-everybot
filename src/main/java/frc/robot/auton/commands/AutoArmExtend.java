package frc.robot.auton.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.*;
import frc.robot.subsystems.Arm.*;

public class AutoArmExtend extends CommandBase {
    private Arm arm;
    private boolean ended = false;

    public AutoArmExtend() {
    }

    @Override
    public void initialize() {
        arm = Arm.getInstance();
    }

    @Override
    public void execute() {
        arm.setState(ArmState.EXTENDED);

        if (arm.hasReachedTargetPose(2.0)) {
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
