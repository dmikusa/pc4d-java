package com.mikusa.pc4djava;

import java.util.Base64;

import org.apache.commons.codec.binary.Hex;

import com.goterl.lazycode.lazysodium.SodiumJava;
import com.goterl.lazycode.lazysodium.interfaces.SecretBox;

public class Encrypt {

	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("Usage: java -jar encrypt/target/pc4d-java-encrypt.jar plaintext");
			System.out.println("    plaintext: unencrypted data");
			System.exit(1);
		}

		SodiumJava sodium = new SodiumJava();
        
        // generate a key
        byte[] key = new byte[SecretBox.KEYBYTES];
        sodium.crypto_secretbox_keygen(key);

        // generate a nonce
        byte[] nonce = new byte[SecretBox.NONCEBYTES];
        sodium.randombytes_buf(nonce, SecretBox.NONCEBYTES);

        // get message
        byte[] message = args[0].getBytes("utf-8");

        // encrypt
        byte[] cipherText = new byte[SecretBox.MACBYTES + message.length];
        int res = sodium.crypto_secretbox_easy(cipherText, message, message.length, nonce, key);
        if (res != 0) {
            System.err.println("Encryption failed :(");
            System.exit(1);
        }

        // combine the nonce & cipher text
        byte[] box = new byte[nonce.length + cipherText.length];
        System.arraycopy(nonce, 0, box, 0, nonce.length);
        System.arraycopy(cipherText, 0, box, nonce.length, cipherText.length);

        System.out.println(String.format("Nonce: %s", Hex.encodeHexString(nonce)));
        System.out.println(String.format("Key: %s", Hex.encodeHexString(key)));
        System.out.println(String.format("Box: %s", Base64.getEncoder().encodeToString(box)));
        System.out.println();
        System.out.println(String.format("Decrypt with: java -jar decrypt/target/pc4d-java-decrypt.jar %s %s", Hex.encodeHexString(key), Base64.getEncoder().encodeToString(box)));
        System.out.println();
	}
}
