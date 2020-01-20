package com.mikusa.pc4djava;

import java.util.Base64;

import org.apache.commons.codec.binary.Hex;

import com.goterl.lazycode.lazysodium.SodiumJava;
import com.goterl.lazycode.lazysodium.interfaces.Sign;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

public class PkVerify {

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.out.println("Usage: java -jar pksign/target/pc4d-java-pksign.jar signerPublicKey signedMessage");
			System.out.println("    signerPublicKey: hex-encoded public key");
			System.out.println("    signedMessage: base64-encoded signed message");
			System.exit(1);
		}

		SodiumJava sodium = new SodiumJava();
        
        // load the sender public key
        byte[] signerPublicKey = Hex.decodeHex(args[0]);
        
        // load the signed message
        byte[] signedMessage = Base64.getDecoder().decode(args[1]);
        
        // verify
        byte[] message = new byte[signedMessage.length - Sign.BYTES];
		int res = sodium.crypto_sign_open(message,
				(new PointerByReference(Pointer.NULL)).getPointer(),
				signedMessage,
				signedMessage.length,
				signerPublicKey);
		if (res == 0) {
			System.out.println("Verified");
		} else {
			System.out.println("NOT Verified");
		}
	}
}
