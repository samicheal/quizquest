/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author YoungDON
 */
public final class DatabaseHandler {
    //private static final String URL = "jdbc:mysql://localhost/";
    public static final String DATABASENAME = "QUIZQUESTDATABASE";
    private static final String URL = "jdbc:derby:" + DATABASENAME + ";create=true;";
    private Connection connection;
    //private ResultSet resultSet;
    private static Statement statement;
    //public List<List<String>> result = new ArrayList<>();
    
    public static void main(String[] args) {
        // TODO code application logic here
        DatabaseHandler dh = new DatabaseHandler();
        //dh.executeUpdate("USE STUDENTS");
        
        String update = "create table deck(id int, suit varchar(20))";
        //dh.executeUpdate(update);
        //dh.executeUpdate(update);
        System.out.println(dh.exists("deck"));
        
        String query = "Select * from deck";
        ResultSet resultSet = dh.executeQuery(query);
        
        try {
            while(resultSet.next()){
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
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
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            connection = DriverManager.getConnection(URL);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
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
    
    public boolean exists(String table){
        try {
            ResultSet set = connection.getMetaData().getTables(null, null, table.toUpperCase(), null);
            if(set.next())
                return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    void setURL(String user, String pass){
        //URL = "jdbc:derby:" + DATABASENAME + ";create=true;user=" + user + ";password=" + pass;
    }
    
}
