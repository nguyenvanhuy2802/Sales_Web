package org.example.security;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class ElectronicSignature {
    static KeyPairGenerator generator;
    static SecureRandom secureRandom;
    static Signature signature;

    static KeyPair keyPairDSA;
    static PublicKey publicKeyDSA;
    static PrivateKey privateKeyDSA;

    public static void genDSAKey() throws NoSuchAlgorithmException, NoSuchProviderException {
        generator = KeyPairGenerator.getInstance("DSA", "SUN");
        secureRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");
        generator.initialize(1024, secureRandom);
        keyPairDSA = generator.generateKeyPair();
        signature = Signature.getInstance("DSA", "SUN");

        publicKeyDSA = keyPairDSA.getPublic();
        privateKeyDSA = keyPairDSA.getPrivate();
    }

    public static String sign(String mes, String privateKeyBase64) throws InvalidKeyException, SignatureException,
            NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyBase64);
        KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        signature = Signature.getInstance("DSA", "SUN");
        byte[] data = mes.getBytes();
        signature.initSign(privateKey);
        signature.update(data);
        byte[] sign = signature.sign();
        return Base64.getEncoder().encodeToString(sign);
    }



    public static boolean verify(String mes, String sign, String publicKeyBase64) throws InvalidKeyException,
            SignatureException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);
        KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        signature = Signature.getInstance("DSA", "SUN");
        signature.initVerify(publicKey);
        byte[] data = mes.getBytes();
        byte[] signValue = Base64.getDecoder().decode(sign);
        signature.update(data);
        return signature.verify(signValue);
    }


    public static PublicKey getPublicKeyDSA() {
        return publicKeyDSA;
    }

    public static PrivateKey getPrivateKeyDSA() {
        return privateKeyDSA;
    }


    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException,
            InvalidKeyException, SignatureException, InvalidKeySpecException {
//		genDSAKey();
//
//		String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKeyDSA.getEncoded());
//		String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKeyDSA.getEncoded());
//
//		System.out.println("Public key: " + publicKeyBase64);
//		System.out.println("Private key: " + privateKeyBase64);

		String a = sign("Có cái nịt",
				"MIIBTAIBADCCASwGByqGSM44BAEwggEfAoGBAP1/U4EddRIpUt9KnC7s5Of2EbdSPO9EAMMeP4C2USZpRV1AIlH7WT2NWPq/xfW6MPbLm1Vs14E7gB00b/JmYLdrmVClpJ+f6AR7ECLCT7up1/63xhv4O1fnxqimFQ8E+4P208UewwI1VBNaFpEy9nXzrith1yrv8iIDGZ3RSAHHAhUAl2BQjxUjC8yykrmCouuEC/BYHPUCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoEFwIVAIzMk910fujFW/5IfcFeGUNcmkkZ");
		System.out.println(a);
        System.out.println(verify("Có cái nịt", "MCwCFE7n1S6zK1sLE1fryw1BfQVyMkLZAhRl9n0HBaQFgx7xGTffpX7FJnT40w==",
                "MIIBtzCCASwGByqGSM44BAEwggEfAoGBAP1/U4EddRIpUt9KnC7s5Of2EbdSPO9EAMMeP4C2USZpRV1AIlH7WT2NWPq/xfW6MPbLm1Vs14E7gB00b/JmYLdrmVClpJ+f6AR7ECLCT7up1/63xhv4O1fnxqimFQ8E+4P208UewwI1VBNaFpEy9nXzrith1yrv8iIDGZ3RSAHHAhUAl2BQjxUjC8yykrmCouuEC/BYHPUCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoDgYQAAoGATEwUJw3676+74yPmJBmvYKGlVg1InzAcOxxNPo2PsaJd1rKIpGezW5lZuV0ieh8yITf89/GhtDp4mCpJcplUW4T4Xg48NS0tRBSKnaoT53FI7Jx7JmoeuzrJZH01WR1bmErnjPEO5I6j4CPpLblpJyVWqY5Cp9E/QR5zpAIe3gI="));
    }
}
