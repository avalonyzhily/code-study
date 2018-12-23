import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES密钥 加密 解密 工具类
 */
public class AESUtils {
    private final static String KEY_ALGORITHM = "AES";
    private final static String CIPHER_ALGORITHM="AES/ECB/PKCS5Padding";
    /**
     * @param secretKey
     *            密钥
     * @param content
     *            明文字符串
     * @return 密文字节数组
     */
    public static String encrypt(String content,SecretKeySpec secretKey,String charset) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encypted = cipher.doFinal(content.getBytes(Charset.forName(charset)));
            return Base64.encodeBase64String(encypted);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param content
     *            密文字节数组
     * @param secretKeySpec
     *            密钥
     * @return 解密后的字符串
     */
    public static String decrypt(String content, SecretKeySpec secretKeySpec,String charset) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decrypted = cipher.doFinal(Base64.decodeBase64(content));
            return new String(decrypted,Charset.forName(charset));
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @param seed 种子数据
     * @return 密钥数据
     */
    public static SecretKeySpec getRawKey(String seed,String charset) {
        SecretKeySpec rawKey = null;
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
            // AES加密数据块分组长度必须为128比特，密钥长度可以是128比特、192比特、256比特中的任意一个
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(seed.getBytes(Charset.forName(charset)));
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            rawKey = new SecretKeySpec(secretKey.getEncoded(),KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
        }
        return rawKey;
    }
}
