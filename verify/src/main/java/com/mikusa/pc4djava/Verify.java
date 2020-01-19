package com.mikusa.pc4djava;

import org.apache.commons.codec.binary.Hex;

import com.goterl.lazycode.lazysodium.SodiumJava;

public class Verify {

	public static void main(String[] args) throws Exception {
		if (args.length != 3) {
			System.out.println("Usage: java -jar verify/target/pc4d-java-verify.jar key digest message");
			System.out.println("    key: the key in hex format");
			System.out.println("    digest: the digest in hex format");
			System.out.println("    message: unencrypted data");
			System.exit(1);
		}

		SodiumJava sodium = new SodiumJava();
        
		byte[] key = Hex.decodeHex(args[0]);
		byte[] digest = Hex.decodeHex(args[1]);
		byte[] message = args[2].getBytes("utf-8");
		
		int res = sodium.crypto_auth_verify(digest, message, message.length, key);
		if (res == 0) {
			System.out.println("Verified");
		} else {
			System.out.println("NOT Verified");
		}
	}
}
