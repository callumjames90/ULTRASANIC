import lejos.robotics.subsumption.Behavior;

public class ApproachBall implements Behavior{
	
	boolean suppressed;
	
	@Override
	public boolean takeControl() {
		return UltraSanic.approachBall;
	}

	@Override
	public void action() {
		if(!SanicPilot.pilot.isMoving()) {
			SanicPilot.pilot.travel(UltraSanic.destination);
		}
		UltraSanic.approachBall = false;
		UltraSanic.minDist = Float.POSITIVE_INFINITY;
		UltraSanic.closeScan = true;
		
	}

	@Override
	public void suppress() {
		suppressed = true;
		
	}

}
