package com.finEasy.models.utilities.cryptography;



public interface RsaAesStandard {
public String getSecretAESKeyAsString() throws Exception;
	
	public String encryptAESKey(String plainAESKey, String publicKey) throws Exception;
	
	public String decryptAESKey(String data, String base64PrivateKey) throws Exception;
	
	public String encryptTextUsingAES(String plainText, String aesKeyString) throws Exception;
	
	public String decryptTextUsingAES(String msgEncrypted, String key) throws Exception;


}
