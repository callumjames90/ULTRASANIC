import java.io.File;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;


public class SanicMusic extends Thread {
	public void run() {
		int count = 1;
		while (true) {
			//LCD.drawInt(count++, 0, 6);
			playTune();
		}
	}
	
	private void playTune() {
		int time = Sound.playSample(new File("sanic.wav"), 10);
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
