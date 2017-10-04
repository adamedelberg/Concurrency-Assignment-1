
/* 
 * Assignment 2 - Concurrency
 * CSC 2002S
 * Adam Edelberg
 * Skeleton Class: Michelle Kuttel
 * August 2015
 * 
 * golfBall Object Class
*/


public class golfBall {
	// static variables
	private static int noBalls;
	private  int myID;

	// constructor
	golfBall() {
		myID = noBalls;
		incID();
	}

	/*
	 * Thread Safety Rationale: As the calling method is not a static inference,
	 * synchronizing the get and set methods will only lock on the instance of
	 * the class that runs the method thus there can be multiple threads that
	 * modify the same object simultaneously, therefore not being thread safe.
	 * In this case, we would need to use an Atomic Integer.
	 */

	public int getID() {
		return myID;
	}

	public static void incID() {
		noBalls++;
	}

}
