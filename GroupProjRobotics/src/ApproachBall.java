import lejos.robotics.subsumption.Behavior;

public class ApproachBall implements Behavior{
	
	boolean suppressed = false;
	
	@Override
	public boolean takeControl() {
		return UltraSanic.approachBall;
	}

	@Override
	public void action() {
		
		if (!suppressed) {
			Thread.yield();
		}
		
		if(!SanicPilot.pilot.isMoving()) {
			SanicPilot.pilot.forward();
		}
		
		if(DistanceSensor.sample[0] <= UltraSanic.grabRange) {
			SanicPilot.pilot.stop();
			UltraSanic.closeDist = true;
			UltraSanic.approachBall = false;
		}
		
		if(DistanceSensor.sample[0] > UltraSanic.arenaRadius) {
			SanicPilot.pilot.stop();
			UltraSanic.scan = true;
			UltraSanic.approachBall = false;
		}
		
	}

	@Override
	public void suppress() {
		suppressed = true;
		
	}

}
