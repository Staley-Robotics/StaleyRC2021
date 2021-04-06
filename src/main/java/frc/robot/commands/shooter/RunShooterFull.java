package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import frc.robot.subsystems.Shooter;

public class RunShooterFull extends ParallelRaceGroup {

  private Shooter shooter;

  public RunShooterFull(){

    shooter = Shooter.getInstance();

    addCommands(

        new ShootBallsSimple(),
        new RunShooterIntakeFull()


    );
  }

}
