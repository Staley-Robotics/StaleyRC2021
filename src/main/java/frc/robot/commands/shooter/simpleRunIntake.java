package frc.robot.commands.shooter;

import static frc.robot.Constants.ShooterConstants.shooterIntakeSpeed;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class simpleRunIntake extends CommandBase {
  private Shooter shooter;

  public simpleRunIntake(){
    shooter = Shooter.getInstance();
    addRequirements(shooter);
  }

  @Override
  public void initialize(){
    shooter.runShooterIntake(shooterIntakeSpeed);

  }

  @Override
  public void execute() {
    shooter.runShooterIntake(shooterIntakeSpeed);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    shooter.runShooterIntake(0.00);
  }

}
