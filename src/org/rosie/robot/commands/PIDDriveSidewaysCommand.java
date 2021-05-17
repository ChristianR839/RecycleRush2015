package org.rosie.robot.commands;

import org.rosie.robot.Robot;
import org.rosie.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class PIDDriveSidewaysCommand extends PIDCommand
{
    final PIDController oPIDController;
    final double m_TargetDistance;
    
    double m_Heading;
    
    final double m_MaxSpeed;
    
    /**
     * @param distanceInches The distance to travel, in inches.
     */
    public PIDDriveSidewaysCommand(double distanceInches)
    {
        this(distanceInches, 2, 0.002);
    }
    
    public PIDDriveSidewaysCommand(double distanceInches, double kP)
    {
        this(distanceInches, 2, kP);
    }
    
    public PIDDriveSidewaysCommand(double distanceInches, double maxSpeed, double kP)
    {
        super( kP, 0.0, 0.0 );
        
        m_TargetDistance = DriveTrain.inchesToTicks(distanceInches);
        
        m_MaxSpeed = maxSpeed;
        
        requires(Robot.driveTrain);

        oPIDController = getPIDController();
        
        oPIDController.setAbsoluteTolerance(160);
        
        LiveWindow.addActuator("PID Drivetrain", "Drive_Sideways", oPIDController);
    }

    @Override
    protected double returnPIDInput()
    {
        return Robot.driveTrain.sidewaysEncPosition();
    }

    @Override
    protected void usePIDOutput(double output)
    {
        Robot.driveTrain.omniDrive(0, -output / m_MaxSpeed, 0, m_Heading, false);
    }

    @Override
    protected void initialize()
    {
        final double target = m_TargetDistance + Robot.driveTrain.sidewaysEncPosition();
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
