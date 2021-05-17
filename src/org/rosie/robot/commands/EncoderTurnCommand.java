/**
 * Rosie Robotics 2015
 */

package org.rosie.robot.commands;

import org.rosie.robot.Robot;
import org.rosie.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class EncoderTurnCommand extends Command 
{
    /**The angle that the robot will turn when this command is called*/
    final double m_Angle;
    /**The direction to turn, true for left, false for right*/
    final boolean m_TurnLeft;
    /**See initialize()*/
    double m_Target;

    /**A preset for turning a certain angle*/
    public EncoderTurnCommand(double angle)
    {
        m_TurnLeft = angle < 0;
        m_Angle = DriveTrain.degreesToTicks(angle);

        requires(Robot.driveTrain);
    }

    @Override
    protected void initialize() 
    {
            m_Target = Robot.driveTrain.forwardEncPosition() + m_Angle;
    }

    @Override
    protected void execute()
    {
        if(m_TurnLeft)
            Robot.driveTrain.rotateDrive(-0.75);
        else
            Robot.driveTrain.rotateDrive( 0.75);
    }

    @Override
    protected boolean isFinished() 
    {
        if(m_TurnLeft)
            return m_Target >= Robot.driveTrain.forwardEncPosition();
        else
            return m_Target <= Robot.driveTrain.forwardEncPosition();
    }

    @Override
    protected void end() 
    {
        Robot.driveTrain.stop();
    }

    @Override
    protected void interrupted() 
    {
        end(); 
    }
}
