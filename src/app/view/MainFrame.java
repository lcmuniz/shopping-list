package app.view;

import app.database.Database;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {

        Database database = Database.getInstance();
        database.init();

        setTitle("Shopping List");

        setLayout(new BorderLayout());

        add(new FormPanel(), BorderLayout.WEST);
        add(new ListPanel(), BorderLayout.CENTER);

        setPreferredSize(new Dimension(700, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
