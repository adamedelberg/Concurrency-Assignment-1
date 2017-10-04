
/* 
 * Assignment 2 - Concurrency
 * CSC 2002S
 * Adam Edelberg
 * Skeleton Class: Michelle Kuttel
 * August 2015
 * 
 * Driver Class
*/

import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class DrivingRangeApp {

	// shared variables
	// static BallStash sharedStash;
	// static Range sharedField;
	// static int noGolfers;
	// static int sizeStash;
	// static int sizeBucket;

	public static void main(String[] args) throws InterruptedException {
		AtomicBoolean done = new AtomicBoolean(false);
		AtomicBoolean cartOnField = new AtomicBoolean(false);

		// ------- TESTING IN CONSOLE -------
		Scanner scn = new Scanner(System.in);
		int noGolfers = scn.nextInt();
		int sizeStash = scn.nextInt();
		int sizeBucket = scn.nextInt();
		// ----------------------------------

		// command line arguments
		// int noGolfers = Integer.parseInt(args[0]);
		// int sizeStash = Integer.parseInt(args[1]);
		// int sizeBucket = Integer.parseInt(args[2]);

		// set the class variables
		BallStash.setSizeStash(sizeStash);
		BallStash.setSizeBucket(sizeBucket);
		Golfer.setBallsPerBucket(sizeBucket);
		// Range.setSizeStash(sizeStash);

		/// initialize shared variables
		// BallStash sharedStash = new BallStash(sizeStash, sizeBucket, done);
		// Range sharedField = new Range(sharedStash, done);
		BallStash sharedStash = new BallStash(done);
		Range sharedField = new Range(cartOnField, done);

		// output
		System.out.println("=======   River Club Driving Range Open  ========");
		System.out.println("======= Golfers:" + noGolfers + " balls: " + sizeStash + " bucketSize:" + sizeBucket + "  ======");

		// array of golfer threads
		Golfer threads[] = new Golfer[noGolfers];
		// loop for number of golfer threads to be created
		for (int i = 0; i < noGolfers; i++) {
			// create golfer threads
			threads[i] = new Golfer(sharedStash, sharedField, cartOnField, done);
			// start golfer threads
			threads[i].start();
		}

		// create bollie thread
		Bollie bollie = new Bollie(sharedStash, sharedField, done);
		// start bollie thread
		bollie.start();

		// sleep for a random interval [1 - 100 seconds]
		Thread.sleep((int) (1 + (Math.random() * 100) * 1000));
		// synchronized (sharedStash) {
		// shut down gracefully
		done.set(true);
		// output
		System.out.println("=======  River Club Driving Range Closing ========");
		// }

	}

}
