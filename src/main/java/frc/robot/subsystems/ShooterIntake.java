package frc.robot.subsystems;

import static frc.robot.Constants.ShooterConstants.shooterIntakeMotorPort;

import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterIntake extends SubsystemBase {

  private static ShooterIntake instance;

  private VictorSP intakeMotor;

  private ShooterIntake(){
    intakeMotor = new VictorSP(shooterIntakeMotorPort);
  }

  public static ShooterIntake getInstance() {
    if (instance == null) {
      instance = new ShooterIntake();
    }
    return instance;
  }

  public void runShooterIntake(double speed){
    intakeMotor.set(speed);
  }

}
