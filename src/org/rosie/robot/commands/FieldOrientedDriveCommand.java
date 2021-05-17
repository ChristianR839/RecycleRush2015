/**
 * Rosie Robotics 2015
 */

package org.rosie.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.rosie.robot.Robot;
import org.rosie.robot.RobotMap;
import org.rosie.robot.lib.GameController;

import com.kauailabs.navx_mxp.AHRS;

public class  FieldOrientedDriveCommand extends Command
{
    public static final double DEGTORAD = Math.PI / 180.0;
    AHRS   m_IMU = RobotMap.g_IMU;
    
    double m_TargetAngle;

    public FieldOrientedDriveCommand()
    {
        requires(Robot.driveTrain);
    }

    protected void initialize()
    {

    }

    protected void execute()
    {
        // Get Current Joystick values
        
        double forwardSpeed  = Robot.oi.gameController.getRawAxis(GameController.LEFT_Y );
        double sidewaysSpeed = Robot.oi.gameController.getRawAxis(GameController.LEFT_X );
        double rotationSpeed = Robot.oi.gameController.getRawAxis(GameController.RIGHT_X);

        // Field-oriented drive - Adjust input angle for gyro offset angle
        
        double curr_gyro_angle_degrees = 0;
        
        if ( m_IMU.isConnected() ) 
        {
            curr_gyro_angle_degrees = m_IMU.getYaw();

            SmartDashboard.putNumber ( "IMU Yaw"                      , curr_gyro_angle_degrees          );
            SmartDashboard.putNumber ( "IMU Fused Heading"            , m_IMU.getFusedHeading         () );
            SmartDashboard.putBoolean( "IMU IsConnected"              , m_IMU.isConnected             () );
            SmartDashboard.putBoolean( "IMU IsCalibrating"            , m_IMU.isCalibrating           () );
            SmartDashboard.putBoolean( "IMU IsMagneticDisturbance"    , m_IMU.isMagneticDisturbance   () );
            SmartDashboard.putBoolean( "IMU IsMagnetometerCalibrated" , m_IMU.isMagnetometerCalibrated() );
            SmartDashboard.putBoolean( "IMU IsMoving"                 , m_IMU.isMoving                () );
            SmartDashboard.putBoolean( "IMU IsRotating"               , m_IMU.isRotating              () );
        }
        
        if( (forwardSpeed == 0 && sidewaysSpeed == 0) || rotationSpeed != 0 )
            m_TargetAngle = curr_gyro_angle_degrees;
        
        double curr_gyro_angle_radians = curr_gyro_angle_degrees * DEGTORAD;
        
        double max    = Math.max(Math.abs( forwardSpeed ), Math.abs( sidewaysSpeed ));
        
        double temp   = forwardSpeed * Math.cos( curr_gyro_angle_radians ) + sidewaysSpeed * -Math.sin( curr_gyro_angle_radians );

        sidewaysSpeed = forwardSpeed * Math.sin( curr_gyro_angle_radians ) + sidewaysSpeed *  Math.cos( curr_gyro_angle_radians );

        forwardSpeed  = temp;

        double offset = max - Math.max(Math.abs(forwardSpeed), Math.abs(sidewaysSpeed));

        forwardSpeed  += offset;
        sidewaysSpeed += offset;

        // At this point, the strafe and fwd vectors have been
        // adjusted, and can be provided to the drive system.

        System.out.println("FWD: " + forwardSpeed + " : SDW: " + sidewaysSpeed);
        
        if( rotationSpeed == 0 )
            Robot.driveTrain.omniDrive(forwardSpeed, sidewaysSpeed, rotationSpeed, m_TargetAngle, true);
        else
            Robot.driveTrain.omniDrive(forwardSpeed, sidewaysSpeed, rotationSpeed,                true);
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
