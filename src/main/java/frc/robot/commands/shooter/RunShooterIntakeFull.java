package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Shooter;

public class RunShooterIntakeFull extends SequentialCommandGroup {

  private Shooter shooter;

  public RunShooterIntakeFull(){

    shooter = Shooter.getInstance();

    //Has the intake run then pause for 2 seconds, this is repeated 5 times
    addCommands(

        new RunShooterIntakeTimed(),
        new WaitCommand(2.0),
        new RunShooterIntakeTimed(),
        new WaitCommand(2.0),
        new RunShooterIntakeTimed(),
        new WaitCommand(2.0),
        new RunShooterIntakeTimed(),
        new WaitCommand(2.0),
        new RunShooterIntakeTimed(),
        new WaitCommand(2.0)
    );

  }

}
