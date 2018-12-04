import java.io.File;

import lejos.hardware.Sound;
import lejos.robotics.subsumption.Behavior;

public class Grab implements Behavior{
	
	boolean suppressed = false;
	
	@Override
	public boolean takeControl() {
		return UltraSanic.grab;
	}

	@Override
	public void action() {
		
		if (!suppressed) {
			Thread.yield();
		}
		
		
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
