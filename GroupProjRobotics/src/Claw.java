import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.Port;

public class Claw {	
	private static boolean closed;
	private static NXTRegulatedMotor clawMotor;
	
	public Claw(Port clawPort) {
		closed = false;
		clawMotor = new NXTRegulatedMotor(clawPort);
		this.prime();
	}
	
	public static void close() {	
		clawMotor.rotate(-200);
		closed = true;
	}
	
	public static void open() {
		clawMotor.rotate(180);
		closed = false;
	}
	
	public static boolean isClosed() {
		return closed;
	}
	
	private void prime() {
		clawMotor.rotate(360);
	}
}

