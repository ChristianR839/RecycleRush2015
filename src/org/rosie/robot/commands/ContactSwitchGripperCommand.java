/**
 * Rosie Robotics 2015
 */

package org.rosie.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

import org.rosie.robot.Robot;
import org.rosie.robot.RobotMap;

public class  ContactSwitchGripperCommand extends Command
{
    /**Roughly 0.12 seconds*/
    static final double READY_DELAY = 1.0 / 8.5;
    /**The brightness of the green value for LEDs when both contacts are hit*/
    int m_ReadyLight = 255;
    /**This is to check whether to add or subtract brightness to m_ReadyLight*/
    boolean m_Add;

    public ContactSwitchGripperCommand()
    {

    }

    protected void initialize()
    {

    }

    protected void execute()
    {
        //Neither contact hit = both LEDs red 
        //Left hit, Right not hit = Left LED orange, Right LED red
        //Left not hit, Right hit = Left LED red, Right LED orange
        //Both contacts hit = both LEDs green blinking

        if(Robot.gripper.leftContactHit())
        {
            if(Robot.gripper.rightContactHit())
            {
                RobotMap.g_LedStrip.setColor(3, 0, m_ReadyLight, 0);
                RobotMap.g_LedStrip.setColor(4, 0, m_ReadyLight, 0);
                RobotMap.g_LedStrip.setColor(5, 0, m_ReadyLight, 0);
                RobotMap.g_LedStrip.setColor(6, 0, m_ReadyLight, 0);

                m_ReadyLight += (m_Add ? 15 : -15);

                if(m_ReadyLight == 0)
                    m_Add = true;
                else if(m_ReadyLight == 255)
                    m_Add = false;

                Timer.delay(READY_DELAY);
            }
            else
            {
                RobotMap.g_LedStrip.setColor(3, 255, 165, 0);
                RobotMap.g_LedStrip.setColor(4, 255,   0, 0);
                RobotMap.g_LedStrip.setColor(5, 255, 165, 0);
                RobotMap.g_LedStrip.setColor(6, 255,   0, 0);
            }
        }
        else
        {
            if(Robot.gripper.rightContactHit())
            {
                RobotMap.g_LedStrip.setColor(3, 255,   0, 0);
                RobotMap.g_LedStrip.setColor(4, 255, 165, 0);
                RobotMap.g_LedStrip.setColor(5, 255,   0, 0);
                RobotMap.g_LedStrip.setColor(6, 255, 165, 0);
            }
            else
            {
                RobotMap.g_LedStrip.setColor(3, 255, 0, 0);
                RobotMap.g_LedStrip.setColor(4, 255, 0, 0);
                RobotMap.g_LedStrip.setColor(5, 255, 0, 0);
                RobotMap.g_LedStrip.setColor(6, 255, 0, 0);
            }
        }
    }

    protected boolean isFinished()
    {
        return false;
    }

    protected void end()
    {

    }

    protected void interrupted()
    {
        end();
    }
}
