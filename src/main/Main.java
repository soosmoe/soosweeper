package main;

import conn.Connecter;
import gui.Panel;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args){
        new Panel().start();
        //Connecter c = new Connecter("jdbc:mysql://mysql01.manitu.net","u38937", "V2ZDudBWdT69");
        //System.out.println(c.read("Bombs", 2));
        //System.out.println("soos");
        //c.delete("Bombs");
        //System.out.println("soos");
        //c.write("Bombs", "Row, Column", "' 5 ' , '5' ");
    }
}
