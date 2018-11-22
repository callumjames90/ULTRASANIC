import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.Pose;

public class LocationSensor extends Thread {
	
	static PoseProvider poseProvider = new OdometryPoseProvider(SanicPilot.pilot);
	static Pose pose;

	public LocationSensor() {}
	
	public void run() {
		while (true) {
			pose = poseProvider.getPose();
		}
	}
}
