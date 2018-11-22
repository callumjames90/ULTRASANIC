import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.MovePilot;

public class SanicPilot {
	
	/*BOILER PLATE CODE TO BUILD THE PILOT & NAVIGATOR*/
	static RegulatedMotor mL = new EV3LargeRegulatedMotor(MotorPort.A);
	static RegulatedMotor mR = new EV3LargeRegulatedMotor(MotorPort.B);
	
	static final float WHEEL_DIAMETER = 56; // mm
	static final float AXLE_LENGTH = 120; // mm
	
	static Wheel wL = WheeledChassis.modelWheel(mL, WHEEL_DIAMETER).offset(-AXLE_LENGTH / 2);
	static Wheel wR = WheeledChassis.modelWheel(mR, WHEEL_DIAMETER).offset(AXLE_LENGTH / 2);
	
	static Chassis chassis = new WheeledChassis(new Wheel[] {wR, wL}, WheeledChassis.TYPE_DIFFERENTIAL);
	
	static MovePilot pilot = new MovePilot(chassis);
}
