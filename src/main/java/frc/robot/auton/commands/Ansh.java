package frc.robot.auton.commands;

import frc.robot.subsystems.*;
import frc.robot.subsystems.Arm.ArmState;
import frc.robot.subsystems.Intake.IntakeState;

import edu.wpi.first.wpilibj2.command.*;

public class Ansh extends CommandBase {
    private Drivebase drivebase;
    private Arm arm;
    private Intake intake;

    @Override
    public void initialize() {
        drivebase = Drivebase.getInstance();
        arm = Arm.getInstance();
    }

    @Override
    public void execute() {
        drivebase.autoDrive(2, 0.1);
        arm.setState(ArmState.EXTENDED);
        drivebase.autoDrive(1, 0.2);
        arm.setState(ArmState.RETRACTED);
        drivebase.autoDrive(1, 1);
        intake.setState(IntakeState.INTAKE_CO);
    }
}
