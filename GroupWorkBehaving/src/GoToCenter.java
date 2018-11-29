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
		
		if(!SanicPilot.pilot.isMoving()) {
			SanicPilot.pilot.travel(UltraSanic.arenaEdge * -1);
		}
		UltraSanic.goToCenter = false;
		UltraSanic.minDist = Float.POSITIVE_INFINITY;
		SanicPilot.pilot.setAngularSpeed(UltraSanic.scanSpeed);
		System.exit(0);
	}

	@Override
	public void suppress() {
		suppressed = true;
		
	}

}
