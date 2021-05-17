/**
 * Rosie Robotics 2015
 */

package org.rosie.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.rosie.robot.Robot;
import org.rosie.robot.lib.GameController;

public class  JoystickDriveCommand extends Command
{
    double m_TargetAngle;
    boolean useTarget;
    
    public JoystickDriveCommand()
    {
        requires(Robot.driveTrain);
    }

    protected void initialize()
    {

    }

    protected void execute()
    {
        double forwardSpeed  = Robot.oi.gameController.getRawAxis(GameController.LEFT_Y );
        double sidewaysSpeed = Robot.oi.gameController.getRawAxis(GameController.LEFT_X );
        double rotationSpeed = Robot.oi.gameController.getRawAxis(GameController.RIGHT_X);
        
        if( !GameController.inDeadzone( sidewaysSpeed ) && GameController.inDeadzone( rotationSpeed) )
        {
            if( !useTarget)
            {
                m_TargetAngle = Robot.driveTrain.gyroAngle();
                useTarget = true;
            }
            
            Robot.driveTrain.omniDrive(forwardSpeed, sidewaysSpeed, rotationSpeed, m_TargetAngle, true);
        }
        else
        {
            useTarget = false;
            
            Robot.driveTrain.omniDrive(forwardSpeed, sidewaysSpeed, rotationSpeed,                true);
        }
        
        /*
        if( GameController.inDeadzone( sidewaysSpeed ) || !GameController.inDeadzone( rotationSpeed ) )
            m_TargetAngle = Robot.driveTrain.gyroAngle();
        
        if( sidewaysSpeed != 0 && rotationSpeed == 0 )
            Robot.driveTrain.omniDrive(forwardSpeed, sidewaysSpeed, rotationSpeed, m_TargetAngle, true);
        else
            Robot.driveTrain.omniDrive(forwardSpeed, sidewaysSpeed, rotationSpeed,                true);
        */
        //Robot.driveTrain.omniRotateDrive(Robot.oi.gameController, true);
    }

    protected boolean isFinished()
    {
        return false;
    }

    protected void end()
    {
        Robot.driveTrain.stop();
    }

    protected void interrupted()
    {
        end();
    }
}
