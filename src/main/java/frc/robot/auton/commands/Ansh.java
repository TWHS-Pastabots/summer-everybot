package frc.robot.auton.commands;

import frc.robot.auton.Command;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Arm.ArmState;
import frc.robot.subsystems.Intake.IntakeState;

public class Ansh extends Command {
    private Drivebase drivebase;
    private Arm arm;
    private Intake intake;

    public Ansh() {
        drivebase = Drivebase.getInstance();
        arm = Arm.getInstance();
    }

    @Override
    public void initialize() {
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
