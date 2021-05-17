package org.rosie.robot.commands.autonomous;

import org.rosie.robot.commands.CloseGripperCommand;
import org.rosie.robot.commands.LevelLiftCommand;
import org.rosie.robot.commands.OpenGripperCommand;
import org.rosie.robot.commands.PIDDriveForwardCommand;
import org.rosie.robot.commands.PIDTurnCommand;
import org.rosie.robot.commands.PIDTurnCommand.TurnType;
import org.rosie.robot.subsystems.VerticalLift.Level;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class GetOneRecycleBinBackwards extends CommandGroup
{
    public GetOneRecycleBinBackwards()
    {
        addSequential(new CloseGripperCommand   (                       ));
        addParallel(new LevelLiftCommand        (          Level.LEVEL4 ));
        addSequential(new WaitCommand           (                     1 ));
        addSequential(new PIDDriveForwardCommand(                  -155 ));
        addSequential(new WaitCommand           (                   1.5 ));
        addSequential(new PIDTurnCommand        ( TurnType.Relative, 75 ));
    }
}