package frc.robot.commands.shooter;

import static frc.robot.Constants.ShooterConstants.maxEncounterSteps;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;

public class RotateTurret extends CommandBase {

  private Shooter shooter;
  private Vision vision;

  private double speed;
  private int switches;

  public RotateTurret(){
    speed = Constants.ShooterConstants.turretSpeed;
    switches = 0;

    shooter = Shooter.getInstance();
    vision = Vision.getInstance();
    addRequirements(shooter, vision);
  }

  @Override
  public void execute() {
    if(switches == 0){
      shooter.spinToAngle(maxEncounterSteps);
      if(shooter.checkPosition(maxEncounterSteps)){
        switches++;
      }
    }else if(switches == 1){
      shooter.spinToAngle(-maxEncounterSteps);
      if(shooter.checkPosition(maxEncounterSteps)){
        switches++;
      }
    }
  }

  @Override
  public boolean isFinished() {
    if(vision.getV() == 1){
      return true;
    }
    else if(switches==2){
      return true;
    }
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    shooter.spinTurret(0.0);
  }

}
