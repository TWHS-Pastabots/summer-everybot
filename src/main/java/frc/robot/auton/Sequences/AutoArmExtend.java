package frc.robot.auton.Sequences;

import frc.robot.subsystems.Arm;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoArmExtend {

    private static AutoArmExtend instance;
    private static Arm autoArmExtend;
    private static CANSparkMax autoArmSpark;
    private boolean endCondition = false;
    private double power = 1.0;
    private double reqPosition = -18;

    public void aExtend() {
        autoArmExtend = Arm.getInstance();

        autoArmExtend.armController.set(power);
    }

    public static AutoArmExtend getInstance() {
        if (instance == null) {
            instance = new AutoArmExtend();
        }
        return instance;
    }

    public void update() {
        SmartDashboard.putNumber("ARM POSITION", autoArmExtend.armController.getEncoder().getPosition());

        if (autoArmExtend.armController.getEncoder().getPosition() >= reqPosition) {
            autoArmExtend.armController.set(0.0);
            endCondition = true;
        }
    }

}
