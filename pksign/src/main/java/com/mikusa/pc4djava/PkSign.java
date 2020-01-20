package com.mikusa.pc4djava;

import java.util.Base64;

import org.apache.commons.codec.binary.Hex;

import com.goterl.lazycode.lazysodium.SodiumJava;
import com.goterl.lazycode.lazysodium.interfaces.Box;
import com.goterl.lazycode.lazysodium.interfaces.Sign;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

public class PkSign {

	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("Usage: java -jar pkverify/target/pc4d-java-pkverify.jar plaintext");
			System.out.println("    plaintext: message to sign");
			System.exit(1);
		}

		SodiumJava sodium = new SodiumJava();
        
        // generate a signer key pair
        byte[] signerPublicKey = new byte[Sign.PUBLICKEYBYTES];
        byte[] signerPrivateKey = new byte[Sign.SECRETKEYBYTES];
        sodium.crypto_sign_keypair(signerPublicKey, signerPrivateKey);

        // get message
        byte[] message = args[0].getBytes("utf-8");

        // sign
        byte[] signedMessage = new byte[Sign.BYTES + message.length];
        int res = sodium.crypto_sign(signedMessage,
        		(new PointerByReference(Pointer.NULL)).getPointer(), // formality, we don't need the length in Java
        		message, 
        		(long) message.length, 
        		signerPrivateKey);
        if (res != 0) {
            System.err.println("Signing failed :( ");
            System.exit(1);
        }
        
        System.out.println(String.format("Signer public key: %s", Hex.encodeHexString(signerPublicKey)));
        System.out.println(String.format("Signer private key: %s", Hex.encodeHexString(signerPrivateKey)));
        System.out.println(String.format("Signature: %s", Base64.getEncoder().encodeToString(signedMessage)));
        System.out.println();
        System.out.println(String.format("Verify with: java -jar pkverify/target/pc4d-java-pkverify.jar %s %s",
        		Hex.encodeHexString(signerPublicKey),
        		Base64.getEncoder().encodeToString(signedMessage)));
        System.out.println();
	}
}
