package frc.robot.subsystems;

import static frc.robot.Constants.RevolverConstants.revolverMotorPort;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Revolver extends SubsystemBase {

  private static Revolver instance;

  private VictorSPX revolverMotor;

  private Revolver(){
    revolverMotor = new WPI_VictorSPX(revolverMotorPort);
  }

  public static Revolver getInstance(){
    if (instance == null){
      instance = new Revolver();
    }
    return instance;
  }

  public void runRevolver(double speed){
    revolverMotor.set(ControlMode.PercentOutput, speed);
  }

}
