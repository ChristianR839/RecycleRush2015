package org.rosie.robot.commands.autonomous;

import org.rosie.robot.commands.PIDDriveForwardCommand;
import org.rosie.robot.commands.PIDTurnCommand;
import org.rosie.robot.commands.PIDTurnCommand.TurnType;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class DriveToAutoZone extends CommandGroup
{
    {
        addSequential(new PIDDriveForwardCommand(                  112));
        addSequential(new WaitCommand           (                  0.6));
        addSequential(new PIDTurnCommand        (TurnType.Relative, 75));
        addSequential(new PIDDriveForwardCommand(              0.1, -5));
    }
}
