package org.rosie.robot.commands.autonomous;

import org.rosie.robot.commands.CloseGripperCommand;
import org.rosie.robot.commands.LevelLiftCommand;
import org.rosie.robot.commands.OpenGripperCommand;
import org.rosie.robot.commands.PIDDriveForwardCommand;
import org.rosie.robot.subsystems.VerticalLift.Level;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GetOneLong extends CommandGroup
{
    public GetOneLong()
    {
        addSequential(new CloseGripperCommand   (            ));
        addParallel  (new LevelLiftCommand      (Level.LEVEL2));
        addSequential(new PIDDriveForwardCommand(         -85));
        addSequential(new LevelLiftCommand      (Level.LEVEL1));
        addSequential(new OpenGripperCommand    (            ));
        addSequential(new PIDDriveForwardCommand(          -5));
    }
}
