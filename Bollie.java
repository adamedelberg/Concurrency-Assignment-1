
/* 
 * Assignment 2 - Concurrency
 * CSC 2002S
 * Adam Edelberg
 * Skeleton Class: Michelle Kuttel
 * August 2015
 * 
 * Bollie Class
*/

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Bollie extends Thread {

	private static AtomicBoolean done; // flag to indicate when threads should stop
	private static BallStash sharedStash; // link to shared stash
	private static Range sharedField; // link to shared field
	private Random waitTime;

	// link to shared field
	Bollie(BallStash stash, Range field, AtomicBoolean doneFlag) {
		sharedStash = stash; // shared
		sharedField = field; // shared
		waitTime = new Random();
		done = doneFlag;
	}

	public void run() {

        ArrayBlockingQueue<golfBall> ballsCollected = new ArrayBlockingQueue<>(BallStash.getSizeStash());

		while (done.get() != true) {
			try {
				// simulated wait time until bollie clears range
				sleep(waitTime.nextInt(5000));
				
				System.out.println("*********** Bollie collecting balls   ************");
				synchronized(sharedField) {
					sharedField.collectAllBallsFromField(ballsCollected);
					
				}
				System.out.println("*********** Bollie adding "+ballsCollected.size()+ " balls to stash ************");
				sleep(1000);

				// collect balls, not allowed to swing while this is happening
				 sharedStash.addBallsToStash(ballsCollected);
			} catch (InterruptedException e) {
				System.out.println("Error: Bollie");
				e.printStackTrace();
			}
		}
	}
}
