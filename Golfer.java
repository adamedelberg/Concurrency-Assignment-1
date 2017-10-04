
/* 
 * Assignment 2 - Concurrency
 * CSC 2002S
 * Adam Edelberg
 * Skeleton Class: Michelle Kuttel
 * August 2015
 * 
 * Golfer Class
*/

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Golfer extends Thread {

	private  AtomicBoolean done;
	private  AtomicBoolean cartOnField;

	private static int noGolfers = 0; // shared amongst threads
	private static int ballsPerBucket; // shared amongst threads

	private int myID;

	private golfBall[] golferBucket;
	private  BallStash sharedStash; // link to shared stash
	private  Range sharedField; // link to shared field
	private Random swingTime;

	Golfer(BallStash stash, Range field, AtomicBoolean cartFlag, AtomicBoolean doneFlag) {
		sharedStash = stash; // shared
		sharedField = field; // shared
		cartOnField = cartFlag; // shared
		done = doneFlag;
		golferBucket = new golfBall[ballsPerBucket];
		swingTime = new Random();
		myID = newGolfID();
	}

	public static int newGolfID() {
		noGolfers++;
		return noGolfers;
	}

	public static void setBallsPerBucket(int noBalls) {
		ballsPerBucket = noBalls;
	}

	public static int getBallsPerBucket() {
		return ballsPerBucket;
	}

	public void run() {

		while (!done.get()) {

			// print message
			System.out.println(">>> Golfer #" + myID + " trying to fill bucket with " + getBallsPerBucket() + " balls.");
			// get the golf balls
			try {
				golferBucket = sharedStash.getBucketBalls(golferBucket);
			} catch (InterruptedException e1) {
			
				e1.printStackTrace();
			}
			// output message
			System.out.println("<<< Golfer #" + myID + " filled bucket with " + getBallsPerBucket()
					+ " balls ( remaining stash = " + sharedStash.getBallsInStash() + " )");

			// loop for every ball in bucket
			for (int b = 0; b < ballsPerBucket; b++) {
				try {
					// sleep for a simulated time for "swinging"
					sleep(swingTime.nextInt(2000));
					// add the ball #'b' onto the field
					sharedField.hitBallOntoField(golferBucket[b]);
					// output
					System.out.println("Golfer #" + myID + " hit ball #" + golferBucket[b].getID() + " onto field");
					// check that cart is not on field (rule before playing)
					while (cartOnField.get()) {
						// wait 
					}

				} catch (InterruptedException e) {
					e.printStackTrace();
				} // swing

			
			}

		} // while loop end
	} // method end
}
