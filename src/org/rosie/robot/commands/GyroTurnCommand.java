/**
 * Rosie Robotics 2015
 */

package org.rosie.robot.commands;

import org.rosie.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class GyroTurnCommand extends Command
{
    /**Finds how far the robot has already turned */
    double m_GyroOffset;
    /**Defines how far to turn*/
    final double m_Angle;

    /**
     * Turns the robot.
     * @param angle The amount to turn, +/- to change the direction to turn
     */
    public GyroTurnCommand(double angle)
    {
        m_Angle = angle;

        requires(Robot.driveTrain);
    }

    @Override
    protected void initialize()
    {
        m_GyroOffset = Robot.driveTrain.gyroAngle();
    }

    @Override
    protected void execute()
    {
        Robot.driveTrain.rotateDrive(m_GyroOffset + m_Angle, 0.5);
    }

    @Override
    protected boolean isFinished()
    {
        //Return true if the current angle is about the target angle
        return Math.abs(Robot.driveTrain.gyroAngle() - (m_GyroOffset + m_Angle)) < 0.1;
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
