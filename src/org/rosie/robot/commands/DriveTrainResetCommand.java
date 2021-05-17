package org.rosie.robot.commands;

import org.rosie.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveTrainResetCommand extends Command
{

    @Override
    protected void initialize()
    {

    }

    @Override
    protected void execute()
    {
        Robot.driveTrain.resetTargeting();
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
