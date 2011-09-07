package com.danielfrisk.matrix.icons;

import com.mongodb.io.StreamUtil;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author daniel.frisk@mojang.com
 */
public abstract class Icons {

    private static Icon connect;

    public static Icon connect() {
        if (connect == null) {
            connect = new ImageIcon(load("connect.png"));
        }
        return connect;
    }

    private static byte[] load(String name) {
        try {
            return StreamUtil.readBytesFully(Icons.class.getResourceAsStream(name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
