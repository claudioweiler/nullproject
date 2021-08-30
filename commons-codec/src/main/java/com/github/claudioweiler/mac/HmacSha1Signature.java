package com.github.claudioweiler.mac;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

public class HmacSha1Signature {

	public static void main(String[] args) {
		String hmac = new HmacUtils(HmacAlgorithms.HMAC_SHA_1, "key").hmacHex("data");
		
		System.out.println(hmac);
		assert hmac.equals("104152c5bfdca07bc633eebd46199f0255c9f49d");
	}
}