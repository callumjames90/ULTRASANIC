import lejos.robotics.subsumption.Behavior;

public class FarScan implements Behavior{
	
	boolean suppressed;
	float minDist = Float.POSITIVE_INFINITY;
	
	@Override
	public boolean takeControl() {
		return UltraSanic.farScan;
	}

	@Override
	public void action() {
		if(!SanicPilot.pilot.isMoving()) {
			SanicPilot.pilot.arcForward(1);
		}
		if(DistanceSensor.sample[0] < UltraSanic.arenaRadius) {					
			minDist = DistanceSensor.sample[0];
			SanicPilot.pilot.stop();	
			if(minDist < UltraSanic.effectiveGrabRange) {
				minDist = Float.POSITIVE_INFINITY;
				UltraSanic.closeScan = true;
			} else {
				UltraSanic.destination = ((minDist * 1000) - UltraSanic.farScanOffset);
				UltraSanic.approachBall = true;			
			}
		}
		
	}

	@Override
	public void suppress() {
		suppressed = true;
		
	}

}
