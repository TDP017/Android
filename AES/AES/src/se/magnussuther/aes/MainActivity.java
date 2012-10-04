package se.magnussuther.aes;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final String TAG = "AES MainActivity";
			
	//private MainView mMainView = null;	
	private ImageFetcher mImageFetcher = null;
	private AES mAES = null;
	
	private int i = 0;
	private double avg = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        /*
        mMainView = (MainView) findViewById(R.id.mainview);
        mMainView.registerEventListener(this);
        */
        
        
        mImageFetcher = new ImageFetcher();
        
        byte[] iv = {0xA,1,0xB,5,4,0xF,7,9,0x17,3,1,6,8,0xC,0xD,91};
        byte[] salt = {0,1,2,3,4,5,6,7,8,9,0xA,0xB,0xC,0xD,0xE,0xF};
        mAES = new AES("secretdonald", "PBEWITHSHAANDTWOFISH-CBC", "AES/CBC/PKCS7Padding", iv, salt, 10000, 256);
        
        
        // Create log file
        Calendar cal = Calendar.getInstance();
        String logPath = Environment.getExternalStorageDirectory() + "/AES/log_" + cal.get(Calendar.YEAR) 
        		+ "-" + cal.get(Calendar.MONTH) 
        		+ "-" + cal.get(Calendar.DAY_OF_MONTH) 
        		+ "." + cal.get(Calendar.HOUR_OF_DAY) 
        		+ "_" +  cal.get(Calendar.MINUTE) 
        		+ "_" + cal.get(Calendar.SECOND) 
        		+ ".txt";
        File logFile = new File(logPath);
        if (!logFile.exists()) {
        	try {
				logFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        TimeLogger.setLogFile(logFile);
       
	    File orig = new File(Environment.getExternalStorageDirectory() + "/AES/Orig");
	    String[]originals = orig.list();
	    for (String path : originals) {
	        try {
	        	File f = new File(Environment.getExternalStorageDirectory() + "/AES/Orig/" + path);
	            byte[] encrypted = encryptImage(f.getAbsolutePath());
	            File crypt = new File(Environment.getExternalStorageDirectory() + "/AES/Crypt/" + f.getName());
	            crypt.createNewFile();
	        	BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(crypt));
	        	bos.write(encrypted);
	        } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    
	    
    }

    @Override
	public void onStart(){
    	super.onStart();
        	
		 onMainViewDraw();
	}
    
    
	private byte[] encryptImage(final String imagePath) {
		try {
			byte[] origImage = mImageFetcher.getImageBytes(imagePath);
			byte[] bytes = mAES.getEncrypter().encryptData(origImage);
			return bytes;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
    
	
	private byte[] decryptImage(final String imagePath) {
		try {
			byte[] encrypted = mImageFetcher.getImageBytes(imagePath);
			long startTime = System.nanoTime();
			byte[] bytes =  mAES.getDecrypter().decryptData(encrypted);
			double time = System.nanoTime() - startTime;
			avg += time / 1000000000.0;
			
			if (this.i == 9) {
				TimeLogger.log("Image: " + imagePath + " Time: " + avg / 10.0);
			}
			return bytes;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public void onMainViewDraw() {
		File crypt = new File(Environment.getExternalStorageDirectory() + "/AES/Crypt");
		String[] files = crypt.list();
		
		Log.d(TAG, "INSIDE ONMAINVIEWDRAW");
		
		TextView t=new TextView(this);
		 t=(TextView)findViewById(R.id.debugText); 

		 /*
		 LinearLayout vg = (LinearLayout)findViewById (R.id.hassan);
		 vg.invalidate();
*/
		 
		 getWindow().getDecorView().findViewById(android.R.id.content).invalidate();
		
		for (String path: files) {
			this.avg = 0;
			for (int i = 0; i != 10; i++) {
				this.i = i;
				File file = new File(path);
				
				t.append("Decrypting: " + file.getName() + "\n");
				
				byte[] original = decryptImage(Environment.getExternalStorageDirectory() + "/AES/Crypt/" + file.getName());
			}
        }
	}
}


