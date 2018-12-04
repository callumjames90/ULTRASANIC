import lejos.robotics.navigation.Waypoint;
import lejos.robotics.subsumption.Behavior;

public class GoToBox implements Behavior{
	
	boolean suppressed = false;
	
	@Override
	public boolean takeControl() {
		return UltraSanic.goToBox;
	}

	@Override
	public void action() {
		
		if (!suppressed) {
			Thread.yield();
		}
		
		if(!UltraSanic.pathInit) {
			
			UltraSanic.pose = UltraSanic.poseProvider.getPose();
			SanicPilot.pilot.setAngularSpeed(UltraSanic.turnSpeed);
			UltraSanic.path.add(new Waypoint(UltraSanic.pose.getX(), 0, 0));
			UltraSanic.path.add(new Waypoint(UltraSanic.arenaEdge, 0, 0));
			UltraSanic.pathInit = true;
			UltraSanic.nav.followPath(UltraSanic.path);
		}
		if(UltraSanic.nav.pathCompleted()) {
			SanicPilot.pilot.stop();
			UltraSanic.nav.clearPath();
			UltraSanic.goToBox = false;
			UltraSanic.pathInit = false;
			UltraSanic.drop = true;
		}
		
	}

	@Override
	public void suppress() {
		suppressed = true;
		
	}

}
