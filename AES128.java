import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * Created by Matthew on 2016-06-14.
 */
public class AES128 {
    public static String encrypt(String key, String text) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        SecretKey sKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, sKey, new IvParameterSpec(new byte[16]));
        return Base64.getEncoder().encodeToString(cipher.doFinal(text.getBytes("UTF-8")));
    }

    public static String decrypt(String key, String cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        SecretKey sKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, sKey, new IvParameterSpec(new byte[16]));
        return new String(cipher.doFinal(Base64.getDecoder().decode(cipherText)), "UTF-8");
    }
}
