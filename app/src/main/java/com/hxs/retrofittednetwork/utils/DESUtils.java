package com.hxs.retrofittednetwork.utils;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;


public class DESUtils {


	public final static String DES_KEY = "Tj>$GA|I(#L0=/&9Xpqbvwa?";

	public static String encrypt(String message) throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		DESKeySpec desKeySpec = new DESKeySpec(DES_KEY.getBytes("UTF-8"));

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec("33878402".getBytes("UTF-8"));
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

		return encodeBase64(cipher.doFinal(message.getBytes("UTF-8")));
	}

	public static String decrypt(String message) {
		try {

			byte[] bytesrc = decodeBase64(message);//convertHexString(message);
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			DESKeySpec desKeySpec = new DESKeySpec(DES_KEY.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			IvParameterSpec iv = new IvParameterSpec( "33878402".getBytes("UTF-8"));

			cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

			byte[] retByte = cipher.doFinal(bytesrc);
			return new String(retByte);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static byte[] convertHexString(String ss) {
		byte digest[] = new byte[ss.length() / 2];
		for (int i = 0; i < digest.length; i++) {
			String byteString = ss.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}

		return digest;
	}

	public static String toHexString(byte b[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2)
				plainText = "0" + plainText;
			hexString.append(plainText);
		}

		return hexString.toString();
	}


	public static String encodeBase64(byte[] b) {
		return Base64.encodeToString(b, Base64.DEFAULT);
	}

	public static byte[] decodeBase64(String base64String) {
		return Base64.decode(base64String, Base64.DEFAULT);
	}

	public static void main(String[] args) throws Exception {
		String s = "YtRxGEloL3nJ2HJVGVN8nY2o2qDWwcFbmegQxhoUuNlPK3NR9vKJlN9ftT59 620xS5LijAf4DyH0wYiZO0kxqugLtvq1g1lWnmU/49xaQElo5o5i/cayQq9c oR5C0XwnoPi1/rR0k7n3R9CkmNM5sVQkVHvw/xm8P8boKxIFqPJzoCK/Q0t9 EO943EMa7REPLMxQoJkjNJgEUnjFFdLnIEOg703/wxO6WjYiO45DXyEV0Zhb Adr0ngKgjbmNHcS6PW89C9kyPVeB+Rs4ZYBOGbhV/950pxGGGAo2dQk5Q+bN 5CvEI+B9rlL3JQ28K5IfSvZnRJAs+galSNyplCIqgFKX3kQDgFtQJ6xs5nMw lSSYhhuiCSNewavPruW3vytchC+dmc3cZ4bsL/hTjAE8OjZhUST462bhWKct z2jYvvKCJZg2EvUVr73pjx0Hei882Rp54Rufig2IBXZ5DQrGiMBAGKY7KEA4 YWoVh7fO0wPPISTGo9ZJH+zt6FnhIZsvv3voh4amMHY=";
		String ec = DESUtils.decrypt(s);
		System.out.println(ec);
	}

}
