package org.rosie.robot.commands.autonomous;

import org.rosie.robot.commands.CloseGripperCommand;
import org.rosie.robot.commands.LevelLiftCommand;
import org.rosie.robot.commands.OpenGripperCommand;
import org.rosie.robot.commands.PIDDriveForwardCommand;
import org.rosie.robot.commands.PIDDriveSidewaysCommand;
import org.rosie.robot.subsystems.VerticalLift.Level;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class GetOneShort extends CommandGroup
{
    public GetOneShort()
    {
        addSequential(new CloseGripperCommand    (            ));
        addSequential(new WaitCommand            (         0.5));
        addSequential(new LevelLiftCommand       (Level.LEVEL2));
        addSequential(new WaitCommand            (           1));
        addSequential(new PIDDriveSidewaysCommand(        -112));
        addSequential(new LevelLiftCommand       (Level.LEVEL1));
        addSequential(new WaitCommand            (         0.5));
        addSequential(new OpenGripperCommand     (            ));
        addSequential(new WaitCommand            (         0.5));
        addSequential(new PIDDriveForwardCommand (         -15));
    }
}