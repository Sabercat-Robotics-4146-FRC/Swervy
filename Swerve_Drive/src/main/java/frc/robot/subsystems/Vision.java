package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;

public class Vision implements Subsystem{
  private final DrivetrainSubsystem drive;
  NetworkTableInstance inst = NetworkTableInstance.getDefault();
  NetworkTable table = inst.getTable("limelight");
  NetworkTableEntry xEntry, yEntry, aEntry, lEntry, vEntry, sEntry;
  NetworkTableEntry tshortEntry,
      tlongEntry,
      thorEntry,
      tvertEntry,
      getpipeEntry,
      camtranEntry,
      ledModeEntry;
  // You will need to add/subtract the angle of the limelight to the angle it returns. I didnt add
  // this as I do not know what angle the limelight will be mounted at.
  public Vision(DrivetrainSubsystem drivetrainSubsystem) {
    this.drive = drivetrainSubsystem;
    inst.startClientTeam(4146);
    inst.startDSClient();
    xEntry = table.getEntry("tx");
    yEntry = table.getEntry("ty");
    aEntry = table.getEntry("ta");
    lEntry = table.getEntry("tl");
    vEntry = table.getEntry("tv");
    sEntry = table.getEntry("ts");

    tshortEntry = table.getEntry("tshort");
    tlongEntry = table.getEntry("tlong");
    thorEntry = table.getEntry("thor");
    tvertEntry = table.getEntry("tvert");
    getpipeEntry = table.getEntry("getpipe");
    camtranEntry = table.getEntry("camtran");
    ledModeEntry = table.getEntry("ledMode");

    double tx =
        xEntry.getDouble(
            0.0); // Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees)
    double ty =
        yEntry.getDouble(
            0.0); // Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
    double ta = aEntry.getDouble(0.0); // Target Area (0% of image to 100% of image)
    double tl =
        lEntry.getDouble(
            0.0); // The pipeline’s latency contribution (ms) Add at least 11ms for image capture
    // latency.
    double tv = vEntry.getDouble(0.0); // Whether the limelight has any valid targets (0 or 1)
    double ts = sEntry.getDouble(0.0);
  }

  public double fetchDistance() {
    double theta = yEntry.getDouble(0.0);
    double distance = (Constants.yt / Math.tan(theta * Math.PI / 180));
    return distance;
  }

  public double computeAngFlywheel() {
    double d = fetchDistance()+1;
    double v = Constants.speedObject;
    double h = Constants.zt - Constants.zr;
    double g = -9.81;
    double part = Math.sqrt(v * v * v * v + g * (2 * h * v * v - g * d * d));
    double ang = Math.atan((v * v + part) / (-g * d));
    // Uncertainty of the Object's Velocity
    //double unV = 0;
    // Uncertainty of the angle between height and distance(needs to be obtained through some
    // measurements on limelight
    //double unT = 0;
    // This is the uncertainty of the angle the flywheel should shoot at. This could be used to
    // adjust the angle if necessary
    // double uncertainty =
    //  (d / (v * Math.cos(ang)))
    //    * Math.sqrt(Math.pow(Math.cos(ang) * unV, 2) + Math.pow(v * Math.sin(ang) * unT, 2));
    return ang;
    // TODO Add some if statements to check to see if this angle is within the angles the flywheel
    // could shoot at and return 0.0 if it isnt
    // TODO Also incorporate uncertainty
  }
  public void setLaunchAngle() {
    double ideal = computeAngFlywheel();
    //Needs to be finished with code to control the servo
  }
  public void turnRobotToHoop() {
    double anghoop = xEntry.getDouble(0.0);
    while (Math.abs(anghoop) > 2) {
      if(anghoop < 0) {
        //Needs to turn the robot such that it faces the hoop
      }
    }
  }
  public void periodic() {
    SmartDashboard.putNumber("Angle of flywheel", computeAngFlywheel());
  }
}
