/**
 * Rosie Robotics 2015
 */

package org.rosie.robot.lib;

import edu.wpi.first.wpilibj.Joystick;

public class GameController extends Joystick 
{
    /**The Left-X Axis to use for getRawAxis(int), getLX() is the equivalent*/
    public static final int LEFT_X = 0;
    /**The Left-Y Axis to use for getRawAxis(int), getLY() is the equivalent*/
    public static final int LEFT_Y = 1;
    /**The Right-X Axis to use for getRawAxis(int), getRX() is the equivalent*/
    public static final int RIGHT_X = 2;
    /**The Right-Y Axis to use for getRawAxis(int), getRY() is the equivalent*/
    public static final int RIGHT_Y = 3;

    /**The A / 2 button to use for getRawButton(int), getBA() is the equivalent*/
    public static final int A = 2;
    /**The B / 3 button to use for getRawButton(int), getBB() is the equivalent*/
    public static final int B = 3;
    /**The X / 1 button to use for getRawButton(int), getBX() is the equivalent*/
    public static final int X = 1;
    /**The Y / 4 button to use for getRawButton(int), getBY() is the equivalent*/
    public static final int Y = 4;
    /**The LB (Top) button to use for getRawButton(int), getBLB() is the equivalent*/
    public static final int LB = 5;
    /**The LT (Bottom) button to use for getRawButton(int), getBLT() is the equivalent*/
    public static final int LT = 7;
    /**The RB (Top) button to use for getRawButton(int), getBRB() is the equivalent*/
    public static final int RB = 6;
    /**The RT (Bottom) button to use for getRawButton(int), getBRT() is the equivalent*/
    public static final int RT = 8;
    /**The Back / 9 button to use for getRawButton(int), getBBack() is the equivalent*/
    public static final int BACK = 9;
    /**The Start / 10 button to use for getRawButton(int), getBStart() is the equivalent*/
    public static final int START = 10;

    public GameController(int port)
    {
        super(port);
    }

    /**Returns the Left Analog stick's X-Axis value*/
    public double getLX()
    {
        return getRawAxis(LEFT_X);
    }

    /**Returns the Left Analog stick's Y-Axis value*/
    public double getLY()
    {
        return getRawAxis(LEFT_Y);
    }

    /**Returns the Right Analog stick's X-Axis value*/
    public double getRX()
    {
        return getRawAxis(RIGHT_X);
    }

    /**Returns the Right Analog stick's Y-Axis value*/
    public double getRY()
    {
        return getRawAxis(RIGHT_Y);
    }

    /**Returns whether the A / 2 button is pressed*/
    public boolean getBA()
    {
        return getRawButton(A);
    }

    /**Returns whether the B / 3 button is pressed*/
    public boolean getBB()
    {
        return getRawButton(B);
    }

    /**Returns whether the X / 1 button is pressed*/
    public boolean getBX()
    {
        return getRawButton(A);
    }

    /**Returns whether the Y / 4 button is pressed*/
    public boolean getBY()
    {
        return getRawButton(Y);
    }

    /**Returns whether the LB (Top) button is pressed*/
    public boolean getBLB()
    {
        return getRawButton(LB);
    }

    /**Returns whether the LT (Bottom button is pressed*/
    public boolean getBLT()
    {
        return getRawButton(LT);
    }

    /**Returns whether the RB (Top) button is pressed*/
    public boolean getBRB()
    {
        return getRawButton(RB);
    }

    /**Returns whether the RT (Bottom) button is pressed*/
    public boolean getBRT()
    {
        return getRawButton(RT);
    }

    /**Returns whether the Back / 9 button is pressed*/
    public boolean getBBack()
    {
        return getRawButton(BACK);
    }

    /**Returns whether the Start / 10 button is pressed*/
    public boolean getBStart()
    {
        return getRawButton(START);
    }
    
    public static boolean inDeadzone(double value)
    {
        return Math.abs(value) < 0.1;
    }
}
