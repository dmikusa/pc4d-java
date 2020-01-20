# Practical Cryptography for Developers (in Java)

This repository contains examples of how to use various Java libraries to perform cryptographic operations safely and with purpose.

The primary purposes of cryptography are:

- Confidentiality
- Integrity
- Non-repudiation
- Authentication

The examples in this repository depend on the [LazySodium Java](https://github.com/terl/lazysodium-java) package for everything except hashing. The hashing is done by [Bouncy Castle](https://www.bouncycastle.org/)'s Java Cryptography Extension (JCE) and the Java Cryptography Architecture (JCA) provider.

This is inspired by:

 - [aeden/pc4d-go](https://github.com/aeden/pc4d-go)
 - [aeden/pc4d-ruby](https://github.com/aeden/pc4d-ruby)

See also:

 - [dmikusa/pc4d-python](https://github.com/dmikusa/pc4d-python)

## Examples

To run the examples...

- Install Java 11+.
- Install libsodium. Your package manager can likely do this for you. For example, `brew install libsodium` or `apt install libsodium23`.
- Run `./mvnw package` from the project directory to install required Java dependencies & build.

You should now be able to run the examples, for example `java -jar encrypt/target/pc4d-java-encrypt.jar`, or try running `./test.sh` which runs all four examples and validates they are working correctly.

### Confidentiality

Symmetric examples: `java -jar encrypt/target/pc4d-java-encrypt.jar` & `java -jar decrypt/target/pc4d-java-decrypt.jar`

Asymmetric examples: `java -jar pkencrypt/target/pc4d-java-pkencrypt.jar` & `java -jar pkdecrypt/target/pc4d-java-pkdecrypt.jar`

Run either example with the appropriate arguments and the final output should show the command required to decrypt.

### Integrity

Hash example: `java -jar hash/target/pc4d-java-hash.jar`

Run `java -jar hash/target/pc4d-java-hash.jary` with the same input argument and you should get the same output argument every time.

Asymmetric example: `java -jar pksign/target/pc4d-java-pksign.jar` & `java -jar pkverify/target/pc4d-java-pkverify.jar`

Run `java -jar pksign/target/pc4d-java-pksign.jar` with the appropriate arguments and the final output should show the command required to verify.

### Non-repudiation

Asymmetric examples: `java -jar pksign/target/pc4d-java-pksign.jar` & `java -jar pkverify/target/pc4d-java-pkverify.jar`

Run `java -jar pksign/target/pc4d-java-pksign.jar` with the appropriate arguments and the final output should show the command required to verify.

### Authentication

Symmetric example: `java -jar auth/target/pc4d-java-auth.jar` & `java -jar verify/target/pc4d-java-verify.jar`

Run `java -jar auth/target/pc4d-java-auth.jar` with the appropriate arguments and the final output should show the command required to verify.
