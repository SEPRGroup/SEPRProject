package sepr.atcGame;

public final class FrameRateMonitor{
	public long[] frameTimes;
	public /*private*/ long totalTime; //MH: Changed to public for testing. (FrameRateMonitorTest.java)
	public /*private*/ int pos; //MH: Changed to public for testing. (FrameRateMonitorTest.java)
	public /*private*/ long frames = 0; //MH: Changed to public for testing. (FrameRateMonitorTest.java)

	public FrameRateMonitor(int bufferSize) {
		frameTimes = new long[bufferSize];
		reset();
	}


	public double getFrameRate(){
		return (frameTimes.length*1000000000.0 /totalTime);}

	public long getFrames(){
		return frames;}


	public void newFrame(long nano) {
		totalTime = totalTime -frameTimes[pos] +nano;
		frameTimes[pos] = nano;
		pos = (pos +1) % frameTimes.length;
		frames++;

		//System.out.println(nano);
		/*if (0 == pos){
			System.out.println("fps:\t" +getFrameRate());}*/
	}

	public void reset(){
		for (int i=0; i < frameTimes.length; i++){
			frameTimes[i] = 0;}
		totalTime = 0;
		pos = 0;
		frames = 0;
	}
	
	
	@Override
	public String toString(){
		String s = "Frame rate: " + frameTimes.length*1000000000.0 /totalTime;
		return s;
	}
	

}