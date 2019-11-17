package conn;

import gui.Field;
import main.Main;

import java.sql.*;
import java.util.ArrayList;

public class Connector {

    private static String dbName;
    private static Connection connection;

    static {
        dbName = "db38937";
        String url = "jdbc:mysql://mysql01.manitu.net";
        String username = "u38937";
        String password = "V2ZDudBWdT69";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> read(String table) {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("select * from " + dbName + "." + table);
            ArrayList<String> strings = new ArrayList<>();
            while (resultSet.next()) {
                for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                    strings.add(resultSet.getString(i+1));
                }
            }
            return strings;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Field readField(int width, int x, int y) {
        int index = y * width + x;
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("select * from " + dbName + ".Fields");
            int i = 0;
            while (resultSet.next()) {
                if (i == index) {
                    Field field = new Field(x, y);
                    //index, mine, open, flag
                    //0    , 1   , 2   , 3
                    field.setMine(Integer.parseInt(resultSet.getString(2)) == 1);
                    field.setOpen(Integer.parseInt(resultSet.getString(3)) == 1);
                    field.setFlag(width, Integer.parseInt(resultSet.getString(4)) == 1);
                    return field;
                }
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void insert(String table, Object... values) {
        try {
            StringBuilder builder = new StringBuilder("insert into ").append(dbName).append(".").append(table);
            builder.append(" values (");
            for (int i = 0; i < values.length; i++) {
                builder.append(values[i].toString());
                if (i < values.length-1) builder.append(", ");
            }
            builder.append(")");
            connection.createStatement().execute(builder.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertField(int width, Field field) {
        insert("Fields", field.getY() * width + field.getX(), field.getMine() ? 1 : 0, field.getOpen() ? 1 : 0, field.getFlag() ? 1 : 0);
    }

    public static void update(String table, int index, String attribute, boolean state) {
        try {
            String command = "UPDATE " + dbName + "." + table + " SET " + attribute + "=" + (state ? 1 : 0) + " WHERE IndexNumber=" + index + ";";
            System.out.println(command);
            connection.createStatement().execute(command);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void clear(String table) {
        try {
            connection.createStatement().execute("delete from " + dbName + "." + table + " where true");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
