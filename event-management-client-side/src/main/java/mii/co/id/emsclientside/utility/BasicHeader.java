package mii.co.id.emsclientside.utility;

import java.nio.charset.Charset;
import org.apache.tomcat.util.codec.binary.Base64;

public class BasicHeader {

    public static String createBasicToken(String username, String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(Charset.forName("US-ASCII")));
        return new String(encodedAuth);
    }
}
