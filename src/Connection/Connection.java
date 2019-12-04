package Connection;

import game.*;

import java.sql.*;
import java.util.ArrayList;

public class Connection {
    private java.sql.Connection con;
    public Connection() {
        connect();
    }

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://mysql01.manitu.net", "u38937", "V2ZDudBWdT69");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void clear() {
        try {
            Statement stmt = con.createStatement();
            System.out.println("delete from db38937.Field");
            stmt.execute("delete from db38937.Field");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void saveToDB(Field f) {
        try {
            Statement stmt = con.createStatement();
            System.out.println("update db38937.Field set Offen = " + (f.getOpen()? 1:0) + ", Flag = " + (f.getFlag()? 1:0) + " where X = " + f.getX() + " and Y = " + f.getY());
            stmt.execute("update db38937.Field set Offen = " + (f.getOpen()? 1:0) + ", Flag = " + (f.getFlag()? 1:0) + " where X = " + f.getX() + " and Y = " + f.getY());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void saveInit(Field f) {
        try {
            Statement stmt = con.createStatement();

            System.out.println("insert into db38937.Field (X, Y, Mine, Offen, Flag) values (" + f.getX() + ", "  + f.getY() + ", 0, 0, 0)");
            stmt.execute("insert into db38937.Field (X, Y, Mine, Offen, Flag) values (" + f.getX() + ", "  + f.getY() + ", 0, 0, 0)");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setMine(Field f) {
        try {
            Statement stmt = con.createStatement();
            stmt.execute("update db38937.Field set Mine = 1 where X = " + f.getX() + " and Y = " + f.getY());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList<Field> getFromDB(Board b) {
        ArrayList<Field> field = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from db38937.Field");
            int i = 1;
            while (rs.next())
                field.add(new game.Field(rs.getInt(i),rs.getInt(i+1),rs.getInt(i+2)==1,rs.getInt(i+3)==1,rs.getInt(i+4)==1,b));
                i+=5;


        } catch (Exception e) {
            System.out.println(e);
        }
        return field;
    }
}
