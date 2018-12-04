import lejos.robotics.subsumption.Behavior;

public class Drop implements Behavior {
	boolean suppressed = false;

	@Override
	public boolean takeControl() {
		return UltraSanic.drop;
	}

	@Override
	public void action() {
		if (!suppressed) {
			Thread.yield();
		}
		
		if (Claw.isClosed()) {
			Claw.open();
		}
		UltraSanic.drop = false;
		UltraSanic.goToCenter = true;
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
