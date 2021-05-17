package org.rosie.robot.commands.autonomous;

import org.rosie.robot.commands.CloseGripperCommand;
import org.rosie.robot.commands.LevelLiftCommand;
import org.rosie.robot.commands.OpenGripperCommand;
import org.rosie.robot.commands.PIDDriveForwardCommand;
import org.rosie.robot.commands.PIDDriveSidewaysCommand;
import org.rosie.robot.subsystems.VerticalLift.Level;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class TwoLongFromLeft extends CommandGroup
{
	public TwoLongFromLeft()
	{
        addSequential(new CloseGripperCommand    (              ));
        addSequential(new WaitCommand            (          0.4 ));
        addParallel  (new LevelLiftCommand       ( Level.LEVEL2 ));
        addSequential(new PIDDriveForwardCommand (          -30 ));
        addSequential(new PIDDriveSidewaysCommand(          -53 ));
        addSequential(new PIDDriveForwardCommand (           30 ));
        addSequential(new OpenGripperCommand     (              ));
        addSequential(new LevelLiftCommand       ( Level.LEVEL1 ));
        addSequential(new CloseGripperCommand    (              ));
        addSequential(new WaitCommand            (          0.4 ));
        addSequential(new LevelLiftCommand       ( Level.LEVEL2 ));
        addSequential(new PIDDriveForwardCommand (         -130 ));
        addSequential(new LevelLiftCommand       ( Level.LEVEL1 ));
        addSequential(new OpenGripperCommand     (              ));
        addSequential(new PIDDriveForwardCommand (          -15 ));
     }	
}
