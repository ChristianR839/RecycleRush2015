package org.rosie.robot.commands;

import org.rosie.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class UpdateSBValuesCommand extends Command
{
    @Override
    protected void initialize()
    {

    }

    @Override
    protected void execute()
    {
        SmartDashboard.putNumber("Forward Encoder Position",  Robot.driveTrain.forwardEncPosition ());
        SmartDashboard.putNumber("Sideway Encoder Position",  Robot.driveTrain.sidewaysEncPosition());
        SmartDashboard.putNumber("VerticalLift position"   , -Robot.verticalLift.getEncPosition   ());
        SmartDashboard.putNumber("Gyro Angle"              ,  Robot.driveTrain.gyroAngle          ());
        SmartDashboard.putString("Drive mode"              ,  Robot.driveTrain.driveMode          ());
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void end()
    {

    }

    @Override
    protected void interrupted()
    {

    }
}
