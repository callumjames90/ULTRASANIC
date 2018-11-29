import lejos.robotics.subsumption.Behavior;

public class CloseScan implements Behavior{
	static final float scanSpeed = 5.0f;
	boolean suppressed;
	
	@Override
	public boolean takeControl() {
		return UltraSanic.closeScan;
	}

	@Override
	public void action() {
		
		if(!SanicPilot.pilot.isMoving()) {
			SanicPilot.pilot.arcForward(1);
		}
		if(DistanceSensor.sample[0] < (UltraSanic.arenaRadius - (UltraSanic.destination / 1000))) {					
			if(UltraSanic.minDist > DistanceSensor.sample[0]) UltraSanic.minDist = DistanceSensor.sample[0];
			if(DistanceSensor.sample[0] > UltraSanic.minDist) {
				SanicPilot.pilot.stop();
				SanicPilot.pilot.setAngularSpeed(scanSpeed);
				UltraSanic.closeScan = false;
				UltraSanic.grab = true;
			}
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}