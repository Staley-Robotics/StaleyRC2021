package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import frc.robot.subsystems.Shooter;

public class RunShooterFull extends ParallelRaceGroup {

  private Shooter shooter;

  public RunShooterFull(){

    shooter = Shooter.getInstance();

    //Takes the shooter and intake (with pauses) commands and runs them at the same time
    addCommands(

        //new ShootBallsSimple(),
        new RunShooterIntakeFull()


    );
  }

}
