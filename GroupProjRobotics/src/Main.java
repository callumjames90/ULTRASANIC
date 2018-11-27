import lejos.hardware.Button;
import lejos.hardware.port.MotorPort;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;

//X = R COS (THETA)
//Y = R SIN (THETA)

//PROVEN EFFECTIVE RANGE: 0.36f (0.45f with the ball)
//EFFECTIVE PICKUP RANGE: approx 0.05f (5cm)
//x = 216.0f

public class Main {
	
	private static DistanceSensor distanceThread;
	private static LocationSensor locationThread;
	private static Claw claw = new Claw(MotorPort.D);
	static Navigator nav = new Navigator(SanicPilot.pilot, LocationSensor.poseProvider);
	
	static final float scanSpeed = 5.0f;
	static final float turnSpeed = 50.0f;
	
	
	public static void main(String[] args) {		
		
		SanicPilot.pilot.setAngularSpeed(scanSpeed);
		SanicPilot.pilot.setLinearSpeed(50);
		
		distanceThread = new DistanceSensor();
		locationThread = new LocationSensor();
		
		distanceThread.start();
		locationThread.start();
		

		boolean farScan = true;
		boolean approachBall = false;
		boolean closeScan = false;
		boolean grab = false;
		boolean goToBox = false;
		boolean drop = false;
		boolean goToCenter = false;
		
		boolean pathInit = false;
		
		float arenaRadius = 0.65f;
		float effectiveGrabRange = 0.05f;
		float destination = 0.0f;
		float arenaEdge = 700.0f;
		
		//float threshhold = 0.003f;
		
		float farScanOffset = 100.0f;
		float closeScanOffset = 0.0f;
		
		float minDist = Float.POSITIVE_INFINITY;
		
		Path path = new Path();
		
		
		while(Button.readButtons() != Button.ID_ESCAPE) {
			
			if(farScan) {		
				if(!SanicPilot.pilot.isMoving()) {
					SanicPilot.pilot.arcForward(1);
				}
				if(DistanceSensor.sample[0] < arenaRadius) {					
					minDist = DistanceSensor.sample[0];
					SanicPilot.pilot.stop();	
					farScan = false;
					if(minDist < effectiveGrabRange) {
						minDist = Float.POSITIVE_INFINITY;
						closeScan = true;
					} else {
						destination = ((minDist * 1000) - farScanOffset);
						approachBall = true;			
					}
				}
			}
			
			if(approachBall) {
				if(!SanicPilot.pilot.isMoving()) {
					SanicPilot.pilot.travel(destination);
				}
				approachBall = false;
				minDist = Float.POSITIVE_INFINITY;
				closeScan = true;	
			}	
			
			if(closeScan) {
				if(!SanicPilot.pilot.isMoving()) {
					SanicPilot.pilot.arcForward(1);
				}
				if(DistanceSensor.sample[0] < arenaRadius) {					
					//minDist = DistanceSensor.sample[0];
					if(minDist > DistanceSensor.sample[0]) minDist = DistanceSensor.sample[0];
					if(DistanceSensor.sample[0] > minDist) {
						SanicPilot.pilot.stop();
						SanicPilot.pilot.setAngularSpeed(scanSpeed);
						closeScan = false;
						grab = true;
					}
				}
			}
			
			if(grab) {
				if(!Claw.isClosed()) {
					Claw.close();
				}
				grab = false;
				goToBox = true;
			}
			
			if(goToBox) {
				if(!pathInit) {
					SanicPilot.pilot.setAngularSpeed(turnSpeed);
					path.add(new Waypoint(LocationSensor.pose.getX(), 0, 0));
					path.add(new Waypoint(arenaEdge, 0, 0));
					pathInit = true;
					nav.followPath(path);
				}
				if(nav.pathCompleted()) {
					nav.clearPath();
					goToBox = false;
					pathInit = false;
					drop = true;
				}
			}
			
			if(drop) {
				if(Claw.isClosed()) {
					Claw.open();
				}
				destination = -arenaEdge;
				drop = false;
			}
			
			if(goToCenter) {
				if(!SanicPilot.pilot.isMoving()) {
					SanicPilot.pilot.travel(destination);
				}
				//do second scan or some shit
				goToCenter = false;
			}			
		}			
	}
}
