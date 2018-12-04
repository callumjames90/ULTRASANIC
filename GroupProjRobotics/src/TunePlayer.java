/*
 * Modified from https://github.com/cyclingProfessor/LejosExamples/blob/master/cp/lejos/TunePlayer.java
 * Author: Dave Cohen
 */


import java.io.File;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

public class TunePlayer extends Thread {
	
	int soundLevel = 50;
	
	public void run() {
		while (true) {
			playTune();
		}
	}

	public void playTune() {
		int time = Sound.playSample(new File("sanic.wav"), soundLevel);

		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
