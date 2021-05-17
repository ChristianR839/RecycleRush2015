package org.rosie.robot.commands;

import org.rosie.robot.Robot;
import org.rosie.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class PIDDriveForwardCommand extends PIDCommand
{
    final PIDController oPIDController;
    final double m_TargetDistance;
    
    double m_Heading;
    
    /**
     * @param distanceInches The distance to travel, in inches.
     */
    public PIDDriveForwardCommand(double distanceInches)
    {
        super( 0.002, 0.0, 0.0 );
        
        m_TargetDistance = DriveTrain.inchesToTicks(distanceInches);
        
        requires(Robot.driveTrain);

        oPIDController = getPIDController();
        
        oPIDController.setAbsoluteTolerance(160);
        
        LiveWindow.addActuator("PID Drivetrain", "Drive_Forward", oPIDController);
    }
    
    /**
     * @param distanceInches The distance to travel, in inches.
     * @param kP The P value for the PID.
     */
    public PIDDriveForwardCommand(double distanceInches, double kP)
    {
        super( kP, 0.0, 0.0 );
        
        m_TargetDistance = DriveTrain.inchesToTicks(distanceInches);
        
        requires(Robot.driveTrain);

        oPIDController = getPIDController();
        
        oPIDController.setAbsoluteTolerance(160);
        
        LiveWindow.addActuator("PID Drivetrain", "Drive_Forward", oPIDController);
    }

    @Override
    protected double returnPIDInput()
    {
        return Robot.driveTrain.forwardEncPosition();
    }

    @Override
    protected void usePIDOutput(double output)
    {
        Robot.driveTrain.omniDrive(-output / 1.4, 0, 0, m_Heading, false);
    }

    @Override
    protected void initialize()
    {
        final double target = m_TargetDistance + Robot.driveTrain.forwardEncPosition();
        oPIDController.setSetpoint(target);
        
        m_Heading = Robot.driveTrain.gyroAngle();
    }

    @Override
    protected void execute()
    {
        
    }

    @Override
    protected boolean isFinished()
    {
        //double set = oPIDController.getSetpoint();
        //double cur = Robot.driveTrain.forwardEncPosition();
        return oPIDController.onTarget();
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
