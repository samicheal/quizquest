/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.mainscreen;

import controller.questions.Loader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author YoungDON
 */
public class MenuController implements Initializable {
    private Loader loader;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loader = new Loader();
    }    

    @FXML
    private void CreateMatch(ActionEvent event) {
    }

    @FXML
    private void ViewQuestions(ActionEvent event) {
        Stage stage = new Stage();
        try {
            Loader.prevScene = null;
            loader.start(stage);
            MainScreen.primaryStage.close();
        } catch (Exception ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Settings(ActionEvent event) {
    }
    
}
