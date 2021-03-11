package frc.robot.commands.shooter;

import static frc.robot.Constants.ShooterConstants.turretSpeed;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;

public class CenterTurret extends CommandBase {
  private Shooter shooter;
  private Vision vision;
  private double speed = turretSpeed;

  public CenterTurret(){
    shooter = Shooter.getInstance();
    addRequirements(shooter);
    vision = Vision.getInstance();
    addRequirements(vision);
  }

  @Override
  public void initialize(){
   if (vision.getX()>0){
     speed /= -2;
   }else{
     speed /= 2;
   }
  }

  @Override
  public void execute() {
    shooter.spinTurret(speed);
  }

  @Override
  public boolean isFinished() {
    if(vision.getX()>=-0.1&&vision.getX()<=0.1){
      return true;
    }else
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    shooter.spinTurret(0.0  );
  }
}
