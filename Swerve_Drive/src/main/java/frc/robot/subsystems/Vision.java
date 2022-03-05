package frc.robot.subsystems;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants;
public class Vision {
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("limelight");
    NetworkTableEntry xEntry, yEntry, aEntry, lEntry, vEntry, sEntry; 
    NetworkTableEntry tshortEntry, tlongEntry, thorEntry, tvertEntry,getpipeEntry,camtranEntry,ledModeEntry;

    public Vision() {
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

        double tx = xEntry.getDouble(0.0); // Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees)
        double ty = yEntry.getDouble(0.0); // Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
        double ta = aEntry.getDouble(0.0); // Target Area (0% of image to 100% of image)
        double tl = lEntry.getDouble(0.0); // The pipeline’s latency contribution (ms) Add at least 11ms for image capture
                                        // latency.
        double tv = vEntry.getDouble(0.0); // Whether the limelight has any valid targets (0 or 1)
        double ts = sEntry.getDouble(0.0);

    }

    public double fetchDistance() {
        double theta = yEntry.getDouble(0.0);
        double distance = (Constants.yt / Math.tan(theta*Math.PI/180));
        return distance;
    }
    public double getIdealDistance() {
        cDistance = fetchDistance()
        double height = Constants.zt - Constants.zr;
        2
    }   
    public double computeVelocity() {
        
        
        double height = Constants.zt - Constants.zr;
        double distance = fetchDistance();
        double ang = yEntry.getDouble(0.0);
        double v = (distance/math.cos(Constants.launchAngle)
        dff

        return v;

        //rArr of the form {xr, yr, zr, degr}
        /*
        double degIdeal = Math.PI/2 - Math.atan((Constants.yt-rArr[1])/(Constants.xt-rArr[0]));
        double[] arrRobot = new double[4];
        arrRobot[3] = degIdeal;
        arrRobot[2] = rArr[2];
        arrRobot[0] = rArr[0];
        double z = Constants.zt - rArr[2];
        double yC = Constants.yt - rArr[1];
        double theta = 0;
        
        //Now, we can compute velocity, assuming the flywheel is facing the hoop   */     
        
    }

    public void periodic() {
   
    }
}
