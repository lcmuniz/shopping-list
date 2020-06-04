package app.view;

import app.database.Database;
import app.eventos.ItemInserted;
import app.eventos.ItemUpdated;
import app.eventos.ItemDeleted;
import app.eventos.ItemSelected;
import app.model.Item;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;

public class ListPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    public ListPanel() {
        setLayout(new GridLayout(1,1));

        EventBus.getDefault().register(this);

        model = new DefaultTableModel();
        model.addColumn("Product");
        model.addColumn("Quantity");
        model.addColumn("Purchased");
        model.addColumn("UUID");

        fillModel();

        table = new JTable(model);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                editItem();
            }
        });

        TableColumnModel tcm = table.getColumnModel();
        tcm.removeColumn(tcm.getColumn(3));

        JScrollPane scroll = new JScrollPane(table);
        add(scroll);
    }

    private void editItem() {
        int lin = table.getSelectedRow();
        if (lin == -1) return;

        String product = (String) model.getValueAt(lin, 0);
        Integer quantity = (Integer) model.getValueAt(lin, 1);
        String purchased = (String) model.getValueAt(lin, 2);
        String id = (String) model.getValueAt(lin, 3);

        Item item = new Item(id, product, quantity, purchased.equalsIgnoreCase("yes"));
        EventBus.getDefault().post(new ItemSelected(item));

    }

    private void fillModel() {
        ArrayList<Item> items = Database.getInstance().getItems();
        items.stream().forEach(item -> addRow(item));
    }

    private void addRow(Item item) {
        model.addRow(new Object[] {item.getProduct(), item.getQuantity(), item.getPurchasedAsString(), item.getId()});
    }

    private void sort() {
        Vector vector = model.getDataVector();
        vector.sort(Comparator.comparing(item -> (((Vector) item).get(0).toString())));
    }

    @Subscribe
    public void on(ItemInserted event) {
        addRow(event.getItem());
        sort();
    }

    @Subscribe
    public void on(ItemUpdated event) {
        int lin = table.getSelectedRow();
        model.removeRow(lin);
        addRow(event.getItem());
        sort();
        table.clearSelection();
    }

    @Subscribe
    public void on(ItemDeleted event) {
        int lin = table.getSelectedRow();
        if (lin == -1) return;
        model.removeRow(lin);
        table.clearSelection();
    }

}
