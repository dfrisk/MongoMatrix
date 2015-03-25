package com.danielfrisk.matrix.os;

import javax.swing.JOptionPane;
import com.apple.mrj.MRJAboutHandler;
import com.apple.mrj.MRJQuitHandler;
import java.lang.reflect.Method;

/**
 * This class is a hack to get some native Mac app support but still be compilable on other platforms.
 * 
 * @author daniel.frisk@mojang.com
 */
public class MacHandler implements MRJAboutHandler, MRJQuitHandler {

    public static void register() {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "MongoMatrix");
        MacHandler handler = new MacHandler();
        try {
            Class<?> macAppUtilsClass = MacHandler.class.getClassLoader().loadClass("com.apple.mrj.MRJApplicationUtils");
            for(Method m : macAppUtilsClass.getMethods()) {
                String mn = m.getName();
                if(mn.equals("registerAboutHandler") || mn.equals("registerQuitHandler")) {
                    m.invoke(null, handler);
                }
            }
        } catch (Exception e) {
            System.err.println("Detected Mac with non MRJ Java. Native support won't work.");
        }
    }

    @Override
    public void handleAbout() {
        JOptionPane.showMessageDialog(null,
                "<html><p><p>A quick hack by daniel.frisk@gmail.com<p><p><i>Free to use for anything</i><p></html>",
                "MongoMatrix v0.1",
                JOptionPane.PLAIN_MESSAGE);
    }

    @Override
    public void handleQuit() throws IllegalStateException {
        System.exit(0);
    }
}
