/**
 * Rosie Robotics 2015
 */

package org.rosie.robot.subsystems;

import org.rosie.robot.RobotMap;
import org.rosie.robot.commands.JoystickDriveCommand;
import org.rosie.robot.lib.Robot4OmniDrive;

import com.kauailabs.navx_mxp.AHRS;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.tables.ITable;

public class DriveTrain extends Subsystem implements LiveWindowSendable
{
    public static final double TICKS_PER_DEGREE    = 13.97;
    public static final double TICKS_PER_INCH      = 80.0;//63.5491; // (1440 / WHEEL_CIRCUMFERENCE)
    public static final double ROBOT_CIRCUMFERENCE = 12.5 * Math.sqrt(2) * Math.PI;
    public static final double WHEEL_CIRCUMFERENCE = 18.84;

    /**Targeting is for when the gyro is in use, Roaming is when the gyro is not in use*/
    private enum StatusEnum
    {
        Targeting, Roaming
    }

    Robot4OmniDrive m_FourOmniDrive = RobotMap.g_DriveTrainFourOmniDrive;

    CANTalon        m_CANTalonSRX1  = RobotMap.g_DriveTrainCANTalonSRX1;
    CANTalon        m_CANTalonSRX2  = RobotMap.g_DriveTrainCANTalonSRX2;
    CANTalon        m_CANTalonSRX3  = RobotMap.g_DriveTrainCANTalonSRX3;
    CANTalon        m_CANTalonSRX4  = RobotMap.g_DriveTrainCANTalonSRX4;

    AHRS            m_IMU           = RobotMap.g_IMU;
    
    double          m_dTargetAngle  = 0;
    StatusEnum      m_eStatus       = StatusEnum.Roaming;
    
    private ITable  m_table;
    
    public void initDefaultCommand()
    {
        setDefaultCommand(new JoystickDriveCommand());
        //setDefaultCommand(new FieldOrientedDriveCommand());
    }

    public void omniDrive(double forwardSpeed, double sidewaysSpeed, double rotationSpeed, boolean scaleRotate, boolean squareInputs)
    {
        m_FourOmniDrive.omniDrive(forwardSpeed, sidewaysSpeed, rotationSpeed, scaleRotate, 0, 0, squareInputs);
    }
    
    public void omniDrive(double forwardSpeed, double sidewaysSpeed, double rotationSpeed, boolean squareInputs)
    {
        omniDrive(forwardSpeed, sidewaysSpeed, rotationSpeed, true, squareInputs);
    }
    
    public void omniDrive(double forwardSpeed, double sidewaysSpeed, double rotationSpeed, boolean scaleRotate, boolean squareInputs, boolean scaleBackwards)
    {
        m_FourOmniDrive.omniDrive(forwardSpeed, sidewaysSpeed, rotationSpeed, scaleRotate, 0, 0, squareInputs, true);
    }

    public void omniDrive(double forwardSpeed, double sidewaysSpeed, double rotationSpeed,  double dHeading, boolean squareInputs)
    {
        double dCurrentAngle = m_IMU.getYaw();
        
        m_FourOmniDrive.omniDrive(forwardSpeed, sidewaysSpeed, rotationSpeed, dCurrentAngle, dHeading, squareInputs);
    }

    public void stop()
    {
        m_FourOmniDrive.stop();
    }

    public void driveStraight(double power)
    {
        omniDrive(power, 0.0, 0.0, false);
    }

    public void driveSideways(double power)
    {
        omniDrive(0.0, power, 0.0, false);
    }

    public void rotateDrive(double power)
    {
        omniDrive(0.0, 0.0, power, false);
    }

    public void rotateDrive(double angle, double speed)
    {
        m_FourOmniDrive.omniDrive(0, 0, speed, gyroAngle(), angle, false);
    }

    public double forwardEncPosition()
    {
        return m_CANTalonSRX1.getEncPosition();
    }

    public double sidewaysEncPosition()
    {
        return m_CANTalonSRX2.getEncPosition();
    }

    public static double inchesToTicks(double inches)
    {
        return inches * TICKS_PER_INCH;
    }
    
    public static double degreesToTicks(double degrees)
    {
        double r = degrees * TICKS_PER_DEGREE;
        return r;
    }

    public double gyroAngle()
    {
        return m_IMU.getYaw();  // Gyro.getAngle();
    }
    
    public void resetGyro()
    {
        m_IMU.zeroYaw();
    }
    
    public void useForwardAngle()
    {
        m_dTargetAngle = m_IMU.getYaw();    // m_Gyro.getAngle();
    }

    public void resetTargeting()
    {
        m_eStatus = StatusEnum.Roaming;
    }

    public String driveMode()
    {
        return m_eStatus == StatusEnum.Roaming ? "Roaming" : "Targeting";
    }

    // SmartDashboard table stuff

    @Override
    public String getSmartDashboardType()
    {
        return "MotorSpeed";
    }

    @Override
    public void initTable(ITable subtable) 
    {
        m_table = subtable;
        updateTable();
    }

    @Override
    public void updateTable() 
    {
        if (m_table != null) 
        {
            m_table.putNumber("FrontForwardSpeed" , m_CANTalonSRX1.get());
            m_table.putNumber("BackForwardSpeed"  , m_CANTalonSRX4.get());
            m_table.putNumber("FrontSidewaysSpeed", m_CANTalonSRX3.get());
            m_table.putNumber("BackSidewaysSpeed" , m_CANTalonSRX2.get());
        }
    }

    @Override
    public ITable getTable()
    {
        return m_table;
    }

    @Override
    public void startLiveWindowMode()
    {

    }

    @Override
    public void stopLiveWindowMode()
    {

    } 
}

