package frc.robot.commands.shooter;

import static frc.robot.Constants.ShooterConstants.shooterIntakeSpeed;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Shooter;

public class RunShooterIntakeTimed extends ParallelRaceGroup {

  private Shooter shooter;

  public RunShooterIntakeTimed(){

    shooter = Shooter.getInstance();

    addCommands(

        new RunCommand(()->shooter.runShooterIntake(shooterIntakeSpeed), shooter),
        new WaitCommand(2.0));
  }

}
