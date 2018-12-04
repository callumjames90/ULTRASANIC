import lejos.robotics.subsumption.Behavior;

public class CloseDist implements Behavior{
	
	boolean suppressed = false;
	
	@Override
	public boolean takeControl() {
		return UltraSanic.closeDist;
	}

	@Override
	public void action() {
		
		if (!suppressed) {
			Thread.yield();
		}
		
		if(!SanicPilot.pilot.isMoving()) {
			SanicPilot.pilot.travel(UltraSanic.closeRange);
		}

		UltraSanic.grab = true;
		UltraSanic.closeDist = false; 
	}

	@Override
	public void suppress() {
		suppressed = true;
		
	}

}