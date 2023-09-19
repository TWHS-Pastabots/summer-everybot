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

    @Override
    public void initialize() {
        arm = Arm.getInstance();
    }

    @Override
    public void execute() {
        arm.setControlState(ArmControl.PID);
        arm.setState(ArmState.EXTENDED);
        arm.update(0, 0);

        if (Math.abs(arm.getArmPose() - arm.state.poseU) <= 0.5) {
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
