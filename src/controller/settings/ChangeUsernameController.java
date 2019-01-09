/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.settings;

import com.jfoenix.controls.JFXTextField;
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

/**
 * FXML Controller class
 *
 * @author YoungDON
 */
public class ChangeUsernameController implements Initializable {
    public static String oldUser;
    MainScreen mainScreen;
    @FXML
    private JFXTextField usernameTxt;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        usernameTxt.setText(oldUser);
    }    

    @FXML
    private void ChangeUername(ActionEvent event) throws FileNotFoundException, IOException {
        String pass;
        try (Scanner scanner = new Scanner(MainScreen.user)) {
            scanner.nextLine();
            pass = scanner.nextLine();
        }
        try (PrintWriter writer = new PrintWriter(MainScreen.user)) {
            writer.println(usernameTxt.getText());
            writer.println(pass);
        }
        mainScreen.showSettingsWindow();
    }

    @FXML
    private void Back(ActionEvent event) throws IOException {
        mainScreen.showSettingsWindow();
    }
    
    public void setMainscreen(MainScreen mainScreen){
        this.mainScreen = mainScreen;
    }
}
