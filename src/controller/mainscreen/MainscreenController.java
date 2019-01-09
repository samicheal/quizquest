/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.mainscreen;

import com.jfoenix.controls.JFXButton;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author YoungDON
 */
public class MainscreenController implements Initializable{

    @FXML
    private PasswordField passwordTxt;
    @FXML
    private JFXButton signinBut;
    @FXML
    private JFXButton signupBut;
    private MainScreen mainScreen;
    @FXML
    private VBox passwordBox;
    private String username, password;
    @FXML
    private Label usernameLbl;

    @FXML
    private void SignIn(ActionEvent event) {
        if(passwordTxt.getText().equals(password)){
            try {
                openMenu();
            } catch (IOException ex) {
                Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Wrong password");
            alert.show();
            passwordTxt.setText("");
        }
    }

    @FXML
    private void SignUp(ActionEvent event) {
        try {
            mainScreen.showSignUpWindow();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Note that creating a new account will clear your previous one");
            alert.show();
        } catch (IOException ex) {
            Logger.getLogger(MainscreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void setIndex(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            try (Scanner scanner = new Scanner(MainScreen.user)) {
                username = scanner.nextLine();
                password = scanner.nextLine();
                usernameLbl.setText(username);
            }
        } catch (FileNotFoundException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("I/O Error");
            alert.setContentText("An error occured trying to read your details.");
            alert.show();
        }
    }
    
    void openMenu() throws IOException{
        mainScreen.showMenuWindow();
    }
    
}
