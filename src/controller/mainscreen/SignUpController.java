/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.mainscreen;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseHandler;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import model.User;

/**
 *
 * @author YoungDON
 */
public class SignUpController {

    @FXML
    private JFXTextField usernameTxt;
    @FXML
    private JFXPasswordField passwordTxt;
    @FXML
    private JFXPasswordField repassTxt;
    private MainScreen mainScreen;
    @FXML
    private Label errorTxt;
    
    public void setMainScreen(MainScreen mainScreen){
        this.mainScreen = mainScreen;
    }

    @FXML
    private void SignUp(ActionEvent event) {
        User user = new User(usernameTxt.getText(), passwordTxt.getText(), repassTxt.getText());
        
        if(!user.verifyUsername()){
            errorTxt.setText("Username invalid");
            
        }else if(!user.verifyPassword()){
            errorTxt.setText("Password should be 4 or more characters");
        }else if(!user.verifyRePass()){
            errorTxt.setText("Both password fields do not match");
        }else{
            saveUser();
        }
    }

    @FXML
    private void Clear(KeyEvent event) {
        errorTxt.setText("");
    }
    
    void saveUser(){
        if(!createDB())
            return;
        
        try {
            //ResultSet set = 
            //dh.executeUpdate("DROP DATABASE " + MainScreen.DATABASENAME);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            try (PrintWriter printWriter = new PrintWriter(MainScreen.user)) {
                printWriter.println(usernameTxt.getText());
                printWriter.println(passwordTxt.getText());
            }
        } catch (FileNotFoundException ex) {
            errorTxt.setText("An error occured. Your details could not be saved");
        }
        mainScreen.secondaryStage.close();
        try {
            mainScreen.showMenuWindow();
        } catch (IOException ex) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    boolean createDB(){
        String db = DatabaseHandler.DATABASENAME;
        DatabaseHandler dh = new DatabaseHandler();
        
        if(dh.exists("questions")) dh.executeUpdate("DROP TABLE questions");
        if(dh.exists("topics")) dh.executeUpdate("DROP TABLE topics");
        
        String update = "CREATE TABLE topics("
                + "id INT NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,"
                + "topic VARCHAR(30) NOT NULL UNIQUE"
                + ")";
        boolean success = dh.executeUpdate(update);
        System.out.println(success);
        
        //dh.executeUpdate("USE " + db);        
        update = "CREATE TABLE questions("
                + "id INT NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,"
                + "question VARCHAR(200) NOT NULL,"
                + "optionA VARCHAR(100) NOT NULL,"
                + "optionB VARCHAR(100) NOT NULL,"
                + "optionC VARCHAR(100) NOT NULL,"
                + "optionD VARCHAR(100) NOT NULL,"
                + "answer CHAR(1) NOT NULL,"
                + "topicID INT NOT NULL,"
                + "FOREIGN KEY (topicID) REFERENCES TOPICS(id)"
                + ")";
        success = dh.executeUpdate(update);
        System.out.println(success);
        return success;
    }
}
