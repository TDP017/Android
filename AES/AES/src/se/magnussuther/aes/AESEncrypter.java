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

public class AESEncrypter extends AES {
	
	public AESEncrypter(final String passphrase, final String keyGenerationAlgorithm, final String cipherModePadding, final byte[] initializationVector, final byte[] salt, final int hashIterations, final int keyLength) {
		super(passphrase, keyGenerationAlgorithm, cipherModePadding, initializationVector, salt, hashIterations, keyLength);
	}
	
	
	public byte[] encryptData(final byte[] data) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
//		TimeLogger.start("AESEncrypter.encryptData(): Setting up secret key");
		char[] passphrase = getPassphrase().toCharArray();
		PBEKeySpec pbeKeySpec = new PBEKeySpec(passphrase, getSalt(), getHashIterations(), getKeyLength());
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(getKeyGenerationAlgorithm());
		SecretKey sk = keyfactory.generateSecret(pbeKeySpec);
//		byte[] skAsByteArray = sk.getEncoded();
//		SecretKey skforAES = new SecretKeySpec(skAsByteArray, "AES");
		Cipher c = Cipher.getInstance(getCipherModePadding());
		IvParameterSpec iv = new IvParameterSpec(getInitializationVector());
//		TimeLogger.stop();
//		TimeLogger.start("AESEncrypter.encryptData(): Initializing Cipher");
		c.init(Cipher.ENCRYPT_MODE, sk, iv);
//		TimeLogger.stop();
//		TimeLogger.start("AESEncrypter.encryptData(): Encrypting data");
		byte[] bytes = c.doFinal(data);
//		TimeLogger.stop();
		return bytes;
	}
}
