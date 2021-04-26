package frc.robot.commands.shooter;

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
    shooter.spinToAngle(00);

  }

  @Override
  public boolean isFinished() {
   if(shooter.checkPosition(00)){
      return true;
    }
    return false;
    }


  @Override
  public void end(boolean interrupted) {
    shooter.spinTurret(0.0);
  }

}
