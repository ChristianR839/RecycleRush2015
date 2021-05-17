/**
 * Rosie Robotics 2015
 */

package org.rosie.robot.lib;

import edu.wpi.first.wpilibj.Joystick;

public class AccessoryController extends Joystick 
{
    /**The button mapping for Level 1*/
    public static final int LEVEL1    =  2;
    /**The button mapping for Level 2*/
    public static final int LEVEL2    =  1;
    /**The button mapping for Tote Shute*/
    public static final int TOTESHUTE =  3;
    /**The button mapping for Level 3*/
    public static final int LEVEL3    =  4;
    /**The button mapping for Level 4*/
    public static final int LEVEL4    =  9;
    /**The button mapping for Level 5*/
    public static final int LEVEL5    = 10;
    /**The button mapping for Level 6*/
    public static final int LEVEL6    =  5;

    /**The button mapping for opening the gripper fully*/
    public static final int OPEN_GRIPPER_FULL = 7;
    /**The button mapping for opening the gripper half-way*/
    public static final int OPEN_GRIPPER_HALF = 6;
    /**The button mapping for closing the gripper*/
    public static final int CLOSE_GRIPPER     = 8;

    public AccessoryController(int port)
    {
        super(port);
    }

    /**Returns whether the Level 1 button is pressed*/
    public boolean getLevel1()
    {
        return getRawButton(LEVEL1);
    }

    /**Returns whether the Level 2 button is pressed*/
    public boolean getLevel2()
    {
        return getRawButton(LEVEL2);
    }

    /**Returns whether the Tote Shute button is pressed*/
    public boolean getToteShute()
    {
        return getRawButton(TOTESHUTE);
    }
    
    /**Returns whether the Level 3 button is pressed*/
    public boolean getLevel3()
    {
        return getRawButton(LEVEL3);
    }

    /**Returns whether the Level 4 button is pressed*/
    public boolean getLevel4()
    {
        return getRawButton(LEVEL4);
    }

    /**Returns whether the Level 5 button is pressed*/
    public boolean getLevel5()
    {
        return getRawButton(LEVEL5);
    }

    /**Returns whether the Level 6 button is pressed*/
    public boolean getLevel6()
    {
        return getRawButton(LEVEL6);
    }

    /**Returns whether the Open-Gripper-Full button is pressed*/
    public boolean getOpenGripperFull()
    {
        return getRawButton(OPEN_GRIPPER_FULL);
    }

    /**Returns whether the Open-Gripper-Half button is pressed*/
    public boolean getOpenGripperHalf()
    {
        return getRawButton(OPEN_GRIPPER_HALF);
    }

    /**Returns whether the Close-Gripper button is pressed*/
    public boolean getCloseGripper()
    {
        return getRawButton(CLOSE_GRIPPER);
    }
}
