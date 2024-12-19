package org.example.security;

import java.io.*;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
private final static String ALGORITHM = "SHA-256";
    public static String hash(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(ALGORITHM);
        byte[] bytes = data.getBytes();
        byte[] digest = md.digest(bytes);
        BigInteger big = new BigInteger(1, digest);
        return big.toString(16);
    }


    public static String hashFile(String src) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance(ALGORITHM);
        File file = new File(src);
        if (!file.exists()) {
            return null;
        }
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        DigestInputStream di = new DigestInputStream(in, md);
        byte[] buff = new byte[1024];
        int read;
        do {
            read = di.read(buff);
        } while (read != -1);
        BigInteger re = new BigInteger(1, di.getMessageDigest().digest());
        return re.toString(16);

    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String textA = "1, Nguyen Van A, địa chỉ giao, 2024-12-3, 1000000, danh sách sản phẩm(1, product1, 1, 400000; 2, product2, 2, 300000)";
        String textB = "1, Nguyen Van A, địa chỉ giao, 2024-12-3, 1000000, danh sách sản phẩm(1, product1, 1, 400000; 2, product2, 2, 300000)";

        String a = Hash.hash(textA);
        String b = Hash.hash(textB);

        System.out.println("Giá trị hash là: " + a);
        System.out.println("Giá trị hash là: " + b);
        System.out.println(a.equals(b));

    }
}
