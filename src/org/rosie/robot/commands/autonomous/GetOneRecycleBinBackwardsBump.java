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

public class GetOneRecycleBinBackwardsBump extends CommandGroup
{
    public GetOneRecycleBinBackwardsBump()
    {
        addSequential(new CloseGripperCommand    (                       ));
        addParallel(new LevelLiftCommand         (          Level.LEVEL3 ));
        addSequential(new WaitCommand            (                   0.5 ));
        addSequential(new PIDTurnCommand         ( TurnType.Relative, 75 ));
        addSequential(new PIDDriveSidewaysCommand(                   -12 ));
        addSequential(new WaitCommand            (                     5 ));
        addSequential(new PIDDriveSidewaysCommand(                    30 ));
        addSequential(new PIDDriveSidewaysCommand(                  -175 ));
        addSequential(new WaitCommand            (                   1.5 ));
    }
}