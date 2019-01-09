/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import controller.mainscreen.MainScreen;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author YoungDON
 */
public final class DatabaseHandler {
    private static final String URL = "jdbc:mysql://localhost/";
    private static final String USER = "root";
    private static final String PASS = "";
    private Connection connection;
    //private ResultSet resultSet;
    private static Statement statement;
    //public List<List<String>> result = new ArrayList<>();
    
    public static void main(String[] args) {
        // TODO code application logic here
        DatabaseHandler dh = new DatabaseHandler();
        dh.executeUpdate("USE STUDENTS");
        
        String update = "drop database deck";
        //dh.executeUpdate(update);
        System.out.println();
        
        String query = "SELECT * FROM EXAM";
        ResultSet resultSet = dh.executeQuery(query);
        
        try {
            while(resultSet.next()){
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public DatabaseHandler(){
        createConnection();
    }
    
    void createConnection(){
        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
            statement = connection.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean executeUpdate(String update){
        try {
            statement = connection.createStatement();
            statement.executeUpdate(update);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public ResultSet executeQuery(String query){
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
    
    public boolean exists(String database){
        try {
            ResultSet set = connection.getMetaData().getCatalogs();
            while(set.next()){
                if(database.toLowerCase().equals(set.getString(1).toLowerCase()))
                    return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
