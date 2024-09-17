package com.finEasy.models.utilities.cryptography;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;






@Service
public class RsaAesGcmStandard implements RsaAesStandard {

	private static final String RSA_ENCRYPT_ALGO = "RSA/ECB/PKCS1Padding"; // RSA Algorithm/Mode/Padding
	private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding"; // AES Algorithm/Mode/Padding
	private static final int TAG_LENGTH_BIT = 128; // Length of authentication tag
	private static final int IV_LENGTH_BYTE = 12; // Length of initialization vector
	private static final Charset UTF_8 = StandardCharsets.UTF_8;

	@Override
	public String getSecretAESKeyAsString() throws Exception {
		KeyGenerator generator = KeyGenerator.getInstance("AES");
		generator.init(256); // The AES key size in number of bits
		SecretKey secKey = generator.generateKey();
		String encodedKey = Base64.getEncoder().encodeToString(secKey.getEncoded());
		return encodedKey;
	}

	@Override
	public String encryptAESKey(String plainAESKey, String publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance(RSA_ENCRYPT_ALGO);
		cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
		return Base64.getEncoder().encodeToString(cipher.doFinal(Base64.getDecoder().decode(plainAESKey)));
	}

	@Override
	public String decryptAESKey(String data, String base64PrivateKey) throws Exception {
		Cipher cipher = Cipher.getInstance(RSA_ENCRYPT_ALGO);
		cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(base64PrivateKey));
		return Base64.getEncoder().encodeToString(cipher.doFinal(Base64.getDecoder().decode(data)));
	}

	@Override
	public String encryptTextUsingAES(String plainText, String aesKeyString) throws Exception {
		byte[] decodedKey = Base64.getDecoder().decode(aesKeyString);
		byte[] iv = getRandomNonce(IV_LENGTH_BYTE);

		SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
		Cipher aesCipher = Cipher.getInstance(ENCRYPT_ALGO);
		aesCipher.init(Cipher.ENCRYPT_MODE, originalKey, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

		byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes(UTF_8));
		// We prefix the IV to the encrypted text, because we need the same IV for Decryption
		byte[] cipherTextWithIv = ByteBuffer.allocate(iv.length + byteCipherText.length).put(iv).put(byteCipherText)
				.array();

		return Base64.getEncoder().encodeToString(cipherTextWithIv);
	}

	
	
	
	@Override
	public String decryptTextUsingAES(String encryptedText, String aesKeyString) throws Exception {
		byte[] decodedKey = Base64.getDecoder().decode(aesKeyString);
		SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
		byte[] decode = Base64.getDecoder().decode(encryptedText.getBytes(UTF_8));
		ByteBuffer bb = ByteBuffer.wrap(decode);
		byte[] iv = new byte[IV_LENGTH_BYTE];
		bb.get(iv);
		byte[] cipherText = new byte[bb.remaining()];
		bb.get(cipherText);

		Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
		cipher.init(Cipher.DECRYPT_MODE, originalKey, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
		byte[] plainText = cipher.doFinal(cipherText);

		return new String(plainText, UTF_8);
	}

	protected PublicKey getPublicKey(String base64PublicKey) {
		PublicKey publicKey = null;
		try {
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			publicKey = keyFactory.generatePublic(keySpec);
			return publicKey;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return publicKey;
	}

	protected PrivateKey getPrivateKey(String base64PrivateKey) {
		PrivateKey privateKey = null;
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		try {
			privateKey = keyFactory.generatePrivate(keySpec);
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return privateKey;
	}

	protected byte[] getRandomNonce(int len) {
		byte[] nonce = new byte[len];
		new SecureRandom().nextBytes(nonce);
		return nonce;
	}

	
	
	
	
	public static void main(String[] args) throws Exception {
		RsaAesGcmStandard rsaAesGcmStandard = new RsaAesGcmStandard();
//		
		String text="{\n" + 
				"    \"username\": \"BOADESINA\",\n" + 
				"    \"password\": \"catalina@2020\"\n" + 
				"}";
		
		
		String text7="{\n" + 
				"    \"countryName\": \"\",\n" + 
				"    \"countryDescription\": \"\",\n" + 
				"    \"countryCode\": \"NG\"\n" + 
				"}";

		String text1 = "{\n" + 
				"    \"batchId\": \"\",\n" + 
				"    \"transactionRef\": \"AFC29092021000000002323\"\n" + 
				"}";
		String text4="{\n" + 
				"		    \"transactionReference\": \"AFC30092021000000002347\",\n" + 
				"		    \"status\": \"REJECTED\"\n" + 
				"		}";
		
		
		String text9="{\n" + 
				"    \"requestId\": \"23234\",\n" + 
				"    \"restrictionType\": \"FLAG\",\n" + 
				"    \"createBy\": \"BOADESINA\",\n" + 
				"    \"comment\": \"world terrorism list\",\n" + 
				"    \"affiliateCode\": \"ENG\",\n" + 
				"    \"cluster\": \"ENG\",\n" + 
				"    \"restrictions\": [\n" + 
				"        {\n" + 
				"            \"sanctionCategory\": \"BENEFICIARYCNTRY\",\n" + 
				"            \"description\": \"\",\n" + 
				"            \"role\": \"SEND\",\n" + 
				"            \"channels\": [\n" + 
				"                {\n" + 
				"                    \"channel\":\"SUBA\",\n" + 
				"                    \"channelDescription\":\"\",\n" + 
				"                    \"createUser\":\"\"\n" + 
				"\n" + 
				"                },\n" + 
				"                {\n" + 
				"                    \"channel\":\"MOBILE\",\n" + 
				"                    \"channelDescription\":\"\",\n" + 
				"                    \"createUser\":\"\"\n" + 
				"\n" + 
				"                }\n" + 
				"            ],\n" + 
				"            \"countries\": [\n" + 
				"                {\n" + 
				"                    \"countryName\": \"\",\n" + 
				"                    \"countryDescription\": \"\",\n" + 
				"                    \"countryCode\": \"UK\"\n" + 
				"                },\n" + 
				"                {\n" + 
				"                    \"countryName\": \"\",\n" + 
				"                    \"countryDescription\": \"\",\n" + 
				"                    \"countryCode\": \"US\"\n" + 
				"                }\n" + 
				"            ],\n" + 
				"            \"affiliates\": [\n" + 
				"                {\n" + 
				"                    \"affiliateCode\": \"EGH\",\n" + 
				"                    \"affliateDescription\": \"\",\n" + 
				"                    \"affiliateCluster\": \"\"\n" + 
				"                }\n" + 
				"            ],\n" + 
				"            \"nationalities\": []\n" + 
				"        }\n" + 
				"    ]\n" + 
				"}";
		
		String text10="{\n" + 
				"    \"requestId\": \"23234\",\n" + 
				"    \"restrictionType\": \"FLAG\",\n" + 
				"    \"createBy\": \"BOADESINA\",\n" + 
				"    \"comment\": \"world terrorism list\",\n" + 
				"    \"clusterOrAffiliate\": \"CLST\",\n" + 
				"    \"restrictions\": [\n" + 
				"        {\n" + 
				"            \"sanctionCategory\": \"BENEFICIARYNAT\",\n" + 
				"            \"description\": \"\",\n" + 
				"            \"role\": \"SEND\",\n" + 
				"            \"channels\": [\n" + 
				"                {\n" + 
				"                    \"channel\": \"BRN\",\n" + 
				"                    \"channelDescription\": \"\",\n" + 
				"                    \"createUser\": \"\"\n" + 
				"                },\n" + 
				"                {\n" + 
				"                    \"channel\": \"ECOMOBILE\",\n" + 
				"                    \"createUser\": \"\"\n" + 
				"                }\n" + 
				"            ],\n" + 
				"            \"clusterOrAffiliate\": [\n" + 
				"                {\n" + 
				"                    \"clusterOrAffiliateCode\": \"EGH\"\n" + 
				"                }\n" + 
				"            ],\n" + 
				"            \"countriesOrNationalities\": [\n" + 
				"                {\n" + 
				"                    \"name\": \"AU\"\n" + 
				"                }\n" + 
				"            ]\n" + 
				"        }\n" + 
				"    ]\n" + 
				"}";
		String text11="{\n" + 
				"    \"restrictionID\": \"22\",\n" + 
				"    \"user\":\"BOADESINA\"\n" + 
				"}";
		String text12="{\n" + 
				"    \"restrictionID\": \"23\"\n" + 
				"\n" + 
				"}";
		
		String text13="{\n" + 
				"\"channel\":\"AGENCY\",\n" + 
				"\"channelDescription\":\"AGENCY BANKING\",\n" + 
				"\"createUser\":\"BOADESINA\"\n" + 
				"}";
		
		String text14="{\n" + 
				"   \"transactionRef\":\"88236\",\n" + 
				"   \"action\":\"PENDING\",\n" + 
				"   \"userId\":\"\"\n" + 
				"}";
		
		
		String text15="{\n" + 
				"   \"transactionRef\":\"PREPRODZ10\",\n" + 
				"   \"action\":\"RIPPLE\",\n" + 
				"   \"userId\":\"\"\n" + 
				"}";
		
		String text16="{\n" + 
				"    \"transactionRef\": \"AFC29092021000000002296\",\n" + 
				"    \"action\": \"APPROVED\",\n" + 
				"    \"userId\": \"BOADESINA\",\n" + 
				"    \"comment\": \"approved\"\n" + 
				"}";
		
		String text17="{\n" + 
				"    \"watchListCategory\": \"ALL\",\n" + 
				"    \"restrictionType\": \"FLAG\",\n" + 
				"    \"jurisdictionCategory\": \"ALL\",\n" + 
				"    \"channel\": \"BRN\"\n" + 
				"}";
		
		String text18="{\n" + 
				"    \"transactionRef\": \"36\",\n" + 
				"    \"action\": \"\",\n" + 
				"    \"userId\": \"\",\n" + 
				"    \"comment\": \"\"\n" + 
				"}";
		
		String text19=""
				+ "{\n" + 
				"    \"transactionRef\": \"000012309177\",\n" + 
				"    \"action\": \"DEACTIVATE\",\n" + 
				"    \"userId\": \"TBELO\",\n" + 
				"    \"comment\": \"APPLY RESTRICTION\"\n" + 
				"}";
		
		String text20=""
				+ "{\n" + 
				"    \"transactionRef\": \"1260578\",\n" + 
				"    \"action\": \"REJECTED\",\n" + 
				"    \"userId\": \"boadesina\",\n" + 
				"    \"comment\": \"TEST\"\n" + 
				"}";
		
		
		
String text3="iQvac4GNws9f852n3MzgPsy9DiMYdrgxiVpS7ZZpeCa24n+9QhOo6YS06X6vFMhaSe2C+TXxAo9dT8y/vL3t9rh91GOidV/zGiFwaqj6a+Uhe4XixlTmN1zNaBCgEGrYDVVGo3RfFz+nimpu/gFwJxBESFsiQLCD91cHSA==";			
				//String text2="NqGtaiOp32t1P+EpzHH4U6pvIIkMBS2fKn0TN3Lx84BWrnEHDtsXwheb8xTmdB2GoGa+xwFsQ3+xrhKyM1xpAHFb/tbewNv5dNFXavQrZWSlbp63+/gGI95dhnHfa5yoBgj3KZRC7ZzVX+pA16mN62E9kiUKDtErYVbz1g==";
					String text6="AZDiW4RziRmyqkH38GX9jTDre4FZrr+lqXWDRKllwsSE47+AcDK3iskQfMYv2b+9iepQUHvtSAJ6m7wdvAMX2dOnn8wil34YjDHzhzuzFv9/pfbccyFoEM8717xI1MspY9xmGscOOjydGL6Vl/jwZLNlWYrlg3wfP3TLt4k2yE5Ai0pHjzo8AhbudnY8QeKcc9eZyYct0xms/csNfFeCKAQqCiM/zrN7+MrpoOVg+W76fRhqwLHkAitagFJ3+i1zisnlVg+6t7kS2JkYmYIEdut2m5NbAmfH1c1zu6iA6vS5+F8z8wyGk2H7mNk3sUkbO32g67kZBwoFuPrOfkWWn+Sh8us4zxaMiI3RkolPWeRrffnBqTnx+Ig5nNE=";
				String key3="OrKk+9qgXR/vhNW4O3S4BR07KiN5YljXx2gG169hHmg=";
				String key2="+F8l/aUsH6qXGU0RRBhrOZNPDyIk759Q05FygHIMXa8=";
				String  key="vvjnEsBISdbOvVOZjuu26k9GSWJZLPwvfhXoIF71MDA";
		System.out.println(rsaAesGcmStandard.encryptTextUsingAES(text20,key3));
		//System.out.println(rsaAesGcmStandard.decryptTextUsingAES(text2, "OrKk+9qgXR/vhNW4O3S4BR07KiN5YljXx2gG169hHmg="));
		//System.out.println(rsaAesGcmStandard.decryptTextUsingAES(text6,key2));
		
		//System.out.println(rsaAesGcmStandard.decryptTextUsingAES(text1, key2));	
		
//		{
//		    "requestId": "23234",
//		    "restrictionType": "BLOCK",
//		    "createBy": "BOADESINA",
//		    "comment": "world terrorism list",
//		    "clusterOrAffiliate": "CLST",
//		    "restrictions": [
//		        {
//		            "sanctionCategory": "BENEFICIARYNAT",
//		            "description": "",
//		            "role": "SEND",
//		            "channels": [
//		                {
//		                    "channel": "SUBA",
//		                    "channelDescription": "",
//		                    "createUser": ""
//		                },
//		                {
//		                    "channel": "ECOMOBILE",
//		                    "createUser": ""
//		                }
//		            ],
//		            "clusterOrAffiliate": [
//		                {
//		                    "clusterOrAffiliateCode": "EMW"
//		                }
//		            ],
//		            "countriesOrNationalities": [
//		                {
//		                    "name": "AU"
//		                }
//		            ]
//		        }
//		    ]
//		}
//	}
		
//		
//		
//		{
//		    "transactionRef": "000012309177",
//		    "action": "DEACTIVATE",
//		    "userId": "TBELO",
//		    "comment": "APPLY RESTRICTION "
//		}
	
	}
}
