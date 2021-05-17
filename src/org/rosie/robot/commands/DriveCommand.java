/**
 * Rosie Robotics 2015
 */

package org.rosie.robot.commands;

import org.rosie.robot.Robot;
import org.rosie.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveCommand extends Command 
{
    /**The directions that the robot may move in*/
    public enum Direction
    {
        FORWARD, BACKWARD, LEFT, RIGHT;
    }

    /**The direction that the robot will move when this command is called*/
    final Direction m_Direction;
    /**The distance that the robot will move when this command is called*/
    final double m_Distance;
    /**See initialize()*/
    double m_Target;

    /**A preset for driving a certain distance in one of the available directions*/
    public DriveCommand(Direction direction, double distance)
    {
        m_Direction = direction;
        if(direction == Direction.FORWARD || direction == Direction.LEFT)
            m_Distance =  DriveTrain.inchesToTicks(distance);
        else
            m_Distance = -DriveTrain.inchesToTicks(distance);

        requires(Robot.driveTrain);
    }

    @Override
    protected void initialize() 
    {
        //Calculate m_EncoderOffset which is either the forward or sideways encoder's current distance

        //This variable is so that the robot will travel an EXTRA m_Distance distance, not just drive until the encoder
        //is at that m_Distance

        if(m_Direction == Direction.FORWARD || m_Direction == Direction.BACKWARD)
            m_Target = Robot.driveTrain.forwardEncPosition()  + m_Distance;
        else if(m_Direction == Direction.RIGHT || m_Direction == Direction.LEFT)
            m_Target = Robot.driveTrain.sidewaysEncPosition() + m_Distance;
    }

    @Override
    protected void execute()
    {
        //Drive the robot in the correct direction
        //FORWARD  goes -0.5 straight
        //BACKWARD goes  0.5 straight
        //RIGHT    goes  0.5 straight, encoder decreases
        //LEFT     goes -0.5 straight, encoder increases

        if(m_Direction == Direction.FORWARD)
            Robot.driveTrain.driveStraight(-1);
        else if(m_Direction == Direction.BACKWARD)
            Robot.driveTrain.driveStraight( 1);
        else if(m_Direction == Direction.RIGHT)
            Robot.driveTrain.driveSideways( 1);
        else if(m_Direction == Direction.LEFT)
            Robot.driveTrain.driveSideways(-1);
    }

    @Override
    protected boolean isFinished() 
    {
        if(m_Direction == Direction.FORWARD)
            return m_Target <= Robot.driveTrain.forwardEncPosition();
        else if(m_Direction == Direction.BACKWARD)
            return m_Target >= Robot.driveTrain.forwardEncPosition();
        else if(m_Direction == Direction.LEFT)
            return m_Target <= Robot.driveTrain.sidewaysEncPosition();
        else
            return m_Target >= Robot.driveTrain.sidewaysEncPosition();
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
