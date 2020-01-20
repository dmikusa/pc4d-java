#!/bin/sh
set -e

CMD=$(java -jar encrypt/target/pc4d-java-encrypt.jar "Hello World!" | grep "Decrypt with:" | cut -d ' ' -f 3-99)
OK=$(eval $CMD | grep "Message:")
if [ "$OK" != "Message: Hello World!" ]; then
    echo "Encrypt/Decrypt failed :("
    exit -1
fi

CMD=$(java -jar pkencrypt/target/pc4d-java-pkencrypt.jar "Hello World!" | grep "Decrypt with:" | cut -d ' ' -f 3-99)
OK=$(eval $CMD | grep "Message:")
if [ "$OK" != "Message: Hello World!" ]; then
    echo "PkEncrypt/PkDecrypt failed :("
    exit -1
fi

CMD=$(java -jar auth/target/pc4d-java-auth.jar "Hello World!" | grep "Verify with:" | cut -d ' ' -f 3-99)
OK=$(eval $CMD)
if [ "$OK" != "Verified" ]; then
    echo "Auth/Verify failed :("
    exit -1
fi

CMD=$(java -jar pksign/target/pc4d-java-pksign.jar "Hello World!" | grep "Verify with:" | cut -d ' ' -f 3-99)
OK=$(eval $CMD)
if [ "$OK" != "Verified" ]; then
    echo "PkSign/PkVerify failed :("
    exit -1
fi

HASH=$(java -jar hash/target/pc4d-java-hash.jar "Hello World!")
if [ "$HASH" != "Hash: 35259d2903a1303d3115c669e2008510fc79acb50679b727ccb567cc3f786de3553052e47d4dd715cc705ce212a92908f4df9e653fa3653e8a7855724d366137" ]; then
    echo "Hash failed :("
    exit -1
fi

echo "Success"