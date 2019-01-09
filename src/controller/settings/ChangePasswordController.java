/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.settings;

import com.jfoenix.controls.JFXPasswordField;
import controller.mainscreen.MainScreen;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author YoungDON
 */
public class ChangePasswordController implements Initializable {

    @FXML
    private JFXPasswordField oldPassTxt;
    @FXML
    private JFXPasswordField newPassTxt;
    @FXML
    private JFXPasswordField conNewPass;
    MainScreen mainScreen;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    

    @FXML
    private void ChangePassword(ActionEvent event) throws FileNotFoundException, IOException {
        String user, password;
        try (Scanner scanner = new Scanner(MainScreen.user)) {
            user = scanner.nextLine();
            password = scanner.nextLine();
        }
        
        if(!oldPassTxt.getText().equals(password)){
            showAlert("Your password is incorrect.");
            return;
        } else if(!newPassTxt.getText().equals(conNewPass.getText())){
            showAlert("Both passwords do not match.");
            return;
        }
        
        try(PrintWriter writer = new PrintWriter(MainScreen.user)){
            writer.println(user);
            writer.println(newPassTxt.getText());
        }
        
        mainScreen.showSettingsWindow();
    }

    @FXML
    private void Back(ActionEvent event) throws IOException {
        mainScreen.showSettingsWindow();
    }

    public void setMainscreen(MainScreen aThis) {
        mainScreen = aThis;
    }
    
    void showAlert(String text){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(text);
        alert.show();
    }
    
}
