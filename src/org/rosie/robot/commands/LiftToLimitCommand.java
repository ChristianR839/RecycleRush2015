package org.rosie.robot.commands;

import org.rosie.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class LiftToLimitCommand extends Command
{
    
    public LiftToLimitCommand()
    {
        super(4.4);
        
        requires(Robot.verticalLift);
    }
    
    @Override
    protected void initialize()
    {
        Robot.verticalLift.positionMode(false);
    }

    @Override
    protected void execute()
    {
        Robot.verticalLift.move(-1);
    }

    @Override
    protected boolean isFinished()
    {
        return Robot.verticalLift.limitSwitchHit() || isTimedOut();
    }

    @Override
    protected void end()
    {
        Robot.verticalLift.positionMode(true);
        Robot.verticalLift.stopPos();
    }

    @Override
    protected void interrupted()
    {
        end();
    }

}
