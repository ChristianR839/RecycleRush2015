/**
 * Rosie Robotics 2015
 */

package org.rosie.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.rosie.robot.Robot;
import org.rosie.robot.RobotMap;

public class  MiddleGripperCommand extends Command
{
    boolean m_Finish;
    
    public MiddleGripperCommand()
    {

    }

    protected void initialize()
    {
        m_Finish = false;

        if(Robot.gripper.isOpen())
            Robot.gripper.close();
        else if(Robot.gripper.isClosed())
            Robot.gripper.open();
        else
            m_Finish = true;
    }

    protected void execute()
    {
        if(Robot.gripper.reedHit());
        {
            Robot.gripper.off();
            RobotMap.g_LedStrip.setColor(1, 255, 245, 238);

            m_Finish = true;
        }
    }

    protected boolean isFinished()
    {
        return m_Finish;
    }

    protected void end()
    {

    }

    protected void interrupted()
    {
        end();
    }
}