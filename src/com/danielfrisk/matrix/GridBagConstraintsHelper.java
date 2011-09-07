package com.danielfrisk.matrix;

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 *
 * @author daniel.frisk@mojang.com
 */
class GridBagConstraintsHelper {

    private GridBagConstraints gbc;

    private GridBagConstraintsHelper() {
        this.gbc = new GridBagConstraints();
    }

    static GridBagConstraintsHelper gbc() {
        return new GridBagConstraintsHelper();        
    }
    
    GridBagConstraintsHelper pos(int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        return this;
    }

    GridBagConstraints done() {
        return gbc;
    }
    
    GridBagConstraintsHelper anchor(int anchor) {
        gbc.anchor = anchor;
        return this;
    }

    GridBagConstraintsHelper inset(int top, int left, int bottom, int right) {
        gbc.insets = new Insets(top, left, bottom, right);
        return this;
    }

    GridBagConstraintsHelper width(int w) {
        gbc.gridwidth = w;
        return this;
    }

    GridBagConstraintsHelper height(int h) {
        gbc.gridheight = h;
        return this;
    }
    
    GridBagConstraintsHelper fill(int f) {
        gbc.fill = f;
        return this;
    }
}
