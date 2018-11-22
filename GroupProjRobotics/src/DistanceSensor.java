import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class DistanceSensor extends Thread {
	
	static EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S1);
	static SampleProvider sp = us.getDistanceMode();
	static float sample[] = new float[1];
	
	public DistanceSensor() {}
	
	public void run() {
		while (true) {
			sp.fetchSample(sample, 0);
			//LCD.drawString(Float.toString(sample[0]), 0, 0);			
		}
	}

}