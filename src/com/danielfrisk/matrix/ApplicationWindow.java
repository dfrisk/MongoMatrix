package com.danielfrisk.matrix;

import com.explodingpixels.macwidgets.MacWidgetFactory;
import com.mongodb.DB;
import com.mongodb.Mongo;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

/**
 * This is a quick hack to be able to edit MongoDB collections in a table view.
 * Free to use for anything. Please send me patches :-)
 * 
 * @author daniel.frisk@mojang.com
 */
class ApplicationWindow extends JFrame {

    private Mongo mongo;
    private DB db;
    private String selectedCollection;
    private SideBar bar;
    private CollectionView view;
    private ConnectionDialog connectDialog;

    ApplicationWindow() {
        super("MongoMatrix");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screen.width * 3 / 4, screen.height * 3 / 4);

        JSplitPane sp = MacWidgetFactory.createSplitPaneForSourceList(
                bar = new SideBar(this),
                view = new CollectionView(this));
        sp.setDividerLocation(240);
        add(sp);
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        showConnectDialog();
    }

    static void create() {
        new ApplicationWindow().setVisible(true);
    }

    void refreshView() {
        view.refresh();
    }

    void showConnectDialog() {
        if (connectDialog == null) {
            connectDialog = new ConnectionDialog(this);
        }
        connectDialog.setVisible(true);
    }

    void connect(String server, int port) {
        try {
            this.setMongo(new Mongo(server, port));
            bar.refresh();
            view.refresh();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getCause() != null ? e.getCause().getMessage() : e.getMessage(), "Connect failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    String getSelectedCollection() {
        return selectedCollection;
    }

    void setSelectedCollection(String selectedCollection) {
        this.selectedCollection = selectedCollection;
    }

    Mongo getMongo() {
        return mongo;
    }

    void setMongo(Mongo mongo) {
        this.mongo = mongo;
    }

    DB getDb() {
        return db;
    }

    void setDb(DB db) {
        this.db = db;
    }
}
