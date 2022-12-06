package com.xws.bootpro.utils.RSA;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA加密工具类
 *
 * @author ACGkaka
 * @since 2021-09-19 19:11:03
 */
public class RSAUtil {

    /**
     * 密钥类实例化入参
     */
    private static final String KEY_ALGORITHM = "RSA";
    /**
     * Cipher类实例化入参
     */
    private static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    /**
     * 密钥对中公钥映射key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";
    /**
     * 密钥对中私钥映射key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    /**
     * 签名类实例化入参
     */
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * 初始化密钥对生成器时，指定密钥大小的整数值（安全漏洞，长度至少为2048）
     */
    private static final int KEY_PAIR_INIT_SIZE = 2048;

    /**
     * RSA最大解密密文大小，
     * RSA 位数 如果采用1024 上面最大加密和最大解密则须填写: 117 128
     * RSA 位数 如果采用2048 上面最大加密和最大解密则须填写: 245 256
     */
    private static final int MAX_DECRYPT_BLOCK = 256;

    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 获取公钥字符串
     *
     * @param keyMap 密钥对
     * @return 公钥字符串
     * @throws Exception 异常
     */
    public static String getPublicKeyStr(Map<String, Object> keyMap) throws Exception {
        //获得map中的公钥对象 转为key对象
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 获取私钥字符串
     *
     * @param keyMap 密钥对
     * @return 私钥字符串
     * @throws Exception 异常
     */
    public static String getPrivateKeyStr(Map<String, Object> keyMap) throws Exception {
        //获得map中的私钥对象 转为key对象
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }


    /**
     * 获取公钥
     *
     * @param key 公钥字符串
     * @return 公钥
     * @throws Exception 异常
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = decryptBASE64(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 获取私钥
     *
     * @param key 私钥字符串
     * @return 私钥
     * @throws Exception 异常
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = decryptBASE64(key);
        // 修复异常：java.security.InvalidKeyException: IOException : algid parse error, not a sequence
        Security.addProvider(new BouncyCastleProvider());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * Base64解码，返回byte[]
     *
     * @param key 待解码字符串
     * @return 解码后的byte[]
     */
    public static byte[] decryptBASE64(String key) {
        return Base64.getMimeDecoder().decode(key);
    }

    /**
     * 将byte[]进行Base64编码
     *
     * @param key 待编码的byte[]
     * @return 编码后的字符串
     */
    public static String encryptBASE64(byte[] key) {
        return Base64.getMimeEncoder().encodeToString(key);
    }

    /**
     * 生成签名
     *
     * @param data          待生成签名内容
     * @param privateKeyStr 私钥
     * @return 签名信息
     * @throws Exception 异常
     */
    public static String sign(byte[] data, String privateKeyStr) throws Exception {
        PrivateKey priK = getPrivateKey(new String(hexToBytes(privateKeyStr)));
        Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
        sig.initSign(priK);
        sig.update(data);
        return bytesToHex(sig.sign());
    }

    /**
     * 验证签名
     *
     * @param data         待验证原文
     * @param sign         待验证签名
     * @param publicKeyStr 公钥
     * @return 是否验证成功
     * @throws Exception 异常
     */
    public static boolean verify(byte[] data, String sign, String publicKeyStr) throws Exception {
        PublicKey pubK = getPublicKey(new String(hexToBytes(publicKeyStr)));
        Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
        sig.initVerify(pubK);
        sig.update(data);
        return sig.verify(hexToBytes(sign));
    }

    /**
     * RSA加密
     * @param plainText 待加密内容
     * @param publicKeyStr 公钥字符串
     * @return 加密后内容
     * @throws Exception 异常
     */
    public static String encrypt(byte[] plainText, String publicKeyStr) throws Exception {
        PublicKey publicKey = getPublicKey(publicKeyStr);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = plainText.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        int i = 0;
        byte[] cache;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(plainText, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(plainText, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptText = out.toByteArray();
        out.close();
        return bytesToHex(encryptText);
    }

    /**
     * RSA解密
     * @param encryptTextHex 已加密内容
     * @param privateKeyStr 私钥字符串
     * @return 解密后内容
     * @throws Exception 异常
     */
    public static String decrypt(String encryptTextHex, String privateKeyStr) throws Exception {
        byte[] encryptText = hexToBytes(encryptTextHex);
        PrivateKey privateKey = getPrivateKey(privateKeyStr);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        int inputLen = encryptText.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptText, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptText, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] plainText = out.toByteArray();
        out.close();
        return new String(plainText);
    }

    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(KEY_PAIR_INIT_SIZE);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 将byte[]转换为16进制字符串
     *
     * @param bytes 待转换byte[]
     * @return 转换后的字符串
     */
    public static String bytesToHex(byte[] bytes) {
        //一个byte为8位，可用两个十六进制位标识
        char[] buf = new char[bytes.length * 2];
        int a = 0;
        int index = 0;
        for (byte b : bytes) { // 使用除与取余进行转换
            if (b < 0) {
                a = 256 + b;
            } else {
                a = b;
            }

            buf[index++] = HEX_CHAR[a / 16];
            buf[index++] = HEX_CHAR[a % 16];
        }
        return new String(buf);
    }

    /**
     * 将16进制字符串转换为byte[]
     *
     * @param str 待转换字符串
     * @return 转换后的byte[]
     */
    public static byte[] hexToBytes(String str) {
        if (str == null || "".equals(str.trim())) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return bytes;
    }

    public static void main(String[] args) throws Exception {
        // 初始化密钥对
        Map<String, Object> keyMap = initKey();
        // 获取公钥字符串
        //String publicKey = getPublicKeyStr(keyMap);
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4rqGUdSLB3+31bMDfdEVlUZYEjQm4WjCN4XBXybKWer/16/1FQ5XNCxdNrYx5p+0t1n7Q5LzzI16pSH3P+S/N5aOGf5jU6Xfv5C0AMKmTgVB0Q79TUvkB41cYNrtUZsGr8ZzjTaRthiAClp7y0bLkR93k5Fy6qxkBHCh2IGExzCpiJxEuMIOr0PpizOCZAbLJLuXXt9eQPrgday7e1lj4tfk6czogp6ui7xAr7TCZoArwyE5yrthYtqOb2ClCPqpK3aw31NF00iYCGedDxOQDXYu/b4KoBwrylQqxtIhksLuO4dK48HufN2Mu9WCh2/WCAX1fJPS5PqPRAg+tWZpAQIDAQAB";
        // 获取私钥字符串
        //String privateKey = getPrivateKeyStr(keyMap);
        String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDiuoZR1IsHf7fVswN90RWVRlgSNCbhaMI3hcFfJspZ6v/Xr/UVDlc0LF02tjHmn7S3WftDkvPMjXqlIfc/5L83lo4Z/mNTpd+/kLQAwqZOBUHRDv1NS+QHjVxg2u1RmwavxnONNpG2GIAKWnvLRsuRH3eTkXLqrGQEcKHYgYTHMKmInES4wg6vQ+mLM4JkBssku5de315A+uB1rLt7WWPi1+TpzOiCnq6LvECvtMJmgCvDITnKu2Fi2o5vYKUI+qkrdrDfU0XTSJgIZ50PE5ANdi79vgqgHCvKVCrG0iGSwu47h0rjwe583Yy71YKHb9YIBfV8k9Lk+o9ECD61ZmkBAgMBAAECggEAbxDAS8W6dWnzPBP9Wdzanc1fx0sU4MbYnSpAl8QAzBt6SMZBYJct0LkK0Ipf14HUnRzPIUMTetizg8EnxXzgzvJJmiIrtgZDnSvdgaHYpc+ddjPJkdvjUG/HGQslxhwTIngskrhwmKtXzbqVefasMeMgSIGvsZXRSPuDzPNlf81CrBhrpwwQ//C6UIyIMGDMOELXNgitKjUI6gg7yWqby4A7WxlztUQdbty1ReJKphl6bHezwBADBTizu0XiXKBezy2MvcHtZ/GuRR+GhCpC5fDBfZeNFq/uIejmAT1Equ5jxR6h6ms4QY5S3zZayRu7DNBLZdc6xXzmn3UHGtpgAQKBgQD1NwkUkQW/BFyFh/e1ARavqUYWF/C8K6zV+wzPRBxd1hXZnSZAlJYWpbOKlaz9OwV3g3oFPQ/Bv5XKQgQ5MVJ63xd5V83HhffaYwQJVuMORiz77p4Opem3OhvF98D/D0Et9dxU3vYwkk4uvZqPR84w1g4S8mCyJL34X04ivnEQ4QKBgQDss1gukIyYClLtWRuUw2JzcaR16j4HjetUKr9DJuuVJuGAx2ne/j0CaJm+LsQ1ohnMgZkKpLu9EXkBQkWqh9Sd8Gl0zsnamFfFsYbz9AVTp52aCJwae/mkM6ozWjyH0JuY8qeGHjVMIsXPkE5DcLMnte29fb078+lOpTljGC68IQKBgC/WMbZKaFWQU0BVexRbhwJzwlFzECqVVp1T4XbZsbL4ncCbMKgulG0MnE7vzhSEnBdplbdJ5zYD2wPfBxXlMlL4DItGfsVqtCRtZo8v3RGezQ3Eyh7PbR2qf1qKb6MSZcPCj94atOpa0Fe781f6SRYr3AkEMarvEgRPC14pysLhAoGAFYENp91WCJIXipyn1tIRZa+TY2sOi50nHhRsH+uvR3Oq1QpI4gty+38JSK/y/3Rkp6G2h7MDo1+tAKJGtgF1HYwz6HrI9+UTRFCmlA89VKZLuSzDEdzlhzdyZQvzp9sZ58FT2ulvqiUWl47irVnVzOvIV4jO4l18erqkBg6yYEECgYBmgsxoz9qP5JOQNe4PomxV3S6EM8Up8Rtq81SEe5CXfV4dUxTZOfxA0KZ4XinlQNCbbCCflaeX9TkY0SIZjSVgW/Clk0RBmVs3Suhc9vtKu07oUXNUjFSrKCBlyBig4txmwo6OVo6FmTiW16joRQg/0iV45lV4EG/yHlnocYi+og==";

        // 打印公钥、私钥
        System.out.println("公钥：（填充方式：PKCS1_PADDING，输出类型：base64，字符集：utf8编码）");
        System.out.println("-----BEGIN PUBLIC KEY-----");
        System.out.println(publicKey);
        System.out.println(publicKey.length());
        System.out.println("-----END PUBLIC KEY-----");
        System.out.println("\n");

        System.out.println("私钥：（填充方式：PKCS1_PADDING，输出类型：base64，字符集：utf8编码）");
        System.out.println("-----BEGIN RSA PRIVATE KEY-----");
        System.out.println(privateKey);
        System.out.println(privateKey.length());
        System.out.println("-----END RSA PRIVATE KEY-----");
        System.out.println("\n");

        // 待加密内容，例：123
        String s = "adminadmin";
        // 进行RSA加密
        String encrypt = encrypt(s.getBytes(), publicKey);
        // 打印加密后内容
        System.out.println("密文：（填充方式：PKCS1_PADDING，输出类型：hex，字符集：utf8编码）");
        System.out.println(encrypt);
        System.out.println(encrypt.length());
        System.out.println("\n");

        // 进行RSA解密
        String decrypt = decrypt(encrypt, privateKey);
        // 打印解密后内容
        System.out.println("解密后明文: ");
        System.out.println(decrypt);
    }
}

