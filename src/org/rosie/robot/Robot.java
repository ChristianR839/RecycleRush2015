/**
 * Rosie Robotics 2015
 */

package org.rosie.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.CANTalon.StatusFrameRate;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

import org.rosie.robot.commands.UpdateSBValuesCommand;
import org.rosie.robot.commands.autonomous.*;
import org.rosie.robot.subsystems.*;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot 
{
    UpdateSBValuesCommand m_USBVCommand;

    int session;
    Image frame;
    
    Command autonomousCommand;
    SendableChooser autonomousChooser;

    USBCamera cam;
    CameraServer camServer;
    
    public static OI oi;
    public static DriveTrain driveTrain;
    public static Gripper gripper;
    public static VerticalLift verticalLift;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() 
    {
        RobotMap.init();

        driveTrain   = new DriveTrain();
        gripper      = new Gripper();
        verticalLift = new VerticalLift();

        // OI must be constructed after subsystems. If the OI creates Commands 
        //(which it very likely will), subsystems are not guaranteed to be 
        // constructed yet. Thus, their requires() statements may grab null 
        // pointers. Bad news. Don't move it.

        oi = new OI();

        autonomousChooser = new SendableChooser();
        autonomousChooser.addDefault( "Auto: Do Nothing"                           , new DoNothing                    () );
        autonomousChooser.addObject ( "Auto: Drive To Auto Zone"                   , new DriveToAutoZone              () );
        autonomousChooser.addObject ( "Auto: Drive To Auto Zone (Bump)"            , new DriveToAutoZoneBump          () );
        //autonomousChooser.addObject ( "Auto: Get One (Long)"                       , new GetOneLong                   () );
        //autonomousChooser.addObject ( "Auto: Get One (Short)"                      , new GetOneShort                  () );
        //autonomousChooser.addObject ( "Auto: Get One Recycle Bin (Bump)"           , new GetOneRecycleBin             () );
        autonomousChooser.addObject ( "Auto: Get One Recycle Bin (Side)"           , new GetRecycleBinSide            () );
        //autonomousChooser.addObject ( "Auto: Get One Recycle Bin (Backwards)"      , new GetOneRecycleBinBackwards    () );
        //autonomousChooser.addObject ( "Auto: Get One Recycle Bin (Backwards, Bump)", new GetOneRecycleBinBackwardsBump() );
        //autonomousChooser.addObject ( "Auto: Get Two (Short)"                      , new GetTwoShort                  () );
        //autonomousChooser.addObject ( "Auto: Get Two Short (Special)"              , new GetTwoShortSpecial           () );
        //autonomousChooser.addObject ( "Auto: Get Two (Long, From left)"            , new TwoLongFromLeft              () );
        //autonomousChooser.addObject ( "Auto: Get Two (Long, From right)"           , new TwoLongFromRight             () );
        autonomousChooser.addObject ( "Auto: Get One Tote and Recycle Bin"         , new OneRecycleBinAndTote         () );
        autonomousChooser.addObject ( "Auto: Get One Tote and Recycle Bin (Bump)"  , new OneRecycleBinAndToteBump     () );
        SmartDashboard.putData( "Autonomous", autonomousChooser );

        // instantiate the command used for the autonomous period
        //autonomousCommand = new RunAutonomousCommand();

        m_USBVCommand = new UpdateSBValuesCommand();
        m_USBVCommand.start();
        
        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
        /*
        // the camera name (ex "cam0") can be found through the roborio web interface
        session = NIVision.IMAQdxOpenCamera("cam1",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
        
        NIVision.IMAQdxStartAcquisition(session);
        */
        /*
        camServer = CameraServer.getInstance();//.startAutomaticCapture("cam1");
        camServer.setQuality(30);
        
        cam = new USBCamera("cam1");
        cam.openCamera();
        cam.setFPS(60);
        
        cam.setSize(320, 240);
        cam.updateSettings();
        */
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit()
    {
        //cam.stopCapture();
        m_USBVCommand.start();
    }

    public void disabledPeriodic() 
    {
        Scheduler.getInstance().run();
    }

    public void autonomousInit() 
    {
        //cam.startCapture();
    	RobotMap.g_IMU.zeroYaw();
    	
        m_USBVCommand.start();
        
        autonomousCommand = (Command) autonomousChooser.getSelected();
        autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() 
    {
        Scheduler.getInstance().run();
    }

    public void teleopInit() 
    {
        //cam.startCapture();
        m_USBVCommand.start();
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null)
            autonomousCommand.cancel();

        RobotMap.g_VerticalLiftCANTalonSRX5.reverseSensor   (true );
        //RobotMap.g_DriveTrainCANTalonSRX1.setVoltageRampRate(8.0);
        //RobotMap.g_DriveTrainCANTalonSRX2.setVoltageRampRate(0.2);
        //RobotMap.g_DriveTrainCANTalonSRX3.setVoltageRampRate(0.4);
        //RobotMap.g_DriveTrainCANTalonSRX4.setVoltageRampRate(6.66);
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() 
    {
       // NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);
        /*
        NIVision.IMAQdxGrab(session, frame, 1);
        //NIVision.imaqDrawShapeOnImage(frame, frame, rect,
            //    DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 0.0f);
        */
        
        //cam.getImage(frame);
        //camServer.setImage(frame);
        
        //CameraServer.getInstance().setImage(frame);
        
        if(verticalLift.limitSwitchHit())
            verticalLift.resetEnc();
        
        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() 
    {
        LiveWindow.run();
    }
}
