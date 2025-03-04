package com.summersec.attack.Encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @ClassName: Encrypt
 * @Description: AES加密工具类
 * @Author: Summer
 * @Date: 2021/1/19 20:25
 * @Version: v1.0.0
 **/
public class Encrypt {

    public static String encrypt(byte[] serialized, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        int sizeInBytes = 16;
        byte[] iv = new byte[sizeInBytes];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec);
        byte[] encrypted = cipher.doFinal(serialized);

        // 合并 IV 和加密数据
        byte[] output = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, output, 0, iv.length);
        System.arraycopy(encrypted, 0, output, iv.length, encrypted.length);

        // 使用 Java 8+ 的 Base64 进行编码
        return Base64.getEncoder().encodeToString(output);
    }
}
