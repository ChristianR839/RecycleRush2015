package org.rosie.robot.commands;

import org.rosie.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ResetYawCommand extends Command
{

    @Override
    protected void initialize()
    {
        Robot.driveTrain.resetGyro();
    }

    @Override
    protected void execute()
    {
        
    }

    @Override
    protected boolean isFinished()
    {
        return true;
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
