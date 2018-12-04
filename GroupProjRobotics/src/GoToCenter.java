import lejos.robotics.navigation.Pose;
import lejos.robotics.subsumption.Behavior;

public class GoToCenter implements Behavior{
	
	boolean suppressed = false;
	
	@Override
	public boolean takeControl() {
		return UltraSanic.goToCenter;
	}

	@Override
	public void action() {
		
		if (!suppressed) {
			Thread.yield();
		}
		
		
		SanicPilot.pilot.travel(-UltraSanic.arenaEdge, false);
		
		UltraSanic.pathInit = false;
		SanicPilot.pilot.setAngularSpeed(UltraSanic.scanSpeed);
		UltraSanic.poseProvider.setPose(new Pose(0.0f, 0.0f, 0.0f));
		UltraSanic.pose = UltraSanic.poseProvider.getPose();
		UltraSanic.scan = true;
		UltraSanic.goToCenter = false;
	
	}

	@Override
	public void suppress() {
		suppressed = true;
		
	}

}
