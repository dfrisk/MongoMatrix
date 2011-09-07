package com.danielfrisk.matrix;

import com.danielfrisk.matrix.os.MacHandler;
import com.explodingpixels.util.PlatformUtils;
import javax.swing.UIManager;

/**
 *
 * @author daniel.frisk@gmail.com
 */
public class MongoMatrix {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting...");
        if (PlatformUtils.isMac()) {
            MacHandler.register();
        }

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        ApplicationWindow.create();
    }
}
