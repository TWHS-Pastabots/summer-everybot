package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Ports;

public class Drivebase {
    private static CANSparkMax rightSparkController1;
    private static CANSparkMax rightSparkController2;
    private static CANSparkMax leftSparkController1;
    private static CANSparkMax leftSparkController2;
    private static Drivebase instance;
 
    private static final double MAX_SPEED = 0.5;

    public Drivebase() {
        // Right motor group

        rightSparkController1 = new CANSparkMax(Ports.RIGHT_SPARK1, MotorType.kBrushed);
        rightSparkController1.setInverted(true);
        rightSparkController1.setSmartCurrentLimit(25);
        rightSparkController1.burnFlash();

        rightSparkController2 = new CANSparkMax(Ports.RIGHT_SPARK2, MotorType.kBrushed);
        rightSparkController2.setInverted(true);
        rightSparkController2.setSmartCurrentLimit(25);
        rightSparkController2.burnFlash();

        // Left motor group

        leftSparkController1 = new CANSparkMax(Ports.LEFT_SPARK1, MotorType.kBrushed);
        leftSparkController1.setInverted(false);
        leftSparkController1.setSmartCurrentLimit(25);
        leftSparkController1.burnFlash();

        leftSparkController2 = new CANSparkMax(Ports.LEFT_SPARK2, MotorType.kBrushed);
        leftSparkController2.setInverted(false);
        leftSparkController2.setSmartCurrentLimit(25);
        leftSparkController2.burnFlash();

    }

    public void drive(double right, double left) {
        double leftSpeed = left * MAX_SPEED;
        double rightSpeed = right * MAX_SPEED;

        leftSparkController1.set(leftSpeed);
        leftSparkController2.set(leftSpeed);

        rightSparkController1.set(rightSpeed);
        rightSparkController2.set(rightSpeed);

        SmartDashboard.putNumber("Left Spark 1", leftSparkController1.getAppliedOutput());
        SmartDashboard.putNumber("Right Spark 1", rightSparkController1.getAppliedOutput());
        SmartDashboard.putNumber("Left Spark 2", leftSparkController2.getAppliedOutput());
        SmartDashboard.putNumber("Right Spark 2", rightSparkController2.getAppliedOutput());
        SmartDashboard.putString("ANSH", "ANSH");
    }

    public static boolean isDriving() {
        if ( leftSparkController1.getAppliedOutput() != 0 || leftSparkController2.getAppliedOutput() != 0 
        || rightSparkController1.getAppliedOutput() != 0 || rightSparkController2.getAppliedOutput() != 0) {
            return true;
        }
        else{
            return false;
        }
    }

    public static Drivebase getInstance() {
        if (instance == null) {
            instance = new Drivebase();
        }
        return instance;
    }
}