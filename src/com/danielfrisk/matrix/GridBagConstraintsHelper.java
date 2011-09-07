package com.danielfrisk.matrix;

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 *
 * @author daniel.frisk@mojang.com
 */
public class GridBagConstraintsHelper {

    private GridBagConstraints gbc;

    private GridBagConstraintsHelper() {
        this.gbc = new GridBagConstraints();
    }

    public static GridBagConstraintsHelper gbc() {
        return new GridBagConstraintsHelper();        
    }
    
    public GridBagConstraintsHelper pos(int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        return this;
    }

    public GridBagConstraints done() {
        return gbc;
    }
    
    public GridBagConstraintsHelper anchor(int anchor) {
        gbc.anchor = anchor;
        return this;
    }

    public GridBagConstraintsHelper inset(int top, int left, int bottom, int right) {
        gbc.insets = new Insets(top, left, bottom, right);
        return this;
    }

    public GridBagConstraintsHelper width(int w) {
        gbc.gridwidth = w;
        return this;
    }

    public GridBagConstraintsHelper height(int h) {
        gbc.gridheight = h;
        return this;
    }
    
    public GridBagConstraintsHelper fill(int f) {
        gbc.fill = f;
        return this;
    }
}
