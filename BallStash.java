
/* 
 * Assignment 2 - Concurrency
 * CSC 2002S
 * Adam Edelberg
 * Skeleton Class: Michelle Kuttel
 * August 2015
 * 
 * BallStash Class
*/

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BallStash {
	// static variables
	// size of the ball stash
	private static int sizeStash;
	// size of each bucket
	private static int sizeBucket;
	// collection of golf balls
	private static BlockingQueue<golfBall> stash;

	// number of balls in the stack
	private AtomicInteger balls;
	// flag for determining end of game
	private AtomicBoolean doneFlag;

	// added methods over skeleton class:
	// getBucketBalls - returns an array (bucket) of balls
	// addBallsToStash - populates the ball stash with collected balls from field
	// getBallsInStash - return number of balls in the stash

	// constructor
	BallStash(AtomicBoolean doneFlag) {
		this.doneFlag = doneFlag;
		// set stash to the size of balls in collection
		stash = new ArrayBlockingQueue<>(sizeStash);
		// set the number of balls in the stash
		balls = new AtomicInteger(stash.size());
		// set the stash to contain golf balls
		for (int i = 0; i < sizeStash; i++) {
			// add golf ball
			stash.add(new golfBall());
		}
	}

	// method to retrieve balls from a given bucket
	public synchronized golfBall[] getBucketBalls(golfBall[] ball) throws InterruptedException {

		if (getBallsInStash() < sizeBucket) {
			notify();
			wait();
		}

		// iterate for the size of the bucket
		for (int i = 0; i < sizeBucket; i++) {
			// retrieve ball from the stash queue and add to ball array
			try {
				// add balls to the stash
				ball[i] = stash.take();
			} catch (Exception e) {
				System.out.println("Error: BallStash - getBucketBalls");
			}
		}

		// set the number of balls in the stash
		balls.set(stash.size());
		notifyAll();
		// return the resulting array
		return ball;

	}

	// method to add balls to the stash
	public synchronized void addBallsToStash(ArrayBlockingQueue<golfBall> ball) throws InterruptedException {
		if (getBallsInStash() == sizeStash) {
			wait();
		}
		ball.drainTo(stash);
		// reset the number of balls in the stash
		balls.set(stash.size());
		notify();

	}

	// method to return the number of balls in the stash
	public int getBallsInStash() {
		return balls.get();
	}

	// getters and setters for static variables
	// sets the size of the bucket
	public static void setSizeBucket(int noBalls) {
		sizeBucket = noBalls;
	}

	// returns the size of the bucket
	public static int getSizeBucket() {
		return sizeBucket;
	}

	// sets the size of the stash
	public static void setSizeStash(int noBalls) {
		sizeStash = noBalls;
	}

	// returns the size of the stash
	public static int getSizeStash() {
		return sizeStash;
	}

}
