package org.rosie.robot.commands.autonomous;

import org.rosie.robot.commands.CloseGripperCommand;
import org.rosie.robot.commands.LevelLiftCommand;
import org.rosie.robot.commands.OpenGripperCommand;
import org.rosie.robot.commands.PIDDriveForwardCommand;
import org.rosie.robot.commands.PIDDriveSidewaysCommand;
import org.rosie.robot.commands.PIDTurnCommand;
import org.rosie.robot.commands.PIDTurnCommand.TurnType;
import org.rosie.robot.subsystems.VerticalLift.Level;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class OneRecycleBinAndTote extends CommandGroup
{
    {
        addSequential(new CloseGripperCommand    (                       ));
        addSequential(new WaitCommand            (                   0.4 ));
        addParallel(new LevelLiftCommand         (          Level.LEVEL3 ));
        addSequential(new WaitCommand            (                     1 ));
        addSequential(new PIDDriveSidewaysCommand(            -38, 0.030 ));
        addSequential(new WaitCommand            (                   0.6 ));
        addSequential(new PIDDriveForwardCommand (             110, 0.05 ));
        addSequential(new PIDTurnCommand         ( TurnType.Relative, 75 ));
    }
}