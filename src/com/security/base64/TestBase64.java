package com.security.base64;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class TestBase64 {
	
	private static String src = "test java security.";
	
	/**
	 * JDK Base64
	 */
	public static void JDKBase64() {
		BASE64Encoder encoder = new BASE64Encoder();
		String encode = encoder.encode(src.getBytes());
		System.out.println("jdk encode: " + encode);
		
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] decodeBytes = decoder.decodeBuffer(encode);
			System.out.println("jdk decode: " + new String(decodeBytes));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * commons codec base64
	 */
	public static void commonsCodecBase64() {
		byte[] encodeBytes = Base64.encodeBase64(src.getBytes());
		System.out.println("commons codec encode: " + new String(encodeBytes));
		
		byte[] decodeBytes = Base64.decodeBase64(encodeBytes);
		System.out.println("commons codec decode: " + new String(decodeBytes));
	}
	
	/**
	 * bouncy castle base64
	 */
	public static void bouncyCastleBase64() {
		byte[] encodeBytes = org.bouncycastle.util.encoders.Base64.encode(src.getBytes());
		System.out.println("bouncy castle encode: " + new String(encodeBytes));
		byte[] decodeBytes = org.bouncycastle.util.encoders.Base64.decode(encodeBytes);
		System.out.println("bouncy castle decode: " + new String(decodeBytes));
	}

	public static void main(String[] args) {
		JDKBase64();
		commonsCodecBase64();
		bouncyCastleBase64();
	}

}
