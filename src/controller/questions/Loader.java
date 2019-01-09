/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.questions;

import controller.mainscreen.MainScreen;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Loader extends Application {
    public static Stage mainStage;
    public static Stage addTopicStage;
    public static Stage addQuestionStage;
    public static Stage deleteTopicsStage;
    private MainScreen mainScreen;
    public static Scene prevScene;
    QuestionsScreenController qsc;
    EditquestionsController ec;
    DeleteTopicsController dtc;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        mainStage.setOnCloseRequest((event) -> {
            try {
                mainScreen = new MainScreen();
                MainScreen.notFirstTime = true;
                mainScreen.start(new Stage());
            } catch (Exception ex) {
                Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        showMainWindow();
    }
    
    void showMainWindow() throws IOException{  
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/questions/questionsscreen.fxml"));
        AnchorPane pane = loader.load();
        
        qsc = loader.getController();
        qsc.setLoader(this);
        
        if(prevScene == null){
            Scene scene = new Scene(pane);
            mainStage.setScene(scene);
            mainStage.setMaximized(true);
            mainStage.show();
        }else{
            pane.setPrefSize(prevScene.getWidth(), prevScene.getHeight());
            Scene scene = new Scene(pane);
            mainStage.setScene(scene);
        }
        mainStage.setResizable(false);
        mainStage.setTitle("View questions");
    }
    
    public void showAddTopicWindow() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/questions/addtopic.fxml"));
        AnchorPane pane = loader.load();
        
        AddTopicController atc = loader.getController();
        atc.setQSC(qsc);
        
        addTopicStage = new Stage();
        addTopicStage.initOwner(mainStage);
        addTopicStage.initModality(Modality.APPLICATION_MODAL);
        
        Scene scene = new Scene(pane);
        addTopicStage.setScene(scene);
        addTopicStage.setResizable(false);
        addTopicStage.show();
        addTopicStage.setTitle(AddTopicController.isRename ? "Rename topic" : "Add topic");
    }
    
    public void showAddQuestionWindow() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/questions/addquestion.fxml"));
        AnchorPane pane = loader.load();
        
        AddQuestionController aqc = loader.getController();
        aqc.setQSC(qsc);
        aqc.setEc(ec);
        
        addQuestionStage = new Stage();
        addQuestionStage.initOwner(mainStage);
        addQuestionStage.initModality(Modality.APPLICATION_MODAL);
        
        Scene scene = new Scene(pane);
        addQuestionStage.setScene(scene);
        addQuestionStage.setResizable(false);
        addQuestionStage.show();
        addQuestionStage.setTitle(AddQuestionController.isEdit ? "Edit question" : "Add question");
    }
    
    public void showEditQuestionsWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/questions/editquestions.fxml"));
        AnchorPane pane = loader.load();
        
        ec = loader.getController();
        ec.setLoader(this);
        ec.setQSC(qsc);
        
        prevScene = mainStage.getScene();
        pane.setPrefSize(prevScene.getWidth(), prevScene.getHeight());
        
        Scene scene = new Scene(pane);
        mainStage.setResizable(false);
        mainStage.setScene(scene);
        mainStage.setTitle("Edit and delete questions");
    }
    
    public void showDeleteTopicsWindow() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/questions/deletetopics.fxml"));
        AnchorPane pane = loader.load();
        
        dtc = loader.getController();
        dtc.setQsc(qsc);
        new AddTopicController().setQSC(qsc);
        
        deleteTopicsStage = new Stage();
        deleteTopicsStage.initOwner(mainStage);
        deleteTopicsStage.initModality(Modality.APPLICATION_MODAL);
        
        Scene scene = new Scene(pane);
        deleteTopicsStage.setScene(scene);
        deleteTopicsStage.setResizable(false);
        deleteTopicsStage.show();
        deleteTopicsStage.setTitle("Edit topics");
    }
    
}
