/**
 * Rosie Robotics 2015
 */

package org.rosie.robot.commands;

import org.rosie.robot.Robot;
import org.rosie.robot.lib.RobotProps;
import org.rosie.robot.subsystems.VerticalLift.Level;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**Please remember to call {@link #init()}!*/
public class PropertiesCommand extends Command
{
    /**
     * 
     * SEND is for sending TO the SmartDashboard. PULL is for pulling FROM the SmartDashboard.
     * Please note that on the SmartDashboard the SEND button will cause the robot to do a PULL,
     * as the SmartDashboard is sending the data to the robot that is pulling.
     *
     */
    public enum Mode
    {
        SEND, PULL
    }

    final Mode m_Mode;

    public PropertiesCommand(Mode mode)
    {
        m_Mode = mode;
    }

    @Override
    protected void initialize()
    {

    }

    @Override
    protected void execute()
    {
        if(!RobotProps.checkPassword())
            return; //An Admin did not set the right password, do not change anything

        if(m_Mode == Mode.SEND)
            sendProperties();
        else
            pullProperties();
    }

    @Override
    protected boolean isFinished()
    {
        return true;
    }

    @Override
    protected void end()
    {
        
    }

    @Override
    protected void interrupted()
    {

    }
    
    /**Must call at the start! Sends the values to the SmartDashboard and sets PIDs.*/
    public static void init()
    {
        sendProperties(); //Send the properties to the dashboard
        Robot.verticalLift.setPIDValues(Robot.verticalLift.getKP(), Robot.verticalLift.getKI(), Robot.verticalLift.getKD());
    }

    private static void sendProperties()
    {
        //Put the values on the Smart Dashboard
        SmartDashboard.putNumber("Level1", Robot.verticalLift.getPos(Level.LEVEL1));
        SmartDashboard.putNumber("Level2", Robot.verticalLift.getPos(Level.LEVEL2));
        SmartDashboard.putNumber("Level3", Robot.verticalLift.getPos(Level.LEVEL3));
        SmartDashboard.putNumber("Level4", Robot.verticalLift.getPos(Level.LEVEL4));
        SmartDashboard.putNumber("Level5", Robot.verticalLift.getPos(Level.LEVEL5));
        SmartDashboard.putNumber("Level6", Robot.verticalLift.getPos(Level.LEVEL6));
        SmartDashboard.putNumber("VerticalLift kP", Robot.verticalLift.getKP());
        SmartDashboard.putNumber("VerticalLift kI", Robot.verticalLift.getKI());
        SmartDashboard.putNumber("VerticalLift kD", Robot.verticalLift.getKD());
    }

    private static void pullProperties()
    {
        //Get properties from the SmartDashboard and change them on the robot
        //Note that sets in the subsystems should update the appropriate property in RobotProps
        Robot.verticalLift.setPos(Level.LEVEL1, SmartDashboard.getNumber("Level1"));
        Robot.verticalLift.setPos(Level.LEVEL2, SmartDashboard.getNumber("Level2"));
        Robot.verticalLift.setPos(Level.LEVEL3, SmartDashboard.getNumber("Level3"));
        Robot.verticalLift.setPos(Level.LEVEL4, SmartDashboard.getNumber("Level4"));
        Robot.verticalLift.setPos(Level.LEVEL5, SmartDashboard.getNumber("Level5"));
        Robot.verticalLift.setPos(Level.LEVEL6, SmartDashboard.getNumber("Level6"));

        double kP = SmartDashboard.getNumber("VerticalLift kP");
        double kI = SmartDashboard.getNumber("VerticalLift kI");
        double kD = SmartDashboard.getNumber("VerticalLift kD");
        Robot.verticalLift.setPIDValues(kP, kI, kD);

        RobotProps.save(); //Save the values so that they are persisted
    }
}
