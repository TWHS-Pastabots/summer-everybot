// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.hal.FRCNetComm.tResourceType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivebase extends SubsystemBase {

    private VictorSPX rightFront;
    private VictorSPX rightBack;
    private VictorSPX leftBack;
    private VictorSPX leftFront;

    /** Creates a new ExampleSubsystem. */
    public Drivebase() {

        VictorSPX
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
