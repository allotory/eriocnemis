package com.security.dh;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

import org.apache.commons.codec.binary.Hex;

public class TestDH {

	private static String src = "test java security.";
	
	public static void jdkDH() {
		
		try {
			// 发送方初始化自己的密钥对，并且把公钥发送给接收方
			KeyPairGenerator senderKeyPairGenerator = KeyPairGenerator.getInstance("DH");
			senderKeyPairGenerator.initialize(512);
			KeyPair senderKeyPair = senderKeyPairGenerator.generateKeyPair();
			// 公钥是要发送给接收方的，此处同一类内省略该操作
			PublicKey senderPublicKey = senderKeyPair.getPublic();


			// 接收方用发送方的公钥初始化自己的密钥对
			// 用发送方的公钥产生算法参数
			DHParameterSpec dhParameterSpec = ((DHPublicKey) senderPublicKey).getParams();
			KeyPairGenerator receiverKeyPairGenerator = KeyPairGenerator.getInstance("DH");
			// 初始化接收方的KeyPairGenerator
			receiverKeyPairGenerator.initialize(dhParameterSpec);
			// 生产接收方的密钥对
			KeyPair receiverKeyPair = receiverKeyPairGenerator.generateKeyPair();


			// DES密钥构建
			KeyAgreement keyAgreement = KeyAgreement.getInstance("DH");
			// 接收方根据自己的私钥和发送方的公钥创建DES密钥
			// 接收方的私钥
			keyAgreement.init(receiverKeyPair.getPrivate());
			// 发送方的公钥
			keyAgreement.doPhase(senderKeyPair.getPublic(), true);
			SecretKey receiverDesKey = keyAgreement.generateSecret("DES");


			// 发送方根据自己的私钥和接收方的公钥创建DES密钥
			keyAgreement.init(senderKeyPair.getPrivate());
			keyAgreement.doPhase(receiverKeyPair.getPublic(), true);
			SecretKey senderDesKey = keyAgreement.generateSecret("DES");

			// 判断两方得到的DES密钥是不是相等的
			if (Objects.equals(senderDesKey, receiverDesKey)) {
				System.out.println("双方密钥相同");
			}

			//加密
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, senderDesKey);
			byte[] result = cipher.doFinal(src.getBytes());
			System.out.println("JDK DH ENCRYPT:" + Hex.encodeHexString(result));

			//解密
			cipher.init(Cipher.DECRYPT_MODE, receiverDesKey);
			result = cipher.doFinal(result);
			System.out.println("JDK DH DECRYPT:" + new String(result));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] arsg) {
		jdkDH();
	}
}
