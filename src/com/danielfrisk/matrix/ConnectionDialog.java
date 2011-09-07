package com.danielfrisk.matrix;

import static java.awt.GridBagConstraints.*;
import static com.danielfrisk.matrix.GridBagConstraintsHelper.gbc;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * TODO: add authentication
 *
 * @author daniel.frisk@mojang.com
 */
class ConnectionDialog extends JDialog implements ActionListener {

    private static final String SERVER_PREF = "mongomatrix/server", PORT_PREF = "mongomatrix/port";
    private JTextField serverField, portField;
    private String server = "localhost";
    private int port = 27017;
    private ApplicationWindow app;

    ConnectionDialog(ApplicationWindow app) {
        super(app, "Connect", true);
        this.app = app;
        this.server = Preferences.userRoot().get(SERVER_PREF, server);
        this.port = Preferences.userRoot().getInt(PORT_PREF, port);
        initComponents();
    }

    private void initComponents() {
        // Create //
        JLabel serverLabel = new JLabel("Server:");
        JLabel portLabel = new JLabel("Port:");

        this.serverField = new JTextField(server);
        this.portField = new JTextField(Integer.toString(port));

        JButton connectButton = new JButton("Connect");
        connectButton.addActionListener(this);

        // Layout //
        setLayout(new GridBagLayout());
        setLocationByPlatform(true);

        add(serverLabel, gbc().pos(0, 0).anchor(EAST).inset(24, 12, 9, 9).done());
        add(portLabel, gbc().pos(0, 1).anchor(EAST).inset(9, 12, 9, 9).done());

        add(serverField, gbc().pos(1, 0).width(2).fill(HORIZONTAL).inset(15, 0, 0, 12).done());
        portField.setColumns(6);
        add(portField, gbc().pos(1, 1).done());

        add(connectButton, gbc().pos(2, 2).anchor(SOUTHEAST).inset(9, 9, 12, 9).done());

        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            server = serverField.getText();
            port = Integer.parseInt(portField.getText());

            app.connect(server, port);

            Preferences.userRoot().put(SERVER_PREF, server);
            Preferences.userRoot().putInt(PORT_PREF, port);

            ConnectionDialog.this.setVisible(false);
        } catch (NumberFormatException nfe) {
            portField.setText("27017");
            portField.requestFocusInWindow();
            return;
        }
    }
}
