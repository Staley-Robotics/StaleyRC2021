package frc.robot.subsystems;

import static frc.robot.Constants.DriveConstants.kD;
import static frc.robot.Constants.DriveConstants.kP;
import static frc.robot.Constants.ShooterConstants.shooterD;
import static frc.robot.Constants.ShooterConstants.shooterF;
import static frc.robot.Constants.ShooterConstants.shooterI;
import static frc.robot.Constants.ShooterConstants.shooterP;
import static frc.robot.Constants.ShooterConstants.shooterRightMotorPort;
import static frc.robot.Constants.ShooterConstants.shooterLeftMotorPort;
import static frc.robot.Constants.ShooterConstants.turretMotorPort;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase{

  private static Shooter instance;

  private WPI_TalonSRX turretMotor;

  private CANSparkMax shooterRightMotor;
  private CANSparkMax shooterLeftMotor;

  private CANEncoder shooterEncoder;
  private CANPIDController shooterPID;



  private Shooter() {
    try{
      shooterRightMotor = new CANSparkMax(shooterRightMotorPort, MotorType.kBrushless);
      shooterLeftMotor = new CANSparkMax(shooterLeftMotorPort, MotorType.kBrushless);
      turretMotor = new WPI_TalonSRX(turretMotorPort);
    } catch (RuntimeException ex) {
      DriverStation
          .reportError("Error Instantiating Shooter Motor Controllers: " + ex.getMessage(), true);
    }
    shooterRightMotor.follow(shooterLeftMotor, true);
    shooterLeftMotor.setIdleMode(IdleMode.kCoast);
    shooterLeftMotor.setInverted(false);

    shooterRightMotor.setInverted(true);
    shooterRightMotor.setIdleMode(IdleMode.kCoast);

    TalonSRXConfiguration talonConfig = new TalonSRXConfiguration();
    talonConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;
    talonConfig.slot0.kP = kP;
    talonConfig.neutralDeadband = 0.0;
    talonConfig.slot0.kI = 0.0;
    talonConfig.slot0.kD = kD;
    talonConfig.slot0.integralZone = 400;
    talonConfig.slot0.closedLoopPeakOutput = 1.0;

    turretMotor.configAllSettings(talonConfig);
    shooterEncoder = shooterLeftMotor.getEncoder();
    shooterPID = shooterLeftMotor.getPIDController();
    shooterPID.setFeedbackDevice(shooterEncoder);

    updateShooterPID();
  }

  public static Shooter getInstance() {
    if (instance == null) {
      instance = new Shooter();
    }
    return instance;
  }

  private void updateShooterPID(){
    shooterPID.setOutputRange(0, 1);
    shooterPID.setP(shooterP);
    shooterPID.setI(shooterI);
    shooterPID.setD(shooterD);
    shooterPID.setFF(shooterF);
  }

  public void setFlywheelSpeed(double rpm){
    shooterPID.setReference(rpm, ControlType.kVelocity);
  }

  public void stopFlywheel(){
    shooterPID.setReference(0, ControlType.kVelocity);
    shooterRightMotor.set(0);
  }

  public int getTurretMotorPosition(){
    return turretMotor.getSelectedSensorPosition();
  }

  public void spinTurret(double speed){
    turretMotor.set(speed);
  }

}
