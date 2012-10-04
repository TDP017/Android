package se.magnussuther.aes;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;


public class AESDecrypter extends AES {
	
	public AESDecrypter(final String passphrase, final String keyGenerationAlgorithm, final String cipherModePadding, final byte[] initializationVector, final byte[] salt, final int hashIterations, final int keyLength) {
		super(passphrase, keyGenerationAlgorithm, cipherModePadding, initializationVector, salt, hashIterations, keyLength);
	}

	public byte[] decryptData(byte[] data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
//		TimeLogger.start("AESDecrypter.decryptData(): Setting up secret key");
		Cipher c = Cipher.getInstance(getCipherModePadding());
		char[] passphrase = getPassphrase().toCharArray();
		PBEKeySpec pbeKeySpec = new PBEKeySpec(passphrase, getSalt(), getHashIterations(), getKeyLength());
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(getKeyGenerationAlgorithm());
		SecretKey sk = keyfactory.generateSecret(pbeKeySpec);
		IvParameterSpec iv = new IvParameterSpec(getInitializationVector());
//		TimeLogger.stop();
//		TimeLogger.start("AESDecrypter.decryptData(): Initializing Cipher");
		c.init(Cipher.DECRYPT_MODE, sk, iv);
//		TimeLogger.stop();
		
//		TimeLogger.start("AESDecrypter.decryptData(): Decrypting data");
		byte[] bytes = c.doFinal(data);
//		TimeLogger.stop();
		return bytes;
	}
}
