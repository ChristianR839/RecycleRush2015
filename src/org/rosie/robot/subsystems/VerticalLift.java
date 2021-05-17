/**
 * Rosie Robotics 2015
 */

package org.rosie.robot.subsystems;

import org.rosie.robot.RobotMap;
import org.rosie.robot.commands.*;
import org.rosie.robot.lib.RobotProps;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;


public class VerticalLift extends Subsystem
{
    boolean m_PosMode;
    CANTalon m_CANTalonSRX5 = RobotMap.g_VerticalLiftCANTalonSRX5;

    /**The various levels that the VerticalLift may go to.*/
    public enum Level
    {
        LEVEL1("1"), LEVEL2("2"), TOTESHUTE("tote"), LEVEL3("3"), LEVEL4("4"), LEVEL5("5"), LEVEL6("6");

        /**Index for double[] pos*/
        private final String propAppend;

        private Level(String propAppend)
        {
            this.propAppend = propAppend;
        }
    }
    
    public void positionMode(boolean use)
    {
        if(use)
            m_CANTalonSRX5.changeControlMode(CANTalon.TalonControlMode.Position   );
        else
            m_CANTalonSRX5.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
        m_PosMode = use;
    }
    
    public boolean inPositionMode()
    {
        return m_PosMode;
    }
    
    public boolean limitSwitchHit()
    {
        return m_CANTalonSRX5.isRevLimitSwitchClosed();
    }
    
    public void resetEnc()
    {
        m_CANTalonSRX5.setPosition(0);
    }

    public double getEncPosition()
    {
        return m_CANTalonSRX5.getEncPosition();
    }

    /**Get the position for the given Level from the Properties file*/
    public double getPos(Level level)
    {
        return RobotProps.getDouble("vlLevel" + level.propAppend, 0);
    }

    /**Set the position for the given Level in the Properties file*/
    public void setPos(Level level, double pos)
    {
        RobotProps.setDouble("vlLevel" + level.propAppend, pos);
    }
    
    public void move(double power)
    {
        m_CANTalonSRX5.set(power);
    }

    /**Tells the lift to go the given Level, whose height is set with {@link #setPos(Level, double)}*/
    public void goTo(Level level)
    {
        double pos = getPos(level);
        m_CANTalonSRX5.set(pos);
    }
    
    public void goTo(float pos)
    {
        m_CANTalonSRX5.set(pos);
    }

    /**Returns whether the VerticalLift is currently at a given level*/
    public boolean isAt(Level level)
    {//TODO comment
        double pos = getPos(level);
        double p   = m_CANTalonSRX5.get();
        double dis = Math.abs(p - pos);
        final boolean r = dis < 160;
        //RobotProps.log(r + " : " + p + " : " + dis);
        return r;
    }
    
    public boolean isAt(float pos)
    {
        return Math.abs(m_CANTalonSRX5.get() - pos) < 160;
    }

    /**Stops the lift at the current position by setting the target position to the current*/
    public void stopPos()
    {
        m_CANTalonSRX5.set(m_CANTalonSRX5.get());
    }
    
    /**Sets lift to 0*/
    public void stopMove()
    {
        m_CANTalonSRX5.set(0);
    }

    /**Returns the kP for the PID from the Properties file*/
    public double getKP()
    {
        return RobotProps.getDouble("vlKP", 0);
    }

    /**Returns the kI for the PID from the Properties file*/
    public double getKI()
    {
        return RobotProps.getDouble("vlKI", 0);
    }

    /**Returns the kD for the PID from the Properties file*/
    public double getKD()
    {
        return RobotProps.getDouble("vlKD", 0);
    }

    /**Updates the Talon's PID as well as the Properties file*/
    public void setPIDValues(double kP, double kI, double kD)
    {
        RobotProps.setDouble("vlKP", kP);
        RobotProps.setDouble("vlKI", kI);
        RobotProps.setDouble("vlKD", kD);
        m_CANTalonSRX5.setPID(kP, kI, kD);
    }

    public void initDefaultCommand()
    {
        setDefaultCommand(new TeleopLiftCommand());
    }
}

