package frc.robot.commands.revolver;

import static frc.robot.Constants.RevolverConstants.revolverMotorSpeed;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Revolver;

public class SpinRevolver extends CommandBase {

  private Revolver revoler;

  public SpinRevolver(){
    revoler = Revolver.getInstance();
    addRequirements(revoler);
  }

  @Override
  public void execute(){
    revoler.runRevolver(revolverMotorSpeed);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    revoler.runRevolver(0.0);
  }

}
