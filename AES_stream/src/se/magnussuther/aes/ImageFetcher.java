package se.magnussuther.aes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ImageFetcher {
	public ImageFetcher() {
		
	}
	
	public byte[] getImageBytes(final String imagePath) throws IOException {
		File queryImg = new File(imagePath);
		int imageLen = (int)queryImg.length();
		byte[] imgData = new byte[imageLen];
		FileInputStream fis = new FileInputStream(queryImg);
		fis.read(imgData);
		
		return imgData;
	}
	
//	public Bitmap streamDecrypt(final String imagePath, AESDecrypter decrypter) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
//		File queryImg = new File(imagePath);
//	
//		PipedOutputStream os = new PipedOutputStream();
//		InputStream is = new PipedInputStream(os);
//		
////		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		
//		SecretKey sk = decrypter.calculateSecretKey();
//		IvParameterSpec iv = decrypter.calculateIvParameterSpec();
//
//		
//		FileInputStream fis = new FileInputStream(queryImg);
//		
//		Cipher c = Cipher.getInstance(decrypter.getCipherModePadding());
//		
//		int blockSize = c.getBlockSize();
//		
//		byte[] buffer = new byte[blockSize];
//		
//		int read = 0;
////		int i = 0;
//		
//		
//		while((read = fis.read(buffer)) != -1) {
////			baos.write(buffer, 0, read);
////			buffer[i] = (byte)read;
////			fis.read(buffer);
////			i++;
//			
//			if (buffer.length == blockSize) { // TODO: Do only if buffer is full
//				os.write(decrypter.decryptData(buffer, sk, iv, c));
//
//				// Calculate new iv based on the data we just decrypted. This will be
//				// used to decrypt the next block
//				iv = new IvParameterSpec(buffer);
//	//				i = 0;
//
//			}
//		}
//		Bitmap bm = BitmapFactory.decodeStream(is);
//		return bm;
//	}
	
	public Bitmap streamDecrypt(final String imagePath, AESDecrypter decrypter) throws FileNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		File queryImg = new File(imagePath);
		FileInputStream fis = new FileInputStream(queryImg);
		
		Log.d("ImageFetcher", "Decrypting image " + queryImg.getAbsolutePath());
		
		SecretKey sk = decrypter.calculateSecretKey();
		IvParameterSpec iv = decrypter.calculateIvParameterSpec();
		Cipher c = Cipher.getInstance(decrypter.getCipherModePadding());
		c.init(Cipher.DECRYPT_MODE, sk, iv);
		
		CipherInputStream cis = new CipherInputStream(fis, c);
		Bitmap bm = BitmapFactory.decodeStream(cis);
		
		Log.d("ImageFetcher", "Done decrypting image " + queryImg.getAbsolutePath());
		
		return bm;
	}
}
