/**
 * Rosie Robotics 2015
 */

package org.rosie.robot;

import org.rosie.robot.commands.*;
import org.rosie.robot.commands.PropertiesCommand.Mode;
import org.rosie.robot.lib.AccessoryController;
import org.rosie.robot.lib.GameController;
import org.rosie.robot.subsystems.VerticalLift.Level;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI 
{
    public GameController      gameController     ;
    public AccessoryController accessoryController;

    Button openGripperButton;
    Button middleGripperButton;
    Button closeGripperButton;

    Button resetDTButton;

    ContactSwitchGripperCommand m_CSGCommand;

    public OI()
    {
        //Controller constructors

        gameController = new GameController(0);
        accessoryController = new AccessoryController(1);

        //Button constructors

        openGripperButton   = new JoystickButton(accessoryController, AccessoryController.OPEN_GRIPPER_FULL);
        middleGripperButton = new JoystickButton(accessoryController, AccessoryController.OPEN_GRIPPER_HALF);
        closeGripperButton  = new JoystickButton(accessoryController, AccessoryController.CLOSE_GRIPPER    );
        resetDTButton       = new JoystickButton(gameController     , GameController.START                 );

        //Initialize Properties on SmartDashboard and set PIDs

        PropertiesCommand.init();

        gameController      = new GameController(0);
        accessoryController = new AccessoryController(1);

        //Initialize Properties on SmartDashboard and set PIDs

        PropertiesCommand.init();
        
        //Note that the name and modes are reversed as the name refers to the SmartDashboard, and the Mode refers to the Robot.

        SmartDashboard.putData("Pull Properties", new PropertiesCommand(Mode.SEND));
        SmartDashboard.putData("Send Properties", new PropertiesCommand(Mode.PULL));

        //VerticalLift Level commands

        SmartDashboard.putData("Go to Level 1"   , new LevelLiftCommand(Level.LEVEL1   ));
        SmartDashboard.putData("Go to Level 2"   , new LevelLiftCommand(Level.LEVEL2   ));
        SmartDashboard.putData("Go to Tote Shute", new LevelLiftCommand(Level.TOTESHUTE));
        SmartDashboard.putData("Go to Level 3"   , new LevelLiftCommand(Level.LEVEL3   ));
        SmartDashboard.putData("Go to Level 4"   , new LevelLiftCommand(Level.LEVEL4   ));
        SmartDashboard.putData("Go to Level 5"   , new LevelLiftCommand(Level.LEVEL5   ));
        SmartDashboard.putData("Go to Level 6"   , new LevelLiftCommand(Level.LEVEL6   ));

        //Gripper commands

        SmartDashboard.putData("Open Gripper"     , new OpenGripperCommand  ());
        SmartDashboard.putData("Close Gripper"    , new CloseGripperCommand ());
        SmartDashboard.putData("Gripper to Middle", new MiddleGripperCommand());

        openGripperButton.whenPressed  ( new OpenGripperCommand    () );
        middleGripperButton.whenPressed( new MiddleGripperCommand  () );
        closeGripperButton.whenPressed ( new CloseGripperCommand   () );
        resetDTButton.whenPressed      ( new DriveTrainResetCommand() );

        m_CSGCommand = new ContactSwitchGripperCommand();
        m_CSGCommand.start();

        SmartDashboard.putData("Drive 2 feet"         	, new TestDriveCommand     (                          ));

        SmartDashboard.putData("Turn 90 Deg"			, new PIDTurnCommand( PIDTurnCommand.TurnType.Relative, 90));
        
        SmartDashboard.putData("Turn to Heading -45 Deg", new PIDTurnCommand( PIDTurnCommand.TurnType.Heading, -45));
        
        SmartDashboard.putData("To Limit"               , new LiftToLimitCommand());
        
        SmartDashboard.putData("Reset Gyro Yaw"         , new ResetYawCommand   ());
    }
}

