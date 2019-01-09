package controller.quiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ContestantsReg extends Application
{
    public static List<String> contestantNames;
    public static List<Integer> parameters;
    public static List<String> selectQuestion;
    public static List<String> topics=new ArrayList<>();
    
    public static List<Integer> points=new ArrayList<>();
    public static List<Integer> bonus=new ArrayList<>();
    public static int indexForTopic=0;
    
    public static Stage primaryStage;
    public static Stage contestantsStage;
    public static Stage selectQuestionStage;
    public static Stage questionStage;
    public static Stage secondStag;
    public static Stage answerStage;
    public static Stage boardStage;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        ContestantsReg.primaryStage=primaryStage;
        mainWindow();
    }
    
    public void mainWindow() throws IOException {
        FXMLLoader loader=new FXMLLoader(ContestantsReg.class.getResource("/view/quiz/contestantsReg.fxml"));
        AnchorPane pane=loader.load();
        
        ContestantsRegController setControl=loader.getController();
        setControl.setReg(this,primaryStage);
        
        Scene scene=new Scene(pane);
        
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setMaximized(true);
    }
    
    public void contestantNamesWindow() throws IOException{
        FXMLLoader loader=new FXMLLoader(ContestantsReg.class.getResource("/view/quiz/ContestantsName.fxml"));
        AnchorPane pane=loader.load();
        
        contestantsStage=new Stage();
        contestantsStage.initOwner(primaryStage);
        contestantsStage.initModality(Modality.WINDOW_MODAL);
        
        
        Scene scene=new Scene(pane);
        
        contestantsStage.setScene(scene);
        contestantsStage.setResizable(false);
        contestantsStage.show();
    }
    
    public void selectTopicsWindow() throws IOException{
        FXMLLoader loader=new FXMLLoader(ContestantsReg.class.getResource("/view/quiz/SelectTopics.fxml"));
        AnchorPane pane=loader.load();
        
        secondStag=new Stage();
        secondStag.initOwner(primaryStage);
        secondStag.initModality(Modality.WINDOW_MODAL);
        
        Scene scene=new Scene(pane);
        
        secondStag.setScene(scene);
        secondStag.setResizable(false);
        secondStag.show();
    }
    
    public void selectQestionWindow() throws IOException{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/view/quiz/SelectQuestions.fxml"));
        AnchorPane pane=loader.load();
        
        selectQuestionStage=new Stage();
        selectQuestionStage.initOwner(primaryStage);
        selectQuestionStage.initModality(Modality.WINDOW_MODAL);
        
        Scene scene=new Scene(pane);
        
        selectQuestionStage.setScene(scene);
        selectQuestionStage.setResizable(false);
        selectQuestionStage.setMaximized(true);
        selectQuestionStage.show();
    }
    
    public void QuestionWindow() throws IOException{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/view/quiz/Questions.fxml"));
        AnchorPane pane=loader.load();
        
        questionStage=new Stage();
        questionStage.initOwner(primaryStage);
        questionStage.initModality(Modality.WINDOW_MODAL);
        
        Scene scene=new Scene(pane);
        
        questionStage.setScene(scene);
        questionStage.setResizable(false);
        questionStage.setMaximized(true);
        questionStage.show();
    }
    
    public void correctAnswerWindow() throws IOException{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/view/quiz/CorrectAnswer.fxml"));
        AnchorPane pane=loader.load();
        
        answerStage=new Stage();
        answerStage.initOwner(primaryStage);
        answerStage.initModality(Modality.WINDOW_MODAL);
        
        Scene scene=new Scene(pane);
        
        answerStage.setScene(scene);
        answerStage.setResizable(false);
        answerStage.setMaximized(true);
        answerStage.show();
    }
    
    public void scoreBoardWindow() throws IOException{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/view/quiz/ScoreBoard.fxml"));
        AnchorPane pane=loader.load();
        
        boardStage=new Stage();
        boardStage.initOwner(primaryStage);
        boardStage.initModality(Modality.WINDOW_MODAL);
        
        Scene scene=new Scene(pane);
        
        boardStage.setScene(scene);
        boardStage.setResizable(false);
        boardStage.setMaximized(true);
        boardStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}