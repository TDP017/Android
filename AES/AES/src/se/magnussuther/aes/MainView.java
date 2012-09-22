package se.magnussuther.aes;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


interface MainViewEvents {
	public void onMainViewDraw(Canvas canvas);
}

public class MainView extends View {
	private static final String TAG = "AES MainActivity";
	
	private MainViewEvents mEventListener = null;
	
	public MainView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void registerEventListener(MainViewEvents ev) {
		mEventListener = ev;
	}
	
//	public MainView(Context context) {
//		super(context);
//	}
	
	@Override
	public void onDraw(Canvas canvas) {
		Log.d(TAG, ";JHKNLKM;");
		mEventListener.onMainViewDraw(canvas);
	}
}
