package aero.icarus2020.keypairadministrator.util;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


@Service
public final class Util {

    private static final Logger logger = LogManager.getLogger(Util.class);


    @Qualifier("webApplicationContext")
    @Autowired
    static
    ResourceLoader resourceLoader;

    public enum ALGORITHM {
        SHA
    }

    public static String serializeHttpServletRequestHeaders(HttpServletRequest request) {

        List<String> headers = new ArrayList();

        try {

            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName);
                headers.add(headerName + ":" + headerValue);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }

        return new Gson().toJson(headers);
    }//EoM

    public static String createAlgorithm(String content, String algorithm) {

        StringBuilder hexString = new StringBuilder();

        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hash = digest.digest(content.getBytes(StandardCharsets.UTF_8));

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

        } catch (NoSuchAlgorithmException ex) {
            logger.error(ex.getMessage(), ex);
        }

        return hexString.toString();
    }


}//EoC
