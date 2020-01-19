package com.mikusa.pc4djava;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.digests.SHAKEDigest;

public class Hash {

	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("Usage: java -jar hash/target/pc4d-java-hash.jar message");
			System.out.println("    message: message to hash");
			System.exit(1);
		}

		// get message
        byte[] message = args[0].getBytes("utf-8");
        
        // Use Bouncy Castle's implementation, as there doesn't seem to be SHAKE in Java natively
		SHAKEDigest digest = new SHAKEDigest(256);
		digest.update(message, 0, message.length);
		byte[] hashbytes = new byte[64];  // we want 64-bits of output
		digest.doFinal(hashbytes, 0, 64);
		
		System.out.println(String.format("Hash: %s", Hex.encodeHexString(hashbytes)));
	}
}
