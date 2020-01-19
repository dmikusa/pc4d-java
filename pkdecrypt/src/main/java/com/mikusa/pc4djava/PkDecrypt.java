package com.mikusa.pc4djava;

import java.util.Arrays;
import java.util.Base64;

import org.apache.commons.codec.binary.Hex;

import com.goterl.lazycode.lazysodium.SodiumJava;
import com.goterl.lazycode.lazysodium.interfaces.Box;
import com.goterl.lazycode.lazysodium.interfaces.SecretBox;

public class PkDecrypt {

	public static void main(String[] args) throws Exception {
		if (args.length != 3) {
			System.out.println("Usage: java -jar pkdecrypt/target/pc4d-java-pkdecrypt.jar senderPublicKey recipientPrivateKey box");
			System.out.println("    senderPublicKey: base-64 encoded public key");
			System.out.println("    recipientPrivateKey: base64-encoded private key");
			System.out.println("    box: base64 encoded encrypted data");
			System.exit(1);
		}

		SodiumJava sodium = new SodiumJava();
        
        // load the sender public key
        byte[] senderPublicKey = Base64.getDecoder().decode(args[0]);
        
        // load the recipient private key
        byte[] recipientPrivateKey = Base64.getDecoder().decode(args[1]);
        
        // load the box
        byte[] box = Base64.getDecoder().decode(args[2]);
        
        // pull nonce out of the box
        byte[] nonce = Arrays.copyOf(box, SecretBox.NONCEBYTES);

        // pull out the cipher text
        byte[] cipherText = new byte[box.length - Box.NONCEBYTES];
		System.arraycopy(box, nonce.length, cipherText, 0, cipherText.length);

		// make space to store the message
		byte[] message = new byte[cipherText.length - SecretBox.MACBYTES];
		
        // decrypt
		int res = sodium.crypto_box_open_easy(message, cipherText, cipherText.length, nonce, senderPublicKey, recipientPrivateKey);
		if (res == 0) {
			System.out.println(String.format("Nonce: %s", Hex.encodeHexString(nonce)));
			System.out.println(String.format("Message: %s", new String(message, "utf-8")));
		} else {
			System.err.println("Decryption failed :(");
			System.exit(1);
		}
	}
}
