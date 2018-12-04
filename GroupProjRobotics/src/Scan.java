
import lejos.robotics.subsumption.Behavior;

public class Scan implements Behavior{
	
	boolean suppressed = false;
	
	@Override
	public boolean takeControl() {
		return UltraSanic.scan;
	}

	@Override
	public void action() {
		
		if (!suppressed) {
			Thread.yield();
		}
		
		if(!SanicPilot.pilot.isMoving()) {
			SanicPilot.pilot.arcForward(1);
		}
		
		if(DistanceSensor.sample[0] < UltraSanic.arenaRadius) {
			SanicPilot.pilot.stop();
			UltraSanic.approachBall = true;
			UltraSanic.scan = false;
		}
		
	}

	@Override
	public void suppress() {
		suppressed = true;
		
	}

}
