package app.database;

import app.eventos.ItemInserted;
import app.eventos.ItemUpdated;
import app.eventos.ItemDeleted;
import app.model.Item;
import org.greenrobot.eventbus.EventBus;

import java.sql.*;
import java.util.ArrayList;

public class Database {

    private static Database database;

    private Connection connection;

    public static Database getInstance() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("create table if not exists items(id varchar primary key, product varchar, quantity integer, purchased boolean)");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void init() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:database.db");

            createTable();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insertItem(Item item) {
        try {
            String sql = String.format("insert into items values ('%s', '%s',%d,%b)", item.getId(), item.getProduct(), item.getQuantity(), item.isPurchased());
            Statement statement = connection.createStatement();
            statement.execute(sql);
            EventBus.getDefault().post(new ItemInserted(item));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateItem(Item item) {
        try {
            String sql = String.format("update items set product = '%s', quantity = '%d', purchased = %b where id = '%s'", item.getProduct(), item.getQuantity(), item.isPurchased(), item.getId());
            Statement statement = connection.createStatement();
            statement.execute(sql);
            EventBus.getDefault().post(new ItemUpdated(item));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteItem(String id) {
        try {
            String sql = String.format("delete from items where id = '%s'", id);
            Statement statement = connection.createStatement();
            statement.execute(sql);
            EventBus.getDefault().post(new ItemDeleted(id));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ArrayList<Item> getItems() {
        try {
            String sql = "select * from items order by product";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            ArrayList itens = new ArrayList<Item>();
            while(result.next()) {
                    Item item = new Item(result.getString(1), result.getString(2), result.getInt(3), result.getBoolean(4));
                    itens.add(item);
            }
            return itens;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

}
