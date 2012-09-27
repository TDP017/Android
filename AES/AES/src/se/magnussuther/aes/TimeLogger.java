package se.magnussuther.aes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

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
	private static File sLogFile = null;

	private static BufferedWriter sWriter = null;
	
	
	public static void start(final String message) {
		TimeLogger.sTimes.add(new TimeLogger.Time(message));
	}
	
	public static void stop() {
		TimeLogger.Time time = TimeLogger.sTimes.remove(TimeLogger.sTimes.size() - 1);
		long elapsed = System.nanoTime() - time.getStartTime();
		
		String message = time.getMessage() + ". Execution took " + elapsed / 1000000000.0 + " seconds.";
		

		try {
			TimeLogger.sWriter.append(message);

			TimeLogger.sWriter.write(message);
			TimeLogger.sWriter.newLine();
			TimeLogger.sWriter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setLogFile(File f) {
		TimeLogger.sLogFile = f;
		
		try {
			OutputStreamWriter osw = new OutputStreamWriter(
					new FileOutputStream(TimeLogger.sLogFile, true), "UTF-8");
			
					TimeLogger.sWriter = new BufferedWriter(osw);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
