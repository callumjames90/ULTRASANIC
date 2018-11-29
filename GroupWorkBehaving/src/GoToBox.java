import lejos.robotics.navigation.Waypoint;
import lejos.robotics.subsumption.Behavior;

public class GoToBox implements Behavior{
	
	boolean suppressed;
	
	@Override
	public boolean takeControl() {
		return UltraSanic.goToBox;
	}

	@Override
	public void action() {
		if(!UltraSanic.pathInit) {
			SanicPilot.pilot.setAngularSpeed(UltraSanic.turnSpeed);
			UltraSanic.path.add(new Waypoint(LocationSensor.pose.getX(), 0, 0));
			UltraSanic.path.add(new Waypoint(UltraSanic.arenaEdge, 0, 0));
			UltraSanic.pathInit = true;
			UltraSanic.nav.followPath(UltraSanic.path);
		}
		if(UltraSanic.nav.pathCompleted()) {
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
