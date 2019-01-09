/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.settings;

import controller.mainscreen.MainScreen;
import database.DatabaseHandler;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * FXML Controller class
 *
 * @author YoungDON
 */
public class SettingsController implements Initializable {
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
    private void ChangeUsername(ActionEvent event) throws IOException {
        String user;
        try (Scanner scanner = new Scanner(MainScreen.user)) {
            user = scanner.nextLine();
        }
        ChangeUsernameController.oldUser = user;
        mainScreen.showChangeUsername();
    }

    @FXML
    private void ChangePassword(ActionEvent event) throws IOException {
        mainScreen.showChangePass();
    }

    @FXML
    private void ClearData(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to clear data? Doing so will delete all your stored questions.");
        Optional<ButtonType> showAndWait = alert.showAndWait();
        
        if(showAndWait.get() == ButtonType.OK){
            DatabaseHandler dh = new DatabaseHandler();
            dh.executeUpdate("DELETE FROM questions");
            dh.executeUpdate("DELETE FROM topics");
        }
    }
    
    public void setMainscreen(MainScreen mainScreen){
        this.mainScreen = mainScreen;
    }
}
