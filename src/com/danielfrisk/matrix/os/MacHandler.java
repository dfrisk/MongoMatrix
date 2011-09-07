package com.danielfrisk.matrix.os;

import javax.swing.JOptionPane;
import com.apple.mrj.MRJAboutHandler;
import com.apple.mrj.MRJApplicationUtils;
import com.apple.mrj.MRJQuitHandler;

public class MacHandler implements MRJAboutHandler, MRJQuitHandler {

    public static void register() {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "MongoMatrix");
        MacHandler handler = new MacHandler();
        MRJApplicationUtils.registerAboutHandler(handler);
        MRJApplicationUtils.registerQuitHandler(handler);
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
