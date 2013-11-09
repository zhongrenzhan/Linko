package cn.com.unifront.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Encode {
	public static String md5Encoding(String pwd) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] encoed = digest.digest(pwd.getBytes());
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < encoed.length; i++) {
				String s = Integer.toHexString(encoed[i]);
				if (s.length() < 2) {
					buffer.append("0" + s);
				} else {
					buffer.append(s);
				}
			}
			return buffer.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
