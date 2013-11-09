package cn.com.unifront.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Decode {
	public static String decode(String encoded) {
		try {

			MessageDigest digest = MessageDigest.getInstance("MD5");

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
