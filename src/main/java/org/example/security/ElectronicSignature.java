package org.example.security;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
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

    public static String sign(String mes) throws InvalidKeyException, SignatureException {
        byte[] data = mes.getBytes();
        signature.initSign(privateKeyDSA);
        signature.update(data);
        byte[] sign = signature.sign();
        return Base64.getEncoder().encodeToString(sign);
    }

    public static String signFile(String src) throws InvalidKeyException, SignatureException, IOException {
        byte[] data = src.getBytes();
        signature.initSign(privateKeyDSA);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
        byte[] buff = new byte[1024];
        int read;
        while ((read = bis.read(buff)) != -1) {
            signature.update(buff, 0, read);
        }
        bis.close();
        byte[] sign = signature.sign();
        return Base64.getEncoder().encodeToString(sign);
    }

    public static boolean verify(String mes, String sign) throws InvalidKeyException, SignatureException {
        signature.initVerify(publicKeyDSA);
        byte[] data = mes.getBytes();
        byte[] signValue = Base64.getDecoder().decode(sign);
        signature.update(data);
        return signature.verify(signValue);
    }

    public static boolean verifyFile(String src, String sign) throws InvalidKeyException, SignatureException, IOException {
        signature.initVerify(publicKeyDSA);
        byte[] data = src.getBytes();
        byte[] signValue = Base64.getDecoder().decode(sign);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
        byte[] buff = new byte[1024];
        int read;
        while((read = bis.read(buff)) != -1) {
            signature.update(buff, 0, read);
        }
        bis.close();
        return signature.verify(signValue);
    }

    public static PublicKey getPublicKeyDSA() {
        return publicKeyDSA;
    }

    public static PrivateKey getPrivateKeyDSA() {
        return privateKeyDSA;
    }

    public static int saveDSAKeysToFileWithChooser() {
        // Kiểm tra xem publicKey và privateKey đã được tạo chưa
        if (publicKeyDSA == null || privateKeyDSA == null) {
            System.err.println("Chưa tạo Public Key và Private Key. Không thể lưu file.");
            return 1;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file khóa");

        // Đặt tên file mặc định là "key.txt"
        fileChooser.setSelectedFile(new java.io.File("dsaKey.txt"));

        // Mở hộp thoại Save
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // Lấy đường dẫn file từ người dùng
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
                // Encode các key
                String encodedPublicKey = Base64.getEncoder().encodeToString(publicKeyDSA.getEncoded());
                String encodedPrivateKey = Base64.getEncoder().encodeToString(privateKeyDSA.getEncoded());

                // Ghi vào file với định dạng: PUBLIC_KEY=...PRIVATE_KEY=...
                String content = "PUBLIC_KEY=" + encodedPublicKey + "\nPRIVATE_KEY=" + encodedPrivateKey;
                bos.write(content.getBytes(StandardCharsets.UTF_8));
                System.out.println("Lưu thành công các khóa vào file: " + filePath);
                return 2; // Thành công
            } catch (Exception e) {
                e.printStackTrace();
                return 3; // Có lỗi xảy ra
            }
        } else {
            System.out.println("Hủy lưu file.");
            return 3; // Người dùng hủy
        }
    }

    // Hàm đọc cả Public Key và Private Key từ file được chọn
    public static boolean readDSAKeysFromFileWithChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file chứa khóa");

        // Mở hộp thoại Open
        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
                String line;
                String publicKeyStr = null;
                String privateKeyStr = null;

                // Đọc từng dòng và phân tích nội dung
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("PUBLIC_KEY=")) {
                        publicKeyStr = line.replace("PUBLIC_KEY=", "");
                    } else if (line.startsWith("PRIVATE_KEY=")) {
                        privateKeyStr = line.replace("PRIVATE_KEY=", "");
                    }
                }

                // Kiểm tra xem cả Public Key và Private Key có tồn tại không
                if (publicKeyStr != null && privateKeyStr != null) {
                    // Decode và khôi phục Public Key
                    byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyStr);
                    X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(publicKeyBytes);
                    KeyFactory keyFactory = KeyFactory.getInstance("DSA");
                    publicKeyDSA = keyFactory.generatePublic(pubKeySpec);

                    // Decode và khôi phục Private Key
                    byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyStr);
                    PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
                    privateKeyDSA = keyFactory.generatePrivate(privKeySpec);

                    System.out.println("Đọc thành công các khóa từ file: " + filePath);
                    return true; // Thành công
                } else {
                    System.err.println("Không tìm thấy PUBLIC_KEY hoặc PRIVATE_KEY trong file.");
                    return false; // File không hợp lệ
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false; // Có lỗi xảy ra
            }
        } else {
            System.out.println("Hủy đọc file.");
            return false; // Người dùng hủy thao tác
        }
    }



    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, SignatureException, InvalidKeyException {
        genDSAKey();
        String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKeyDSA.getEncoded());
        String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKeyDSA.getEncoded());

        System.out.println("Public Key (Base64):");
        System.out.println(publicKeyBase64);

        System.out.println("\nPrivate Key (Base64):");
        System.out.println(privateKeyBase64);
    }
}
