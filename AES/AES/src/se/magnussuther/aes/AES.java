package se.magnussuther.aes;

public class AES {
	
	private final String mPassphrase;
	private final String mKeyGenerationAlgorithm;
	private final String mCipherModePadding;
	private final byte[] mInitializationVector;
	private final byte[] mSalt;
	private final int mHashIterations;
	private final int mKeyLength;
	
	
	public AES(final String passphrase, final String keyGenerationAlgorithm, final String cipherModePadding, final byte[] initializationVector, final byte[] salt, final int hashIterations, final int keyLength) {
		mPassphrase = passphrase;
		mKeyGenerationAlgorithm = keyGenerationAlgorithm;
		mCipherModePadding = cipherModePadding;
		mInitializationVector = initializationVector;
		mSalt = salt;
		mHashIterations = hashIterations;
		mKeyLength = keyLength;
	}
	
	
	public String getPassphrase() {
		return mPassphrase;
	}


	public String getKeyGenerationAlgorithm() {
		return mKeyGenerationAlgorithm;
	}


	public String getCipherModePadding() {
		return mCipherModePadding;
	}


	public byte[] getInitializationVector() {
		return mInitializationVector;
	}


	public byte[] getSalt() {
		return mSalt;
	}


	public int getHashIterations() {
		return mHashIterations;
	}


	public int getKeyLength() {
		return mKeyLength;
	}

	
	public AESEncrypter getEncrypter() {
		return new AESEncrypter(mPassphrase, mKeyGenerationAlgorithm, mCipherModePadding, mInitializationVector, mSalt, mHashIterations, mKeyLength);
	}
	
	public AESDecrypter getDecrypter() {
		return new AESDecrypter(mPassphrase, mKeyGenerationAlgorithm, mCipherModePadding, mInitializationVector, mSalt, mHashIterations, mKeyLength);
	}
}
