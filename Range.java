
/* 
 * Assignment 2 - Concurrency
 * CSC 2002S
 * Adam Edelberg
 * Skeleton Class: Michelle Kuttel
 * August 2015
 * 
 * Range Class
*/

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import static java.lang.Thread.sleep;

public class Range {

	// static variables
	// private static int sizeStash;
	// boolean to determine cartOnField state
	private static AtomicBoolean cartOnField;
	// boolean to determine done state
	private static AtomicBoolean done;
	// collection of ballsOnField
	private BlockingQueue<golfBall> ballsOnField;

	// constructor
	Range(AtomicBoolean cartFlag, AtomicBoolean doneFlag) {
		done = doneFlag;
		ballsOnField = new ArrayBlockingQueue<>(BallStash.getSizeStash());
		cartOnField = cartFlag;

	}

	// ADD method: collectAllBallsFromField

	public synchronized void collectAllBallsFromField(ArrayBlockingQueue<golfBall> ballsCollected) throws InterruptedException {
		
//		while(ballsOnField.size()<BallStash.getSizeStash()){
//			try {
//				wait();
//			}
//			catch (InterruptedException e) {}
//		}
		
		

		if (done.get() != true) {
			cartOnField.set(true);
			ballsOnField.drainTo(ballsCollected);
            sleep(1000);
			cartOnField.set(false);
			System.out.println("*********** Bollie collected "+ ballsCollected.size()+" balls from range ************");
            sleep(1000);

		}

		
	}

	// ADD method: hitBallOntoField(golfBall ball)

	public synchronized void hitBallOntoField(golfBall ball) {
		while (cartOnField.get())
        {
        }
		ballsOnField.add(ball);

	}

}
