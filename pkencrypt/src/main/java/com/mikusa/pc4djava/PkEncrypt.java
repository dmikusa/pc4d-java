package com.mikusa.pc4djava;

import java.util.Base64;

import org.apache.commons.codec.binary.Hex;

import com.goterl.lazycode.lazysodium.SodiumJava;
import com.goterl.lazycode.lazysodium.interfaces.Box;

public class PkEncrypt {

	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("Usage: java -jar pkencrypt/target/pc4d-java-pkencrypt.jar plaintext");
			System.out.println("    plaintext: unencrypted data");
			System.exit(1);
		}

		SodiumJava sodium = new SodiumJava();
        
        // generate a sender key pair
        byte[] senderPublicKey = new byte[Box.PUBLICKEYBYTES];
        byte[] senderPrivateKey = new byte[Box.SECRETKEYBYTES];
        sodium.crypto_box_keypair(senderPublicKey, senderPrivateKey);

        // generate a recipient key pair
        byte[] recipientPublicKey = new byte[Box.PUBLICKEYBYTES];
        byte[] recipientPrivateKey = new byte[Box.SECRETKEYBYTES];
        sodium.crypto_box_keypair(recipientPublicKey, recipientPrivateKey);
        
        // generate a nonce
        byte[] nonce = new byte[Box.NONCEBYTES];
        sodium.randombytes_buf(nonce, Box.NONCEBYTES);

        // get message
        byte[] message = args[0].getBytes("utf-8");

        // encrypt
        byte[] cipherText = new byte[Box.MACBYTES + message.length];
        int res = sodium.crypto_box_easy(cipherText, message, message.length, nonce, recipientPublicKey, senderPrivateKey);
        if (res != 0) {
            System.err.println("Encryption failed :( ");
            System.exit(1);
        }

        // combine the nonce & cipher text
        byte[] box = new byte[nonce.length + cipherText.length];
        System.arraycopy(nonce, 0, box, 0, nonce.length);
        System.arraycopy(cipherText, 0, box, nonce.length, cipherText.length);

        System.out.println(String.format("Sender public key: %s", Base64.getEncoder().encodeToString(senderPublicKey)));
        System.out.println(String.format("Sender private key: %s", Base64.getEncoder().encodeToString(senderPrivateKey)));
        System.out.println(String.format("Recipient public key: %s", Base64.getEncoder().encodeToString(recipientPublicKey)));
        System.out.println(String.format("Recipient private key: %s", Base64.getEncoder().encodeToString(recipientPrivateKey)));
        System.out.println(String.format("Nonce: %s", Hex.encodeHexString(nonce)));
        System.out.println(String.format("Box: %s", Base64.getEncoder().encodeToString(box)));
        System.out.println();
        System.out.println(String.format("Decrypt with: java -jar pkdecrypt/target/pc4d-java-pkdecrypt.jar %s %s %s",
        		Base64.getEncoder().encodeToString(senderPublicKey),
        		Base64.getEncoder().encodeToString(recipientPrivateKey),
        		Base64.getEncoder().encodeToString(box)));
        System.out.println();
	}
}
