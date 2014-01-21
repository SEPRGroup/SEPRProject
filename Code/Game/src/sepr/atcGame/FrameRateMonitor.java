package sepr.atcGame;

final class FrameRateMonitor{
	private long[] frameTimes;
	private long totalTime;
	private int pos;

	private long frames = 0;


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
		if (0 == pos){
			System.out.println("fps:\t" +getFrameRate());}

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