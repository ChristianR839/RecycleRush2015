/**
 * Rosie Robotics 2015
 */

package org.rosie.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.rosie.robot.Robot;
import org.rosie.robot.RobotMap;

public class  CloseGripperCommand extends Command
{
    public CloseGripperCommand() 
    {

    }

    protected void initialize()
    {

    }

    protected void execute()
    {
        Robot.gripper.close();
        RobotMap.g_LedStrip.setColor(5, 34, 45, 56);
    }

    protected boolean isFinished()
    {
        return true; //Returns true as execute() only needs to be called once
    }

    protected void end()
    {

    }

    protected void interrupted()
    {
        end();
    }
}
