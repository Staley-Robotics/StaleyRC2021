/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import static frc.robot.Constants.IntakeConstants.defaultIntakePower;
import static frc.robot.Constants.MagazineConstants.defaultMagazinePower;
import static frc.robot.Constants.OperatorInputConstants.altControllerPort;
import static frc.robot.Constants.OperatorInputConstants.driveControllerPort;
import static frc.robot.Constants.ShooterConstants.autoLineShootSpeed;
import static frc.robot.Constants.ShooterConstants.trenchShootSpeed;
import static frc.robot.Constants.WinchConstants.winchDefaultMotorPower;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.vision.VisionYawAlign;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Pneumatics;
import frc.robot.subsystems.Vision;
import frc.robot.util.DPadButton;
import frc.robot.util.DPadButton.Direction;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  private XboxController driveController;
  private XboxController altController;
  private Joystick driveStick;

  private final DriveTrain drive;
  private final Pneumatics pneumatics;
  private final Vision vision;

  private SendableChooser<Command> autoChooser;


  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    drive = DriveTrain.getInstance();
    pneumatics = Pneumatics.getInstance();
    vision = Vision.getInstance();

    autoChooser = new SendableChooser<>();

    SmartDashboard.putData("Auto", autoChooser);
    //All subsystems will have checks that should be checked before going out.
    //check 1: default commands have been set/not set correctly
    //drive check1

    /*
    drive.setDefaultCommand(
        new RunCommand(
            () ->
                drive.worldOfTanksDrive(
                    driveController.getTriggerAxis(GenericHID.Hand.kRight),
                    driveController.getTriggerAxis(GenericHID.Hand.kLeft),
                    driveController.getX(GenericHID.Hand.kLeft)),
            drive));
    */


    //intake check1
    //magazine check1
    //Mast check1
    //pneumatics check1
    //shooter check1
    //vision check1
    //winch check1

    /*
    drive.setDefaultCommand(
        new RunCommand(
            () ->
                drive.worldOfStick(
                    driveController.getY(Hand.kLeft),
                    driveController.getX(Hand.kLeft)),
            drive));
    */


    drive.setDefaultCommand(
        new RunCommand(
            () ->
                drive.worldOfStick(
                    driveStick.getY(Hand.kRight),
                    driveStick.getX(Hand.kRight)
                ), drive));


    /*
    drive.setDefaultCommand(
        new RunCommand(
            () ->
                drive.tankerMan(
                    -driveController.getY(Hand.kLeft),
                    -driveController.getY(Hand.kRight)
                ), drive
        )
    );
    */
    configureButtonBindings();
  }


  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    driveController = new XboxController(driveControllerPort);
    altController = new XboxController(altControllerPort);
    driveStick = new Joystick(2);

    /* Drive Controller */
    JoystickButton shiftButton = new JoystickButton(driveController, Button.kB.value);
    shiftButton.whenPressed(drive::toggleShift, drive);

    JoystickButton lineUpShot = new JoystickButton(driveController, Button.kX.value);
    lineUpShot.whileHeld(new VisionYawAlign());

    /* Alt Controller */

    JoystickButton toggleCompressor = new JoystickButton(altController, Button.kY.value);
    toggleCompressor.whenPressed(pneumatics::compressorToggle, pneumatics);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous.
   */
  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }
}