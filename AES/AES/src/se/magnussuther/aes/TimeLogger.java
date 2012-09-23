package se.magnussuther.aes;

import java.util.ArrayList;

import android.util.Log;

public abstract class TimeLogger {
	private static final String TAG = "TimeLogger";
	static class Time {
		private long mStartTime = 0;
		private String mMessage = null;
		
		public Time(final String message) {
			mStartTime = System.nanoTime();
			mMessage = message;
		}
		
		public long getStartTime() {
			return mStartTime;
		}
		
		public String getMessage() {
			return mMessage;
		}
	}
	
	
	private static ArrayList<TimeLogger.Time> sTimes = new ArrayList<TimeLogger.Time>();
	
	
	public static void start(final String message) {
		TimeLogger.sTimes.add(new TimeLogger.Time(message));
	}
	
	public static void stop() {
		TimeLogger.Time time = TimeLogger.sTimes.remove(TimeLogger.sTimes.size() - 1);
		long elapsed = System.nanoTime() - time.getStartTime();
		
		Log.i(TAG, time.getMessage() + ". Execution took " + elapsed + " nanoseconds.");
	}
}
