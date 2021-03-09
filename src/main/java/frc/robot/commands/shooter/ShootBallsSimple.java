package frc.robot.commands.shooter;

import static frc.robot.Constants.ShooterConstants.shooterSpeed;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShootBallsSimple extends CommandBase {

  private Shooter shooter;

  public ShootBallsSimple(){
    shooter = Shooter.getInstance();
    addRequirements(shooter);
  }

  @Override
  public void initialize(){
    shooter.setFlywheelSpeed(shooterSpeed);
  }

  @Override
  public void execute() {
    shooter.setFlywheelSpeed(shooterSpeed);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    shooter.stopFlywheel();
  }

}
