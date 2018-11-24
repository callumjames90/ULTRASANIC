import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.Port;

public class Claw {	
	private boolean closed;
	private NXTRegulatedMotor clawMotor;
	
	public Claw(Port clawPort) {
		closed = false;
		clawMotor = new NXTRegulatedMotor(clawPort);
		clawMotor.setSpeed(clawMotor.getMaxSpeed());
		this.prime();
	}
	
	public void close() {	
		clawMotor.rotate(-190);
		closed = true;
	}
	
	public void open() {
		clawMotor.rotate(190);
		closed = false;
	}
	
	public boolean isClosed() {
		return closed;
	}
	
	private void prime() {
		while(!clawMotor.isStalled()) {
			clawMotor.forward();
		}
	}
}


