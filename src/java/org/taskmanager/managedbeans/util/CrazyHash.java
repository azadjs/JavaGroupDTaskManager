package org.taskmanager.managedbeans.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Elmar H.Mammadov <jrelmarmammadov@gmail.com>
 */
public class CrazyHash {

    private final String SALT_DATA = "D178D6D084BD98A9D88D8B5FED1C2A859504355F6D1DED5D7B129D9834720B59";

    public String getHash(String content) throws NoSuchAlgorithmException {
        content += SALT_DATA;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(content.getBytes());
        byte updatedPass[] = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < updatedPass.length; i++) {
            sb.append(Integer.toString((updatedPass[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
