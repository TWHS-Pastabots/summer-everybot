package frc.robot.auton;

import frc.robot.auton.Sequences.AutoArmExtend;
import frc.robot.auton.Sequences.AutoArmRetract;
import frc.robot.auton.Sequences.AutoDrive;

public class SequentialCommandGroup {

    private static AutoDrive drive;
    private static AutoArmExtend extend;
    private static AutoArmRetract retract;

    public void forwardDrive() {
        drive = AutoDrive.getInstance();
        extend = AutoArmExtend.getInstance();
        retract = AutoArmRetract.getInstance();

        drive.aDrive().and


    }

}