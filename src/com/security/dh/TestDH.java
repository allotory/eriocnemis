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
			// ���ͷ���ʼ���Լ�����Կ�ԣ����Ұѹ�Կ���͸����շ�
			KeyPairGenerator senderKeyPairGenerator = KeyPairGenerator.getInstance("DH");
			senderKeyPairGenerator.initialize(512);
			KeyPair senderKeyPair = senderKeyPairGenerator.generateKeyPair();
			// ��Կ��Ҫ���͸����շ��ģ��˴�ͬһ����ʡ�Ըò���
			PublicKey senderPublicKey = senderKeyPair.getPublic();


			// ���շ��÷��ͷ��Ĺ�Կ��ʼ���Լ�����Կ��
			// �÷��ͷ��Ĺ�Կ�����㷨����
			DHParameterSpec dhParameterSpec = ((DHPublicKey) senderPublicKey).getParams();
			KeyPairGenerator receiverKeyPairGenerator = KeyPairGenerator.getInstance("DH");
			// ��ʼ�����շ���KeyPairGenerator
			receiverKeyPairGenerator.initialize(dhParameterSpec);
			// �������շ�����Կ��
			KeyPair receiverKeyPair = receiverKeyPairGenerator.generateKeyPair();


			// DES��Կ����
			KeyAgreement keyAgreement = KeyAgreement.getInstance("DH");
			// ���շ������Լ���˽Կ�ͷ��ͷ��Ĺ�Կ����DES��Կ
			// ���շ���˽Կ
			keyAgreement.init(receiverKeyPair.getPrivate());
			// ���ͷ��Ĺ�Կ
			keyAgreement.doPhase(senderKeyPair.getPublic(), true);
			SecretKey receiverDesKey = keyAgreement.generateSecret("DES");


			// ���ͷ������Լ���˽Կ�ͽ��շ��Ĺ�Կ����DES��Կ
			keyAgreement.init(senderKeyPair.getPrivate());
			keyAgreement.doPhase(receiverKeyPair.getPublic(), true);
			SecretKey senderDesKey = keyAgreement.generateSecret("DES");

			// �ж������õ���DES��Կ�ǲ�����ȵ�
			if (Objects.equals(senderDesKey, receiverDesKey)) {
				System.out.println("˫����Կ��ͬ");
			}

			//����
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, senderDesKey);
			byte[] result = cipher.doFinal(src.getBytes());
			System.out.println("JDK DH ENCRYPT:" + Hex.encodeHexString(result));

			//����
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
