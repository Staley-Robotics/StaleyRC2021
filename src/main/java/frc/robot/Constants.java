/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed to reduce verbosity.
 */
public final class Constants {

  public static final class DriveConstants {

    public static final int rMotorMasterPort = 1;
    public static final int rMotorFollowerPort = 3;

    public static final int lMotorMasterPort = 2;
    public static final int lMotorFollowerPort = 4;

    public static final double rotateDeadzone = 0.01;
    public static final double speedModifier = 0.75;
    public static final double turnSpeedModifier = -0.85;

    public static final double kS = 0.702;
    public static final double kV = 5.3;
    public static final double kA = 0.341;
    public static final double kP = 0.00175;
    public static final double kD = 0;
    public static final double rSquared = 1.0;

    public static final SimpleMotorFeedforward feedForward = new SimpleMotorFeedforward(kS, kV, kA);

    public static final double maxVoltageAuto = 11;

    public static final int countPerRevolution = 4096;
    public static final double wheelDiameterMeters = 0.1524;
    public static final double wheelCircumferenceMeters = wheelDiameterMeters * Math.PI;

    // If Measured in meters trackWidth should be 0.58m
    public static final double trackWidth = 0.5792883856032899;
    public static final DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(
        trackWidth);

    public static final DifferentialDriveVoltageConstraint VOLTAGE_CONSTRAINT =
        new DifferentialDriveVoltageConstraint(feedForward, kinematics, maxVoltageAuto);

    public static final double maxVelocityMetersPerSecond = 1;
    public static final double maxAccelerationMetersPerSecondSquared = 1;

    // These are standard m/s. Values provided by wpilib docs
    public static final double ramseteB = 2;
    public static final double ramseteZ = 0.7;

    public static final double turnP = 0.03;
    public static final double turnI = 0.08;
    public static final double turnD = 0;

    public static final double turnToleranceDeg = 0.5;
    public static final double turnRateToleranceDegPerS = 5;

    public static final double shiftPointMetersPerSecond = 1.5;
  }

  public static final class RevolverConstants {
    public static final int revolverMotorPort = 6;
    public static final double revolverMotorSpeed = -0.3;
  }

  public static final class OperatorInputConstants {

    public static final int driveControllerPort = 1;
    public static final int altControllerPort = 0;
  }
}