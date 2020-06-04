package app.view;

import app.database.Database;
import app.eventos.ItemSelected;
import app.model.Item;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.UUID;

public class FormPanel extends JPanel {

    private String idItem = "";
    private JTextField productTextField = new JTextField(15);
    private JTextField quantityTextField = new JTextField(5);
    private JCheckBox purchasedCheckBox = new JCheckBox();
    private JButton deleteButton;

    public FormPanel() {

        EventBus.getDefault().register(this);

        productTextField = new JTextField(15);
        quantityTextField = new JTextField(5);
        purchasedCheckBox = new JCheckBox();

        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5 ,5  );

        c.anchor = GridBagConstraints.LINE_END;
        c.gridx = 0;
        add(new JLabel("Product"), c);
        add(new JLabel("Quantity"), c);
        add(new JLabel("Purchased"), c);
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 1;
        add(productTextField, c);
        add(quantityTextField, c);
        add(purchasedCheckBox, c);

        JPanel buttons = new JPanel();
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> salveItem());

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteItem());
        deleteButton.setEnabled(false);

        buttons.add(saveButton);
        buttons.add(deleteButton);

        add(buttons, c);

        c.gridy = 4;
        c.weighty = 10;
        add(new JPanel(), c);
    }

    private void salveItem() {
        if (!productTextField.getText().equals("") && !quantityTextField.getText().equals("")) {
            if (idItem.equals("")) {
                Item item = new Item(UUID.randomUUID().toString(), productTextField.getText(), Integer.parseInt(quantityTextField.getText()), purchasedCheckBox.isSelected());
                Database.getInstance().insertItem(item);
            }
            else {
                Item item = new Item(idItem, productTextField.getText(), Integer.parseInt(quantityTextField.getText()), purchasedCheckBox.isSelected());
                Database.getInstance().updateItem(item);
            }
        }
        clearControls();
    }

    private void deleteItem() {
        Database.getInstance().deleteItem(idItem);
        clearControls();
    }

    private void clearControls() {
        idItem = "";
        productTextField.setText("");
        quantityTextField.setText("");
        purchasedCheckBox.setSelected(false);
        productTextField.grabFocus();
        deleteButton.setEnabled(false);
    }

    @Subscribe
    public void on(ItemSelected event) {
        idItem = event.getItem().getId();
        productTextField.setText(event.getItem().getProduct());
        quantityTextField.setText(event.getItem().getQuantity().toString());
        purchasedCheckBox.setSelected(event.getItem().isPurchased());
        productTextField.grabFocus();
        deleteButton.setEnabled(true);
    }
}
