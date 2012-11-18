package springWeb.util;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {
    private static final String PASSWORD = "jpptopa";

    public String generateHash(String requestXml) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        SecretKey secretKey = new SecretKeySpec(PASSWORD.getBytes("UTF-8"), "HmacMD5");
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return new sun.misc.BASE64Encoder().encode(mac.doFinal(requestXml.getBytes("UTF-8")));
    }
}
