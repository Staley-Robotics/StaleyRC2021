package frc.robot.commands.shooter;

import static frc.robot.Constants.ShooterConstants.shooterIntakeSpeed;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterIntake;

public class simpleRunIntake extends CommandBase {
  private ShooterIntake shooterIntake;

  public simpleRunIntake(){
    shooterIntake = ShooterIntake.getInstance();
    addRequirements(shooterIntake);
  }

  @Override
  public void initialize(){
    shooterIntake.runShooterIntake(shooterIntakeSpeed);

  }

  @Override
  public void execute() {
    shooterIntake.runShooterIntake(shooterIntakeSpeed);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    shooterIntake.runShooterIntake(0.00);
  }

}
