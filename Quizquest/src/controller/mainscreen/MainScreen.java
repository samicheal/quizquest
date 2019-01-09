/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.mainscreen;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author YoungDON
 */
public class MainScreen extends Application {
    public static Stage primaryStage;
    public Stage secondaryStage;
    public static boolean notFirstTime;
    public static File user = new File("user.txt");
    public static final String DATABASENAME = "QUIZQUESTDATABASE";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainScreen.primaryStage = primaryStage;

        //System.out.println(user.exists());
        if(user.exists()){
            if(!notFirstTime)
                showMainWindow();
            else{
                showMenuWindow();
                notFirstTime = false;
            }
        }else{
            showSignUpWindow();
        }
    }
    
    public void showMainWindow() throws IOException{
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/mainscreen/mainscreenview.fxml"));
        AnchorPane pane = loader.load();

        MainscreenController msc = loader.getController();
        msc.setIndex(this);
        //set scene
        Scene scene = new Scene(pane);
        //add scene to stage
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Quizquest 2017 by Solzy and Peter");
        primaryStage.show();
    }
    
    public void showSignUpWindow() throws IOException{
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/mainscreen/signupscreen.fxml"));
        AnchorPane pane = loader.load();
        
        SignUpController suc = loader.getController();
        suc.setMainScreen(this);
        //create stage
        secondaryStage = new Stage();
        secondaryStage.initOwner(primaryStage);
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        
        //set scene
        Scene scene = new Scene(pane);
        //add scene to stage
        secondaryStage.setScene(scene);
        secondaryStage.setResizable(false);
        secondaryStage.setTitle("Create account");
        secondaryStage.show();
    }
    
    public void showMenuWindow() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainscreen/menu.fxml"));
        AnchorPane pane = loader.load();
        
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setTitle("Quizquest 2017 by Solzy and Peter");
    }
}
