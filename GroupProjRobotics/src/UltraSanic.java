import lejos.hardware.port.MotorPort;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;
import lejos.robotics.pathfinding.Path;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class UltraSanic {

	static PoseProvider poseProvider = new OdometryPoseProvider(SanicPilot.pilot);
	static Pose pose = poseProvider.getPose();
	
	static Navigator nav = new Navigator(SanicPilot.pilot, poseProvider);
	private static Claw claw = new Claw(MotorPort.D);
	
	//threads
	private static DistanceSensor distanceThread;
	static TunePlayer SoundThread;
	
	//different rotation speeds
	static final float scanSpeed = 5.0f;
	static final float turnSpeed = 50.0f;
	
	
	//states	
	static boolean scan = true;	
	static boolean approachBall = false;
	static boolean closeDist = false;
	static boolean grab = false;
	static boolean goToBox = false;
	static boolean drop = false;
	static boolean goToCenter = false;
	
	static boolean pathInit = false;
	
	//data constants regarding arena size
	static float arenaEdge = 500.0f;
	static float arenaRadius = 0.65f;
	
	//data constant regarding claw grab range
	static float grabRange = 0.1f;
	static float closeRange = 80.0f;
	
	//path for holding necessary waypoint data
	static Path path = new Path();
	
	public static void main(String[] args) {

		//set initial pilot speeds
		SanicPilot.pilot.setAngularSpeed(scanSpeed);
		SanicPilot.pilot.setLinearSpeed(50);
		
		// Creates and starts a thread that collects distance data
		distanceThread = new DistanceSensor();
		SoundThread = new TunePlayer();
		
		distanceThread.start();
		SoundThread.start();
		
		Behavior[] behaviors = new Behavior[7];
		behaviors[0] = new Scan(); // farScan
		behaviors[1] = new ApproachBall(); // approachBall
		behaviors[2] = new CloseDist(); // closeScan
		behaviors[3] = new Grab(); // grab
		behaviors[4] = new GoToBox(); // goToBox
		behaviors[5] = new Drop(); // drop
		behaviors[6] = new GoToCenter(); // goToCenter

		// Create arbitrator
		Arbitrator arbi = new Arbitrator(behaviors);
		
		// Starts the program
		arbi.go();
	}
}