package org.rosie.robot.commands;

import org.rosie.robot.Robot;
import org.rosie.robot.RobotMap;

import com.kauailabs.navx_mxp.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class PIDTurnCommand extends PIDCommand
{
    public enum TurnType { Relative, Heading };
    
    protected AHRS      m_IMU;
    protected double    m_dTargetAngle;
    protected TurnType  m_eType;
    protected final boolean m_ScaleRotate;

    public PIDTurnCommand( TurnType eType, double dAngleDegrees )
    {
        this(eType, dAngleDegrees, true);
    }
    
    public PIDTurnCommand( TurnType eType, double dAngleDegrees, boolean scaleRotate )
    {
        super( scaleRotate ? 0.1 : 0.01, 0, 0 );

        m_IMU          = RobotMap.g_IMU;
        m_eType        = eType;
        m_dTargetAngle = dAngleDegrees;
        m_ScaleRotate  = scaleRotate;

        // Configure the PID Controller settings
        PIDController oPIDController = getPIDController();

        oPIDController.setInputRange( -180,  180 );
        oPIDController.setAbsoluteTolerance(5); 
        oPIDController.setContinuous();

        requires(Robot.driveTrain);

        LiveWindow.addActuator("PID Drivetrain", "Turn", getPIDController());
    }

    @Override
    protected double returnPIDInput()
    {
        double dYaw = m_IMU.getYaw();

//      System.out.print( " Input: " );
//      System.out.print( dYaw );

        return dYaw;
    }

    @Override
    protected void usePIDOutput(double output) 
    {
//      System.out.print(" usePIDOutput: ");
//      System.out.println( output );

        Robot.driveTrain.omniDrive( 0,  0, output, m_ScaleRotate, true );
    }

    @Override
    protected void initialize() 
    {
        double dTarget = m_dTargetAngle;

        if ( m_eType == TurnType.Relative )
        {
            dTarget += m_IMU.getYaw();

            if      ( dTarget >  180 )
                dTarget -= 360;
            else if ( dTarget < -180 )
                dTarget += 360;
            // -=>TODO: Need to deal with > 180 and < -180
        }

        getPIDController().setSetpoint( dTarget );
    }

    @Override
    protected void execute()
    {
        // PIDCommand enables the PIDController... nothing to do here.
    }

    @Override
    protected boolean isFinished() 
    {
        return getPIDController().onTarget();
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
