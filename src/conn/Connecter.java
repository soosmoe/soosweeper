package conn;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Connecter {
    private String url = "jdbc:mysql://mysql01.manitu.net";
    private String username = "u38937";
    private String password = "V2ZDudBWdT69";
    private java.sql.Connection con;

    public Connecter (String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.con = DriverManager.getConnection(url,username,password);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<String> read(String table, int values) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from db38937." + table);
            List<String> res = new ArrayList<String>();
            System.out.println("fine");
            while (rs.next()) {
                for (int i = 1; i < values + 1; i++) {
                    res.add((rs.getString(i)));

                }
                res.add("§");
            }

            return res;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void delete(String table) {
        try {
            Statement stmt = con.createStatement();
            stmt.execute("delete from db38937." + table + " where true");
        }catch (Exception e) {
            System.out.println(e);
        }

    }

    public void write(String table, String par, String val) {
        try {
            Statement stmt = con.createStatement();
            stmt.execute("insert into db38937." + table +" values (" + val + ")");
        }catch (Exception e) {
            System.out.println(e);
        }
    }
}

