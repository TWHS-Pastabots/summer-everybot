
package frc.robot.auton.Sequences;

import frc.robot.subsystems.Arm;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoArmRetract {
    private static AutoArmRetract instance;
    private static Arm autoArmRetract;
    private boolean endCondition = false;
    private double power = -1.0;
    private double reqPosition = 7;

    public void aExtend() {
        autoArmRetract = Arm.getInstance();

        autoArmRetract.armController.set(power);
    }

    public static AutoArmRetract getInstance() {
        if (instance == null) {
            instance = new AutoArmRetract();
        }
        return instance;
    }

    public void update() {
        SmartDashboard.putNumber("ARM POSITION", autoArmRetract.armController.getEncoder().getPosition());

        if (autoArmRetract.armController.getEncoder().getPosition() >= reqPosition) {
            autoArmRetract.armController.set(0.0);
            endCondition = true;
        }
    }
}
