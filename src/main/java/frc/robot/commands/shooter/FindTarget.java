package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FindTarget extends SequentialCommandGroup {

  public FindTarget(){

    addCommands(
        new RotateTurret(),
        new CenterTurret());
  }

}
