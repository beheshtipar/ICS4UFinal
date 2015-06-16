public class StopWatch {

	// Instance variables
	private long startTime = 0;
	private long stopTime = 0;
	private boolean going = false;
	
	// Start the timer
	public void start() {
		this.startTime = System.currentTimeMillis();
		this.going = true;
	}
	
	// Stop the timer
	public void stop() {
    this.stopTime = System.currentTimeMillis();
    this.going = false;
	}


	// Elaspsed time in milliseconds
	public long getElapsedTime() {
		long elapsed;
    	if (going)
    		elapsed = (System.currentTimeMillis() - startTime);
    	else 
    		elapsed = (stopTime - startTime);
    	return elapsed;
	}


	// Elaspsed time in seconds
	public long getElapsedTimeSecs() {
		long elapsed;
		if (going) 
			elapsed = ((System.currentTimeMillis() - startTime) / 1000);
		else 
			elapsed = ((stopTime - startTime) / 1000);
		return elapsed;
	}
}