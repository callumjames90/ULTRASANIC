import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.navigation.Navigator;

//X = R COS (THETA)
//Y = R SIN (THETA)

public class Main {
	
	private static DistanceSensor distanceThread;
	private static LocationSensor locationThread;
	private static SanicMusic soundThread;
	
	static Navigator nav = new Navigator(SanicPilot.pilot, LocationSensor.poseProvider);
	
	public static void main(String[] args) {		
		
		SanicPilot.pilot.setAngularSpeed(10);
		SanicPilot.pilot.setLinearSpeed(10);
		
		distanceThread = new DistanceSensor();
		locationThread = new LocationSensor();
		soundThread = new SanicMusic();
		
		distanceThread.start();
		locationThread.start();
		soundThread.start();
		
		boolean objectDetected = false;
		boolean scan = true;
		float distLeft = 0.0f, angLeft = 0.0f; 
		float distRight = 0.0f, angRight = 0.0f;
		
		float distMin = 0.0f;
		float center = 0.0f;
		boolean adjusted = false;
		
		int testint = 0;
		
		while(Button.readButtons() != Button.ID_ESCAPE) {
			if(scan) {
				//slowly rotate the robot
				if(!SanicPilot.pilot.isMoving()) {
					SanicPilot.pilot.arcForward(1);
				}
				
				//find and store the distance to the left side of the object
				if(DistanceSensor.sample[0] < 0.1) {
					distLeft = DistanceSensor.sample[0];
					distMin = distLeft;
					angLeft = LocationSensor.pose.getHeading();
					objectDetected = true;
				}
				
				//find and store the distance to the right side of the object
				if(objectDetected) {
					if(DistanceSensor.sample[0] < 0.5) { 
						distRight = DistanceSensor.sample[0];
						if(distMin > distRight) distMin = distRight;
					} else {
						angRight = LocationSensor.pose.getHeading();
						//average the two angles to get the center angle
						center = (angLeft + angRight) / 2;
						objectDetected = false;
						scan = false;
						SanicPilot.pilot.stop();
					}
				}
			} else {				
				LCD.drawString("dL: " + Float.toString(distLeft), 0, 0);
				LCD.drawString("aL: " + Float.toString(angLeft), 0, 1);
				LCD.drawString("dR: " + Float.toString(distRight), 0, 2);
				LCD.drawString("aR: " + Float.toString(angRight), 0, 3);
				LCD.drawString("min: " + Float.toString(distMin), 0, 4);
				
				//adjust robot to face the center of the object
				if(!adjusted) {
					if(!SanicPilot.pilot.isMoving()) {
						//float adjustment = center - LocationSensor.pose.getHeading();
						//SanicPilot.pilot.rotate(adjustment);
						SanicPilot.pilot.rotate(-10);
						adjusted = true;
					}				
				} else {
					LCD.drawString("center: " + Float.toString(center), 0, 5);
					LCD.drawString("pose: " + LocationSensor.pose.toString(), 0, 6);
					//move to object
					//grab object
				}
			}
		}
	}
}
