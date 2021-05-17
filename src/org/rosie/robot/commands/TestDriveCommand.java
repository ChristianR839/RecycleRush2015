package org.rosie.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestDriveCommand extends CommandGroup
{
    public TestDriveCommand()
    {
        addSequential(new PIDDriveForwardCommand(135));
    }
}
