/**
 * Rosie Robotics 2015
 */

package org.rosie.robot.commands;

import org.rosie.robot.Robot;
import org.rosie.robot.subsystems.VerticalLift.Level;

import edu.wpi.first.wpilibj.command.Command;

public class LevelLiftCommand extends Command
{
    /**The level to go to*/
    final Level m_Level;

    /**
     * A command to bring the VerticalLift to one of the 6 presets
     * 
     * @param level The Level-preset to go to
     */
    public LevelLiftCommand(Level level)
    {
        this.m_Level = level;

        requires(Robot.verticalLift);
    }

    /**
     * A command to bring the VerticalLift to one of the 6 presets
     * 
     * @param level The Level-preset to go to
     */
    public LevelLiftCommand(Level level, double timeout)
    {
        super(timeout);
        
        this.m_Level = level;

        requires(Robot.verticalLift);
    }

    @Override
    protected void initialize()
    {
        Robot.verticalLift.positionMode(true);
    }

    @Override
    protected void execute()
    {
        Robot.verticalLift.goTo(m_Level);
    }

    @Override
    protected boolean isFinished()
    {
        return Robot.verticalLift.isAt(m_Level);
    }

    @Override
    protected void end()
    {
        
    }

    @Override
    protected void interrupted()
    {
        Robot.verticalLift.stopPos();
    }
}
