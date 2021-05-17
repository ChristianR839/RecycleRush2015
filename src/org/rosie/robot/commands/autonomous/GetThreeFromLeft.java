package org.rosie.robot.commands.autonomous;

import org.rosie.robot.commands.*;
import org.rosie.robot.subsystems.VerticalLift.Level;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class GetThreeFromLeft extends CommandGroup
{
    public GetThreeFromLeft()
    {
        addSequential(new CloseGripperCommand    (              ));
        addSequential(new WaitCommand            (          0.4 ));
        addSequential(new LevelLiftCommand       ( Level.LEVEL4 ));
        addSequential(new WaitCommand            (            2 ));
        addSequential(new PIDDriveSidewaysCommand(           53 ));
        addSequential(new LevelLiftCommand       ( Level.LEVEL2 ));
        addSequential(new WaitCommand            (            1 ));
        addSequential(new OpenGripperCommand     (              ));
        addSequential(new LevelLiftCommand       ( Level.LEVEL1 ));
        addSequential(new CloseGripperCommand    (              ));
        addSequential(new WaitCommand            (          0.4 ));
        addSequential(new LevelLiftCommand       ( Level.LEVEL4 ));
        addSequential(new PIDDriveSidewaysCommand(           53 ));
        addSequential(new LevelLiftCommand       ( Level.LEVEL2 ));
        addSequential(new OpenGripperCommand     (              ));
        addSequential(new LevelLiftCommand       ( Level.LEVEL1 ));
        addSequential(new CloseGripperCommand    (              ));
        addSequential(new WaitCommand            (          0.4 ));
        addParallel  (new LevelLiftCommand       ( Level.LEVEL2 ));
        addSequential(new PIDDriveForwardCommand (         -135 ));
        addSequential(new OpenGripperCommand     (              ));
        addSequential(new PIDDriveForwardCommand (           -5 ));
    }
}
