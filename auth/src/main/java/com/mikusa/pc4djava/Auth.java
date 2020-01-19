package com.mikusa.pc4djava;

import java.util.Base64;

import org.apache.commons.codec.binary.Hex;

import com.goterl.lazycode.lazysodium.SodiumJava;

public class Auth {

	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("Usage: java -jar auth/target/pc4d-java-auth.jar plaintext");
			System.out.println("    plaintext: unencrypted data");
			System.exit(1);
		}

		SodiumJava sodium = new SodiumJava();
        
        // generate a key
        byte[] key = new byte[com.goterl.lazycode.lazysodium.interfaces.Auth.BYTES];
        sodium.crypto_auth_keygen(key);

        // get message
        byte[] message = args[0].getBytes("utf-8");

        // create digest
        byte[] digest = new byte[com.goterl.lazycode.lazysodium.interfaces.Auth.KEYBYTES];
        sodium.crypto_auth(digest, message, message.length, key);

        System.out.println(String.format("Key: %s", Hex.encodeHexString(key)));
        System.out.println(String.format("Digest: %s", Base64.getEncoder().encodeToString(digest)));
        System.out.println();
        System.out.println(String.format("Verify with: java -jar verify/target/pc4d-java-verify.jar %s %s \"%s\"", Hex.encodeHexString(key), Hex.encodeHexString(digest), new String(message, "utf-8")));
        System.out.println();
	}
}
