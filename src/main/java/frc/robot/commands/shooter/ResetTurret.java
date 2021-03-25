package frc.robot.commands.shooter;

import static frc.robot.Constants.ShooterConstants.turretSpeed;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ResetTurret extends CommandBase {
  private Shooter shooter;

  public ResetTurret (){
    shooter = Shooter.getInstance();
    addRequirements(shooter);
  }

  @Override
  public void execute() {
    shooter.spinTurret(turretSpeed);

  }

  @Override
  public boolean isFinished() {
   if(shooter.getTurretMotorPosition() == 0){
      return true;
    }

    return false;
    }


  @Override
  public void end(boolean interrupted) {
    shooter.spinTurret(0.0);
  }

}
