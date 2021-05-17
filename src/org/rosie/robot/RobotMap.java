/**
 * Rosie Robotics 2015
 */

package org.rosie.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.StatusFrameRate;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.rosie.robot.lib.LEDStrip_SPI;
import org.rosie.robot.lib.Robot4OmniDrive;

import com.kauailabs.navx_mxp.AHRS;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap
{
    public static Robot4OmniDrive   g_DriveTrainFourOmniDrive;

    public static CANTalon          g_DriveTrainCANTalonSRX1;
    public static CANTalon          g_DriveTrainCANTalonSRX2;
    public static CANTalon          g_DriveTrainCANTalonSRX3;
    public static CANTalon          g_DriveTrainCANTalonSRX4;

    public static CANTalon          g_VerticalLiftCANTalonSRX5;
    public static CANTalon          g_VerticalLiftCANTalonSRX6;

    public static Compressor        g_Compressor;

    public static LEDStrip_SPI      g_LedStrip;

    public static DoubleSolenoid    g_GripperSolenoid;

    public static DigitalInput      g_GripperReedSwitch;

    public static DigitalInput      g_GripperContactSwitchLeft;
    public static DigitalInput      g_GripperContactSwitchRight;

    public static AHRS              g_IMU; // This class can only be used w/the navX MXP.

    private static SerialPort       g_SerialPort;

    public static void init()
    {
        // ------------------------------------------------------------------
        // Instantiate each sensor/Actuator
        // ------------------------------------------------------------------

    	g_SerialPort = new SerialPort( 57600, SerialPort.Port.kMXP );
    	g_IMU 	     = new AHRS      ( g_SerialPort );

        //Create each CAN based actuator (CAN Id)

        g_DriveTrainCANTalonSRX1   = new CANTalon( 3);
        g_DriveTrainCANTalonSRX2   = new CANTalon( 2);
        g_DriveTrainCANTalonSRX3   = new CANTalon( 5);
        g_DriveTrainCANTalonSRX4   = new CANTalon( 4);

        g_VerticalLiftCANTalonSRX5 = new CANTalon( 6);
        g_VerticalLiftCANTalonSRX6 = new CANTalon( 7);

        g_Compressor               = new Compressor(10);

        // Solenoids (CAN Id of PCM is first Parameter)

        g_GripperSolenoid = new DoubleSolenoid(10, 0, 1);

        // Create each DigitalInput Sensor

        g_GripperReedSwitch         = new DigitalInput(0);
        g_GripperContactSwitchLeft  = new DigitalInput(2);
        g_GripperContactSwitchRight = new DigitalInput(3);
        
        g_LedStrip                  = new LEDStrip_SPI( Port.kOnboardCS0, 24 );

        // ------------------------------------------------------------------
        // Initialize each with appropriate defaults
        // ------------------------------------------------------------------

        // Start the Compressor

        g_Compressor.start();

        // Setup lift to use position closed loop mode

        g_VerticalLiftCANTalonSRX5.changeControlMode(CANTalon.TalonControlMode.Position);
        g_VerticalLiftCANTalonSRX6.changeControlMode(CANTalon.TalonControlMode.Follower);
        g_VerticalLiftCANTalonSRX6.set(g_VerticalLiftCANTalonSRX5.getDeviceID());

        //Set Talons to send encoder position

        g_DriveTrainCANTalonSRX1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        g_DriveTrainCANTalonSRX2.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        g_DriveTrainCANTalonSRX3.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        g_DriveTrainCANTalonSRX4.setFeedbackDevice(FeedbackDevice.QuadEncoder);

        g_VerticalLiftCANTalonSRX5.setFeedbackDevice(FeedbackDevice.QuadEncoder);

        // Enable breaking mode, and reverse the VerticalLift

        g_DriveTrainCANTalonSRX1  .enableBrakeMode(true );
        g_DriveTrainCANTalonSRX1  .enableBrakeMode(true );
        g_DriveTrainCANTalonSRX1  .enableBrakeMode(true );
        g_DriveTrainCANTalonSRX1  .enableBrakeMode(true );

        g_VerticalLiftCANTalonSRX5.enableBrakeMode (true );
        g_VerticalLiftCANTalonSRX6.reverseOutput   (true );
        g_VerticalLiftCANTalonSRX5.reverseSensor   (true );
        g_VerticalLiftCANTalonSRX5.setSafetyEnabled(false);
        
        // Set Solenoid to off
        
        g_GripperSolenoid.set(DoubleSolenoid.Value.kOff);

        // Reverse the VerticalLift

        //g_VerticalLiftCANTalonSRX5.reverseOutput(true);

        // ------------------------------------------------------------------
        // Instantiate Drive class
        // ------------------------------------------------------------------

        g_DriveTrainFourOmniDrive = new Robot4OmniDrive(g_DriveTrainCANTalonSRX1, 
                                                        g_DriveTrainCANTalonSRX2,
                                                        g_DriveTrainCANTalonSRX3, 
                                                        g_DriveTrainCANTalonSRX4,
                                                        true);

        g_DriveTrainFourOmniDrive.setSafetyEnabled(true);
        g_DriveTrainFourOmniDrive.setExpiration(0.1);
        g_DriveTrainFourOmniDrive.setSensitivity(0.5);
        g_DriveTrainFourOmniDrive.setMaxOutput(1.0);

        // ------------------------------------------------------------------
        // Add all LiveWindow controls
        // ------------------------------------------------------------------


        LiveWindow.addActuator("Gripper", "Forks"               , g_GripperSolenoid          );

        LiveWindow.addSensor("Gripper"  , "Reed Switch"         , g_GripperReedSwitch        );

        LiveWindow.addSensor("Gripper"  , "Contact Switch Left" , g_GripperContactSwitchLeft );
        LiveWindow.addSensor("Gripper"  , "Contact Switch Right", g_GripperContactSwitchRight);

        LiveWindow.addSensor("IMU"      , "Gyro"                , g_IMU                      );
    }
}


