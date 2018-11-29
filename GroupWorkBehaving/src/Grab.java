import lejos.robotics.subsumption.Behavior;

public class Grab implements Behavior{
	
	boolean suppressed;
	
	@Override
	public boolean takeControl() {
		return UltraSanic.grab;
	}

	@Override
	public void action() {
		if(!Claw.isClosed()) {
			Claw.close();
		}
		UltraSanic.grab = false;
		UltraSanic.goToBox = true;
		
	}

	@Override
	public void suppress() {
		suppressed = true;
		
	}

}
