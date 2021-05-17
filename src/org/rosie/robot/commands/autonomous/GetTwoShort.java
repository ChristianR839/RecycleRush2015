package org.rosie.robot.commands.autonomous;

import org.rosie.robot.commands.BarelyLiftCommand;
import org.rosie.robot.commands.CloseGripperCommand;
import org.rosie.robot.commands.LevelLiftCommand;
import org.rosie.robot.commands.OpenGripperCommand;
import org.rosie.robot.commands.PIDDriveForwardCommand;
import org.rosie.robot.commands.PIDDriveSidewaysCommand;
import org.rosie.robot.subsystems.VerticalLift.Level;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class GetTwoShort extends CommandGroup
{
    public GetTwoShort()
    {
        /*
        addSequential   (new CloseGripperCommand    (              ));
        addSequential   (new WaitCommand            (            1 ));
        
        CommandGroup s3 = new CommandGroup(); //'Step 3' - Lift while going backwards
        
        s3.addParallel  (new LevelLiftCommand       ( Level.LEVEL5 ));
        s3.addSequential(new WaitCommand            (          0.6 ));
        s3.addSequential(new PIDDriveForwardCommand (           -6 ));
        
        addSequential   (                                       s3  );
        addSequential   (new PIDDriveForwardCommand (           26 ));
        addSequential   (new WaitCommand            (          0.3 ));
        addSequential   (new PIDDriveSidewaysCommand(           29 ));
        addSequential   (new WaitCommand            (          0.3 ));
        addParallel     (new LevelLiftCommand       ( Level.LEVEL3 ));
        addSequential   (new PIDDriveForwardCommand (           40 ));
        addParallel     (new LevelLiftCommand       ( Level.LEVEL2 ));
        addSequential   (new PIDDriveSidewaysCommand(          -20 ));
        */
        
        addSequential   (new CloseGripperCommand    (              ));
        addSequential   (new WaitCommand            (          0.8 ));
        addParallel     (new LevelLiftCommand       ( Level.LEVEL3 ));
        addSequential   (new WaitCommand            (          0.6 ));
        addSequential   (new PIDDriveSidewaysCommand(           30 ));
        addSequential   (new WaitCommand            (          0.3 ));
        addSequential   (new PIDDriveForwardCommand (           60 ));
        addSequential   (new WaitCommand            (          0.3 ));
        addSequential   (new PIDDriveSidewaysCommand(          -20 ));
        addSequential   (new WaitCommand            (          0.3 ));
        
        addSequential   (new OpenGripperCommand     (              ));
        addSequential   (new WaitCommand            (         0.05 ));
        addSequential   (new LevelLiftCommand       ( Level.LEVEL1, 0.5 ));
        addSequential   (new WaitCommand            (          0.5 ));
        addSequential   (new CloseGripperCommand    (              ));
        addSequential   (new WaitCommand            (          0.5 ));
        //addSequential   (new LevelLiftCommand       ( Level.LEVEL2 ));
        addSequential   (new BarelyLiftCommand      (              ));
        addSequential   (new PIDDriveSidewaysCommand(         -110 ));
        addSequential   (new WaitCommand            (          0.2 ));
        addSequential   (new LevelLiftCommand       ( Level.LEVEL1 ));
        addSequential   (new OpenGripperCommand     (              ));
        addSequential   (new PIDDriveForwardCommand (          -15 ));
    }
}
