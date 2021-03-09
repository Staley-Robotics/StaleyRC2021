package frc.robot.commands.shooter;

import static frc.robot.Constants.ShooterConstants.turretSpeed;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class RotateTurret extends CommandBase {
  private Shooter shooter;
  private double speed = turretSpeed;
  private int switches = 0;

  public RotateTurret(){
    shooter = Shooter.getInstance();
    addRequirements(shooter);
  }

  @Override
  public void initialize(){
    shooter.spinTurret(speed);
  }

  @Override
  public void execute() {
    if (Math.abs(shooter.getTurretMotorPosition())>=1204.705882){
      speed = speed*-1;
      switches++;
    }
      shooter.spinTurret(speed);
  }

  @Override
  public boolean isFinished() {
    if(switches==2){
      return true;
    }
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    shooter.spinTurret(0.0);
  }

}
