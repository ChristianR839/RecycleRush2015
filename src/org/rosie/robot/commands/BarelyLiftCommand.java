/**
 * Rosie Robotics 2015
 */

package org.rosie.robot.commands;

import org.rosie.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class BarelyLiftCommand extends Command
{
    /**
     * This command brings the Lift to just over 0
     */
    public BarelyLiftCommand()
    {
        super(1);
        
        requires(Robot.verticalLift);
    }

    @Override
    protected void initialize()
    {
        Robot.verticalLift.positionMode(true);
    }

    @Override
    protected void execute()
    {
        Robot.verticalLift.goTo(1500);
    }

    @Override
    protected boolean isFinished()
    {
        return Robot.verticalLift.isAt(1500);
    }

    @Override
    protected void end()
    {
        
    }

    @Override
    protected void interrupted()
    {
        Robot.verticalLift.stopPos();
    }
}
