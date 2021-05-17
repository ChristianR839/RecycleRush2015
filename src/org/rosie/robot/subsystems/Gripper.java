/**
`` * Rosie Robotics 2015
 */

package org.rosie.robot.subsystems;

import org.rosie.robot.RobotMap;
import edu.wpi.first.wpilibj.*;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Gripper extends Subsystem
{
    DigitalInput    m_ReedSwitch      = RobotMap.g_GripperReedSwitch;
    DoubleSolenoid  m_Forks           = RobotMap.g_GripperSolenoid;

    DigitalInput m_LeftContactSwitch  = RobotMap.g_GripperContactSwitchLeft;
    DigitalInput m_RightContactSwitch = RobotMap.g_GripperContactSwitchRight;

    DoubleSolenoid.Value m_SolenoidState = DoubleSolenoid.Value.kOff;

    public void initDefaultCommand()
    {

    }

    /**Opens the claw*/

    public void open()
    {
        m_Forks.set(DoubleSolenoid.Value.kReverse);
        m_SolenoidState = DoubleSolenoid.Value.kReverse;
    }

    /**Closes the claw*/
    public void close()
    {
        m_Forks.set(DoubleSolenoid.Value.kForward);
        m_SolenoidState = DoubleSolenoid.Value.kForward;
    }

    /**Sets the claw to off*/
    public void off()
    {
        m_Forks.set(DoubleSolenoid.Value.kOff);
        m_SolenoidState = DoubleSolenoid.Value.kOff;
    }

    /**Returns whether the claw is open*/
    public boolean isOpen()
    {
        return m_SolenoidState == DoubleSolenoid.Value.kReverse;
    }

    /**Returns whether the claw is closed*/
    public boolean isClosed()
    {
        return m_SolenoidState == DoubleSolenoid.Value.kForward;
    }

    /**Returns whether the claw is off*/
    public boolean isOff()
    {
        return m_SolenoidState == DoubleSolenoid.Value.kOff;
    }

    /**Returns whether the left contact switch was hit*/
    public boolean leftContactHit()
    {
        return m_LeftContactSwitch.get();
    }

    /**Returns whether the right contact switch was hit*/
    public boolean rightContactHit()
    {
        return m_RightContactSwitch.get();
    }

    /**Returns whether the reed switch has been hit*/
    public boolean reedHit()
    {
        return m_ReedSwitch.get();
    }
}



