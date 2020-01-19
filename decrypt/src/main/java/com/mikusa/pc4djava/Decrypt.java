package com.mikusa.pc4djava;

import java.util.Arrays;
import java.util.Base64;

import org.apache.commons.codec.binary.Hex;

import com.goterl.lazycode.lazysodium.SodiumJava;
import com.goterl.lazycode.lazysodium.interfaces.SecretBox;

public class Decrypt {

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.out.println("Usage: java -jar decrypt/target/pc4d-java-decrypt.jar key box");
			System.out.println("    key: hex-encoded 32 byte key");
			System.out.println("    box: base64 encoded encrypted data");
			System.exit(1);
		}

		SodiumJava sodium = new SodiumJava();

		byte[] key = Hex.decodeHex(args[0]);
		byte[] box = Base64.getDecoder().decode(args[1]);

		byte[] nonce = Arrays.copyOf(box, SecretBox.NONCEBYTES);

		byte[] cipherText = new byte[box.length - SecretBox.NONCEBYTES];
		System.arraycopy(box, nonce.length, cipherText, 0, cipherText.length);

		byte[] message = new byte[cipherText.length - SecretBox.MACBYTES];

		int res = sodium.crypto_secretbox_open_easy(message, cipherText, cipherText.length, nonce, key);
		if (res != 0) {
			System.err.println("Decryption failed :(");
			System.exit(1);
		}

		System.out.println(String.format("Nonce: %s", Hex.encodeHexString(nonce)));
		System.out.println(String.format("Message: %s", new String(message, "utf-8")));
	}

}
