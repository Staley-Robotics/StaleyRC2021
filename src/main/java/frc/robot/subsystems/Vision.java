package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Handles everything vision related on the robot including networktable communications.
 */
public class Vision extends SubsystemBase {

  private static Vision instance;

  private final NetworkTable table;

  private Vision() {
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    table = inst.getTable("chameleon-vision/Microsoft LifeCam HD-3000");
  }

  public static Vision getInstance() {
    if (instance == null) {
      instance = new Vision();
    }
    return instance;
  }

  @Override
  public void periodic() {
  }

  /**
   * If trackTape is true, vision track. If trackTape is false, the camera stream will not be
   * processed.
   *
   * @param trackTape track tape or not
   */
  public void setTapeProcessing(final boolean trackTape) {
    NetworkTableEntry targetProcessing = table.getEntry("driverMode");
    targetProcessing.setBoolean(trackTape);
  }

  /**
   * Check if processing sees a target.
   *
   * @return Target detected or not
   */
  public boolean tapeDetected() {
    NetworkTableEntry targetDetected = table.getEntry("isValid");
    return targetDetected.getBoolean(false);

  }


  /**
   * Gets and returns pitch (up/down angle of displacement) from chameleon vision network table.
   *
   * @return Calculated pitch
   */
  public double getPitch() {
    NetworkTableEntry targetPitch = table.getEntry("targetPitch");
    return targetPitch.getDouble(0.0);
  }

  /**
   * Gets and returns yaw (left/right angle of displacement) from chameleon vision network table.
   *
   * @return Calculated yaw
   */
  public double getYaw() {
    NetworkTableEntry targetYaw = table.getEntry("targetYaw");
    return targetYaw.getDouble(0.0);
  }
}
