package com.security.sha;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

public class TestSHA {
	
	private static String src = "test java security.";

	public static void JdkSHA1() {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			byte[] shaBytes = md.digest(src.getBytes());
			System.out.print("JDK SHA-1: ");
			System.out.println(Hex.encodeHexString(shaBytes));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		JdkSHA1();
	}
}
