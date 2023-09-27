package frc.robot.auton.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.*;
import frc.robot.subsystems.Arm.*;

public class AutoArmShoot extends CommandBase {
    private Arm arm;
    private boolean ended = false;

    public AutoArmShoot() {
    }

    @Override
    public void initialize() {
        arm = Arm.getInstance();
    }

    @Override
    public void execute() {
        arm.setState(ArmState.SHOOT);

        if (arm.hasReachedTargetPose(3.5)) {
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
