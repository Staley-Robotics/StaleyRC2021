/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import static frc.robot.Constants.OperatorInputConstants.altControllerPort;
import static frc.robot.Constants.OperatorInputConstants.driveControllerPort;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.shooter.ResetTurret;
import frc.robot.commands.shooter.RotateTurret;
import frc.robot.commands.shooter.ShootBallsSimple;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;

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
  private final Vision vision;
  private final Shooter shooter;

  private SendableChooser<Command> autoChooser;
  private SendableChooser<Command> driveChooser;


  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    drive = DriveTrain.getInstance();
    vision = Vision.getInstance();
    shooter = Shooter.getInstance();

    autoChooser = new SendableChooser<>();
    driveChooser = new SendableChooser<>();

    Command tankCommand = new RunCommand(
        () ->
            drive.tankerMan(
                -driveController.getY(Hand.kLeft),
                -driveController.getY(Hand.kRight)
            ), drive
    );

    Command stickDrive = new RunCommand(
        () ->
            drive.worldOfStick(
                driveController.getY(Hand.kLeft),
                driveController.getX(Hand.kLeft)),
        drive);

    Command joyStickDrive = new RunCommand(
        () ->
            drive.worldOfStick(
                driveStick.getY(Hand.kRight),
                driveStick.getX(Hand.kRight)
            ), drive);

    Command worldOfTanks = new RunCommand(
        () ->
            drive.worldOfTanksDrive(
                driveController.getTriggerAxis(GenericHID.Hand.kRight),
                driveController.getTriggerAxis(GenericHID.Hand.kLeft),
                driveController.getX(GenericHID.Hand.kLeft)),
        drive);

    driveChooser.addOption("Tank Drive", tankCommand);
    driveChooser.addOption("Arcade Drive", stickDrive);
    driveChooser.addOption("Joystick Drive", joyStickDrive);
    driveChooser.addOption("World of Tanks", worldOfTanks);


    SmartDashboard.putData("Auto", autoChooser);
    //All subsystems will have checks that should be checked before going out.
    //check 1: default commands have been set/not set correctly
    //drive check1

    SmartDashboard.putData("Drive", driveChooser);
    driveChooser.setDefaultOption("Joystick Drive",joyStickDrive);
    drive.setDefaultCommand(driveChooser.getSelected());

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

    //Alternate Controller Buttons
    JoystickButton simpleShoot = new JoystickButton(altController, Button.kA.value);
    simpleShoot.whileHeld(new ShootBallsSimple());

    JoystickButton rotateTurret = new JoystickButton(altController, Button.kB.value);
    rotateTurret.whenPressed(new RotateTurret());

    JoystickButton resetTurret = new JoystickButton(altController, Button.kY.value);
    resetTurret.whenPressed(new ResetTurret());



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