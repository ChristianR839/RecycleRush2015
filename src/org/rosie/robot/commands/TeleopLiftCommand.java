/**
 * Rosie Robotics 2015
 */

package org.rosie.robot.commands;

import org.rosie.robot.Robot;
import org.rosie.robot.subsystems.VerticalLift.Level;

import edu.wpi.first.wpilibj.command.Command;

public class TeleopLiftCommand extends Command
{
    boolean wasJoystick;

    public TeleopLiftCommand()
    {
        requires(Robot.verticalLift);
        Robot.verticalLift.positionMode(false);
        Robot.verticalLift.stopMove();
    }

    @Override
    protected void initialize()
    {

    }

    @Override
    protected void execute()
    {
        double y = -Robot.oi.accessoryController.getY();
        if(Math.abs(y) >= 0.1)
        {
            if(Robot.verticalLift.inPositionMode())
                Robot.verticalLift.positionMode(false);

            Robot.verticalLift.move(y);
        }
        else
        {
            if(!Robot.verticalLift.inPositionMode())
            {
                Robot.verticalLift.positionMode(true);
                Robot.verticalLift.stopPos();
            }
            
            if(Robot.oi.accessoryController.getLevel1        ())
                Robot.verticalLift.goTo(Level.LEVEL1   );
            else if(Robot.oi.accessoryController.getLevel2   ())
                Robot.verticalLift.goTo(Level.LEVEL2   );
            else if(Robot.oi.accessoryController.getToteShute())
                Robot.verticalLift.goTo(Level.TOTESHUTE);
            else if(Robot.oi.accessoryController.getLevel3   ())
                Robot.verticalLift.goTo(Level.LEVEL3   );
            else if(Robot.oi.accessoryController.getLevel4   ())
                Robot.verticalLift.goTo(Level.LEVEL4   );
            else if(Robot.oi.accessoryController.getLevel5   ())
                Robot.verticalLift.goTo(Level.LEVEL5   );
            else if(Robot.oi.accessoryController.getLevel6   ())
                Robot.verticalLift.goTo(Level.LEVEL6   );
        }
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void end()
    {
        Robot.verticalLift.positionMode(false);
        Robot.verticalLift.stopMove();
    }

    @Override
    protected void interrupted()
    {
        end();
    }
}