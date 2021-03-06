package com.danielfrisk.matrix;

import com.explodingpixels.macwidgets.MacWidgetFactory;
import com.explodingpixels.widgets.TableUtils;
import com.explodingpixels.widgets.TableUtils.SortDirection;
import com.mongodb.BasicDBList;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.DBRef;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 * TODO:
 *    Add document
 *    Delete document
 *
 * @author daniel.frisk@mojang.com
 */
class CollectionView extends JPanel {

    ApplicationWindow app;
    CollectionModel model;
    JTable table;

    CollectionView(ApplicationWindow app) {
        this.app = app;
        model = new CollectionModel();
        table = MacWidgetFactory.createITunesTable(model);

        TableUtils.SortDelegate sortDelegate = new TableUtils.SortDelegate() {

            @Override
            public void sort(int column, TableUtils.SortDirection direction) {
                model.sort(column, direction);
            }
        };
        TableUtils.makeSortable(table, sortDelegate);

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    void refresh() {
        model.refresh();
        table.createDefaultColumnsFromModel();
    }

    /**
     * TODO:
     *   Support DBRef, BasicDBList properly (click ref browse there?)
     *   Learn how to do a proper list...
     *   Custom CellRenderer for references (and lists)?
     *   Clean up and refactor this happy hack
     */
    class CollectionModel extends AbstractTableModel {

        ArrayList<DBObject> data;
        ArrayList<String> columnNames;

        public CollectionModel() {
            refresh();
        }

        final void refresh() {
            this.data = new ArrayList<DBObject>();
            this.columnNames = new ArrayList<String>();
            if (app.getDb() == null || app.getSelectedCollection() == null) {
                return;
            }
            DBCursor cursor = app.getDb().getCollection(app.getSelectedCollection()).find();
            while (cursor.hasNext()) {
                DBObject obj = cursor.next();
                data.add(obj);
                int ix = 0;
                for (String name : obj.keySet()) {
                    int ixx = columnNames.indexOf(name);
                    if (ixx < 0) {
                        columnNames.add(ix++, name);
                    } else {
                        ix = ixx;
                    }
                }
            }
            cursor.close();
        }

        @Override
        public String getColumnName(int column) {
            return columnNames.get(column);
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            String fieldName = columnNames.get(columnIndex);
            DBObject obj = data.get(rowIndex);
            if (obj.containsField(fieldName)) {
                Object field = obj.get(fieldName);
                if (field instanceof BasicDBList) {
                    return "<BasicDBList>";
                } else if (field instanceof DBRef) {
                    return "<DBRef>";
                } else {
                    return field.toString();
                }
            } else {
                return "<n/a>";
            }
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            DBObject d = data.get(row);
            String fieldName = columnNames.get(col);
            if (d.containsField(fieldName)) {
                Object f = d.get(fieldName);
                return f instanceof String || f instanceof Integer;
            }
            return false;
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            DBCollection collection = app.getDb().getCollection(app.getSelectedCollection());

            DBObject dbo = collection.findOne(data.get(row));
            data.set(row, dbo);

            String key = columnNames.get(col);
            Object oldValue = dbo.get(key);
            Object newValue;
            if (oldValue instanceof Integer) {
                newValue = Integer.parseInt(value.toString());
            } else {
                newValue = value;
            }

            dbo.put(key, newValue);

            // Save to db
            collection.save(dbo);
            fireTableCellUpdated(row, col);
        }

        private void sort(final int column, final SortDirection direction) {
            Collections.sort(data, new Comparator<DBObject>() {

                int r = direction == SortDirection.ASCENDING ? -1 : 1;
                String field = columnNames.get(column);

                @Override
                @SuppressWarnings("unchecked")
                public int compare(DBObject a, DBObject b) {
                    Comparable av = (Comparable) a.get(field);
                    return r * av.compareTo(b.get(field));
                }
            });
            fireTableDataChanged();
        }
    }
}
