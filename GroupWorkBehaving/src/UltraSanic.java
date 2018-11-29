import lejos.robotics.navigation.Navigator;
import lejos.robotics.pathfinding.Path;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class UltraSanic {

	//threads
	private static DistanceSensor distanceThread;
	private static LocationSensor locationThread;
	static Navigator nav = new Navigator(SanicPilot.pilot, LocationSensor.poseProvider);
	
	//different rotation speeds
	static final float scanSpeed = 5.0f;
	static final float turnSpeed = 50.0f;
	
	//states
	static boolean farScan = true;
	static boolean approachBall = false;
	static boolean closeScan = false;
	static boolean grab = false;
	static boolean goToBox = false;
	static boolean drop = false;
	static boolean goToCenter = false;
	
	static boolean pathInit = false;
	
	//data constants regarding arena size
	static final float arenaRadius = 0.65f;
	static final  float arenaEdge = 630.0f;
	
	//data constants regarding claw range
	static final  float effectiveGrabRange = 0.05f;
	static float farScanOffset = 100.0f;
	static float closeScanOffset = 0.0f;
	
	//variables holding returned scan distances and intended traversal distances
	static float minDist = Float.POSITIVE_INFINITY;
	static float destination = 0.0f;
	
	//path for holding necessary waypoint data
	static Path path = new Path();
	
	public static void main(String[] args) {

		//set initial pilot speeds
		SanicPilot.pilot.setAngularSpeed(scanSpeed);
		SanicPilot.pilot.setLinearSpeed(50);
		
		// Creates and starts a thread that collects distance data
		distanceThread = new DistanceSensor();
		locationThread = new LocationSensor();
		
		distanceThread.start();
		locationThread.start();

		Behavior[] behaviors = new Behavior[5];
		behaviors[0] = null; // farScan
		behaviors[1] = null; // approachBall
		behaviors[2] = null; // closeScan
		behaviors[3] = null; // grab
		behaviors[4] = null; // goToBox
		behaviors[5] = null; // drop
		behaviors[6] = null; // goToCenter

		// Create arbitrator
		Arbitrator arbi = new Arbitrator(behaviors);
		
		// Starts the program
		arbi.go();
	}
}