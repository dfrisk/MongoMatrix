package com.danielfrisk.matrix;

import com.danielfrisk.matrix.icons.Icons;
import com.explodingpixels.macwidgets.SourceList;
import com.explodingpixels.macwidgets.SourceListCategory;
import com.explodingpixels.macwidgets.SourceListClickListener;
import com.explodingpixels.macwidgets.SourceListControlBar;
import com.explodingpixels.macwidgets.SourceListItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author daniel.frisk@mojang.com
 */
class SideBar extends SourceList implements SourceListClickListener {

    ApplicationWindow app;
    SourceListCategory databases;
    SourceListCategory collections;

    SideBar(ApplicationWindow app) {
        this.app = app;
        createControlBar();
        refresh();
        addSourceListClickListener(SideBar.this);
    }

    private void createControlBar() {
        SourceListControlBar cb = new SourceListControlBar();
        cb.createAndAddButton(Icons.connect(), new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                    app.showConnectDialog();
            }
        });
        installSourceListControlBar(cb);
    }

    final void refresh() {
        refreshDatabases();
        refreshCollections();
    }

    private void refreshDatabases() {
        if (databases != null) {
            getModel().removeCategory(databases);
        }
        getModel().addCategory(databases = new SourceListCategory("Database"));
        if (app.getMongo() != null) {
            for (String dbName : app.getMongo().getDatabaseNames()) {
                getModel().addItemToCategory(new SourceListItem(dbName), databases);
            }
        }
    }

    private void refreshCollections() {
        if (collections != null) {
            getModel().removeCategory(collections);
        }
        getModel().addCategory(collections = new SourceListCategory("Collections"));
        if (app.getDb() != null) {
            for (String collectionName : app.getDb().getCollectionNames()) {
                getModel().addItemToCategory(new SourceListItem(collectionName), collections);
            }
        }
    }

    @Override
    public void sourceListItemClicked(SourceListItem item, Button button, int i) {
        if (databases.containsItem(item)) {
            app.setDb(app.getMongo().getDB(item.getText()));
            refreshCollections();
        } else if (collections.containsItem(item)) {
            app.setSelectedCollection(item.getText());
            app.refreshView();
        }
    }

    @Override
    public void sourceListCategoryClicked(SourceListCategory slc, Button button, int i) {
        // empty on purpose
    }
}
