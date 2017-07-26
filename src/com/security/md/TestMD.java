package com.security.md;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.MD4Digest;

public class TestMD {
	
	private static String src = "test java security.";
	
	/**
	 * JDK MD5
	 */
	public static void JdkMD5() {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] md5Bytes = md.digest(src.getBytes());
			System.out.print("JDK MD5: " );
			System.out.println(Hex.encodeHex(md5Bytes));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * JDK MD2
	 */
	public static void JdkMD2() {
		try {
			MessageDigest md = MessageDigest.getInstance("MD2");
			byte[] md5Bytes = md.digest(src.getBytes());
			System.out.print("JDK MD2: " );
			System.out.println(Hex.encodeHex(md5Bytes));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * bouncy castle md4
	 */
	public static void bouncyCastleMD4() {
		Digest dcMD4 = new MD4Digest();
		dcMD4.update(src.getBytes(), 0, src.getBytes().length);
		byte[] md4Bytes = new byte[dcMD4.getDigestSize()];
		dcMD4.doFinal(md4Bytes, 0);
		System.out.print("Bouncy Castle MD4:");
		System.out.println(org.bouncycastle.util.encoders.Hex.toHexString(md4Bytes));
	}

	public static void main(String[] args) {
		JdkMD5();
		JdkMD2();
		bouncyCastleMD4();
	}

}
