import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.Port;

public class Claw {	
	private boolean closed;
	private NXTRegulatedMotor clawMotor;
	
	public Claw(Port clawPort) {
		closed = false;
		clawMotor = new NXTRegulatedMotor(clawPort);
		this.prime();
	}
	
	public void close() {	
		clawMotor.rotate(-200);
		closed = true;
	}
	
	public void open() {
		clawMotor.rotate(180);
		closed = false;
	}
	
	public boolean isClosed() {
		return closed;
	}
	
	private void prime() {
		clawMotor.rotate(360);
	}
}


