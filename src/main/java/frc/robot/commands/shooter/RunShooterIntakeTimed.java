package frc.robot.commands.shooter;

import static frc.robot.Constants.ShooterConstants.shooterI;
import static frc.robot.Constants.ShooterConstants.shooterIntakeSpeed;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterIntake;

public class RunShooterIntakeTimed extends ParallelRaceGroup {

  private ShooterIntake shooterIntake;

  public RunShooterIntakeTimed(){

    shooterIntake = ShooterIntake.getInstance();

    //Sets the command up so the intake runs with 2 second pauses
    addCommands(

        new simpleRunIntake(),
        new WaitCommand(2.0));
  }

}
