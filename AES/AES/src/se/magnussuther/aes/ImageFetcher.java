package se.magnussuther.aes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.crypto.Cipher;

import android.graphics.Bitmap;

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

//	public Bitmap openEncryptedImageFromSD(final String imagePath, final AESDecrypter decrypter) throws IOException {
//		byte[] encryptedImageData = getImageBytes(imagePath);
//		
//		byte[] decryptedImageData = decrypter.decryptFile(encryptedImageData);
//	}
}
