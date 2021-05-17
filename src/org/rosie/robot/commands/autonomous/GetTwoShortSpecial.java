package org.rosie.robot.commands.autonomous;

import org.rosie.robot.commands.CloseGripperCommand;
import org.rosie.robot.commands.LevelLiftCommand;
import org.rosie.robot.commands.PIDDriveForwardCommand;
import org.rosie.robot.commands.PIDDriveSidewaysCommand;
import org.rosie.robot.commands.PIDTurnCommand;
import org.rosie.robot.commands.PIDTurnCommand.TurnType;
import org.rosie.robot.subsystems.VerticalLift.Level;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class GetTwoShortSpecial extends CommandGroup
{
    public GetTwoShortSpecial()
    {
        addSequential   (new CloseGripperCommand    (              ));
        addSequential   (new WaitCommand            (          0.8 ));
        addParallel     (new LevelLiftCommand       ( Level.LEVEL3 ));
        addSequential   (new WaitCommand            (          0.6 ));
        addSequential   (new PIDDriveSidewaysCommand(           40 ));
        addSequential   (new WaitCommand            (          0.3 ));
        addSequential   (new PIDDriveForwardCommand (          -50 ));
        addSequential   (new WaitCommand            (          0.3 ));
        addSequential   (new PIDDriveSidewaysCommand(          -20 ));
        addSequential   (new WaitCommand            (          0.3 ));
        
        addSequential   (new PIDTurnCommand         (TurnType.Relative, 85, false));
        addSequential   (new DriveToAutoZoneBump    (                            ));
        /*
        addSequential   (new PIDDriveSidewaysCommand(         -110, 1, 0.002 ));
        addSequential   (new WaitCommand            (          0.2 ));
        addSequential   (new LevelLiftCommand       ( Level.LEVEL1 ));
        addSequential   (new OpenGripperCommand     (              ));
        addSequential   (new PIDTurnCommand         (TurnType.Relative, 75));
        addSequential   (new PIDDriveForwardCommand (          -15 ));*/
    }
}
