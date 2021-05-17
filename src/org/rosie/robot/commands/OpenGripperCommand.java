/**
 * Rosie Robotics 2015
 */

package org.rosie.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.rosie.robot.Robot;
import org.rosie.robot.RobotMap;

/**
 *
 */
public class  OpenGripperCommand extends Command
{
    public OpenGripperCommand()
    {

    }

    protected void initialize() 
    {

    }

    protected void execute() 
    {
        Robot.gripper.open();
        RobotMap.g_LedStrip.setColor(6, 34, 45, 56);
    }

    protected boolean isFinished() 
    {
        return true; //Returns true immediately as execute() only needs to be called once
    }

    protected void end() 
    {

    }

    protected void interrupted() 
    {
    	end();
    }
}
