/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import static frc.robot.Constants.DriveConstants.countPerRevolution;
import static frc.robot.Constants.DriveConstants.feedForward;
import static frc.robot.Constants.DriveConstants.kD;
import static frc.robot.Constants.DriveConstants.kP;
import static frc.robot.Constants.DriveConstants.kinematics;
import static frc.robot.Constants.DriveConstants.lMotorFollowerPort;
import static frc.robot.Constants.DriveConstants.lMotorMasterPort;
import static frc.robot.Constants.DriveConstants.maxAccelerationMetersPerSecondSquared;
import static frc.robot.Constants.DriveConstants.maxVelocityMetersPerSecond;
import static frc.robot.Constants.DriveConstants.rMotorFollowerPort;
import static frc.robot.Constants.DriveConstants.rMotorMasterPort;
import static frc.robot.Constants.DriveConstants.ramseteB;
import static frc.robot.Constants.DriveConstants.ramseteZ;
import static frc.robot.Constants.DriveConstants.rotateDeadzone;
import static frc.robot.Constants.DriveConstants.speedModifier;
import static frc.robot.Constants.DriveConstants.turnSpeedModifier;
import static frc.robot.Constants.DriveConstants.wheelCircumferenceMeters;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

@SuppressWarnings("FieldCanBeLocal")
public class DriveTrain extends SubsystemBase {

  private static DriveTrain instance;

  private WPI_TalonFX rightMaster;
  private WPI_TalonFX rightFollower;


  private WPI_TalonFX leftMaster;
  private WPI_TalonFX leftFollower;

  private DifferentialDrive drive;

  private DriveTrain() {
    try {
      rightMaster = new WPI_TalonFX(rMotorMasterPort);
      rightFollower = new WPI_TalonFX(rMotorFollowerPort);

      leftMaster = new WPI_TalonFX(lMotorMasterPort);
      leftFollower = new WPI_TalonFX(lMotorFollowerPort);

    } catch (RuntimeException ex) {
      DriverStation
          .reportError("Error Instantiating drive motor controllers: " + ex.getMessage(), true);
    }

    TalonFXConfiguration talonConfig = new TalonFXConfiguration();
    talonConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;
    talonConfig.slot0.kP = kP;
    talonConfig.neutralDeadband = 0.0;
    talonConfig.slot0.kI = 0.0;
    talonConfig.slot0.kD = kD;
    talonConfig.slot0.integralZone = 400;
    talonConfig.slot0.closedLoopPeakOutput = 1.0;

    rightMaster.configAllSettings(talonConfig);
    rightFollower.configAllSettings(talonConfig);
    leftMaster.configAllSettings(talonConfig);
    leftFollower.configAllSettings(talonConfig);

    rightMaster.setSensorPhase(false);
    leftMaster.setSensorPhase(false);

    rightMaster.setInverted(true);
    rightFollower.setInverted(true);

    leftMaster.setInverted(false);
    leftFollower.setInverted(false);

    rightFollower.follow(rightMaster);

    leftFollower.follow(leftMaster);

    drive = new DifferentialDrive(leftMaster, rightMaster);
    drive.setSafetyEnabled(false);
    drive.setRightSideInverted(false);

    zeroEncoder();
  }

  public static DriveTrain getInstance() {
    if (instance == null) {
      instance = new DriveTrain();
    }
    return instance;
  }

  /* Drive Code */

  /**
   * Used in teleop as right trigger = forward power, left trigger = backward power, left stick
   * x-axis = rotate power.
   */
  public void worldOfTanksDrive(double forward, double backward, double rotate) {

    backward = backward * speedModifier;
    forward = forward * speedModifier;

    // Logic for deadzones
    // rotate > rotateDeadZone || rotate < rotateDeadZone
    if (Math.abs(rotate) > rotateDeadzone) {
      rotate = -rotate * turnSpeedModifier;
    } else {
      rotate = 0;
    }

    // Actual drive logic to move bot.
    if (backward > 0) {
      drive.arcadeDrive(-backward, rotate);
    } else if (forward > 0) {
      drive.arcadeDrive(forward, rotate);
    } else {
      drive.arcadeDrive(0, rotate);
    }
  }

  public void worldOfStick(double yAxis, double rotate) {
    yAxis = yAxis * speedModifier;

    //Deadzone copy
    if (Math.abs(rotate) > rotateDeadzone) {
      rotate = -rotate * turnSpeedModifier;
    } else {
      rotate = 0;
    }

    if (yAxis < 0) {
      drive.arcadeDrive(-yAxis,rotate);
    } else if (yAxis > 0){
      drive.arcadeDrive(-yAxis,rotate);
    } else {
      drive.arcadeDrive(0,rotate);
    }
   }

  public void tankerMan (double leftSide, double rightSide) {
    leftSide = leftSide * speedModifier;
    rightSide = rightSide * speedModifier;

    drive.tankDrive(leftSide,rightSide);
   }

  public void stopDrive() {
    drive.arcadeDrive(0, 0);
  }

  /* Encoder */

  public int getLeftEncoderPosition() {
    return leftMaster.getSelectedSensorPosition(0);
  }

  public int getRightEncoderPosition() {
    return rightMaster.getSelectedSensorPosition(0);
  }

  public int getLeftEncoderVelocity() {
    return leftMaster.getSelectedSensorVelocity();

  }

  public int getRightEncoderVelocity() {
    return rightMaster.getSelectedSensorVelocity();
  }

  /**
   * Zeros drive encoders.
   */
  public void zeroEncoder() {
    rightMaster.setSelectedSensorPosition(0);
    leftMaster.setSelectedSensorPosition(0);
    System.out.println("Encoders have been zeroed");
  }




  public void runDriveTrain(double power) {
    rightMaster.set(power);
    leftMaster.set(power);
  }
}