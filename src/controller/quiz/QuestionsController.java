package controller.quiz;

import com.jfoenix.controls.JFXButton;
import controller.mainscreen.MainScreen;
import static controller.quiz.SelectQuestionsController.lastButtonClick;
import static controller.quiz.SelectQuestionsController.nOfQuest;
import database.DatabaseHandler;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Question;
import model.Topic;

public class QuestionsController implements Initializable {

    @FXML
    private Label topic;
    @FXML
    private Label questionNo;
    @FXML
    private Label contestant;
    @FXML
    private JFXButton Back;
    @FXML
    private JFXButton Next;
    @FXML
    private Label optionA;
    @FXML
    private Label optionB;
    @FXML
    private Label optionC;
    @FXML
    private Label optionD;
    @FXML
    private Label question;
    @FXML
    private Label labelTimer;
    List<Question> allQuestions;
    
    List<Topic> topics;
    public  static String fullAnswer;
    
    Thread trd=new Thread();
    
    public static String answer="";
    
    int i=1;
    
    boolean ifClicked=false;
    
    private List<Integer> id=new ArrayList<>();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DatabaseHandler dh = ContestantsRegController.dh;
        topics = ContestantsRegController.selectedTopics;
        
        try {
            questionNo.setText(ContestantsReg.selectQuestion.get(1));//question Number
            contestant.setText(ContestantsReg.selectQuestion.get(0));//name
            
            if(ContestantsRegController.randomTopBool)
                SelectQuestionsController.indexForTopic = (int)(Math.random()*ContestantsReg.topics.size());
            
            topic.setText(ContestantsReg.topics.get(SelectQuestionsController.indexForTopic));//topic
            
            String selectTopic="SELECT id FROM topics WHERE topic = \'"+topic.getText()+"\'";
            ResultSet getTopicId = dh.executeQuery(selectTopic);
            
            getTopicId.next();
            
            String query = "SELECT id FROM questions WHERE topicId = "+getTopicId.getInt(1);
            ResultSet resultSet = dh.executeQuery(query);
            
            List<Integer> questionsId=new ArrayList<>();
            
            while(resultSet.next()){
               questionsId.add(Integer.valueOf(resultSet.getString(1)));
            }
            
            if(id.size()!=questionsId.size()){
                for(int i=0;i<ContestantsReg.parameters.get(0);i++){
                    int randomizeId=(int)(Math.random()*questionsId.size());

                    if(!id.contains(randomizeId)){
                        id.add(randomizeId);

                        query = "SELECT * FROM questions WHERE topicId = "+getTopicId.getInt(1);//gets the first string from the column id in the topics table and uses it in the questions table
                        resultSet = dh.executeQuery(query);

                        while(resultSet.next()){
                            int id=resultSet.getInt(1);
                            question.setText(resultSet.getString(2));
                            optionA.setText("A.  "+resultSet.getString(3));
                            optionB.setText("B.  "+resultSet.getString(4));
                            optionC.setText("C.  "+resultSet.getString(5));
                            optionD.setText("D.  "+resultSet.getString(6));
                            answer=resultSet.getString(7);
                            
                            switch(answer.toLowerCase()){
                                case "a": fullAnswer = resultSet.getString(3);
                                break;
                                case "b": fullAnswer = resultSet.getString(4);
                                break;
                                case "c": fullAnswer = resultSet.getString(5);
                                break;
                                case "d": fullAnswer = resultSet.getString(6);
                                break;
                            }
                            
                            if(id==questionsId.get(randomizeId))
                                break;
                        }

                        break;
                    }
                }
            }
            else{
                id=new ArrayList<>();
            }
            
            timer();
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
    
    void timer(){
        trd=new Thread(() -> {
            int timer=ContestantsReg.parameters.get(1);
            
            if(timer>0){
                labelTimer.setStyle("-fx-font-weight: bolder;-fx-background-color:rgb(250,82,82);-fx-font-size:20px");
                
                for (i = timer+1; i >=1; i--) {
                    try {
                        String labelTime=String.valueOf(i);
                        
                        Platform.runLater(() -> {
                            labelTimer.setText(labelTime);
                        });
                        
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(QuestionsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                Platform.runLater(() -> {
                    try {
                        if(ifClicked){
                            trd.interrupt();
                            ifClicked=false;
                        }
                        else{
                            ContestantsReg.questionStage.close();
                            ContestantsRegController.Reg.correctAnswerWindow();
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(QuestionsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        });
        
        trd.start();
    }

    @FXML
    private void back(ActionEvent event) throws IOException, Exception {
        
        Alert alert=new Alert(AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("CONFIRM TO CONTINUE");
        alert.setContentText("THE CONTESTANT WILL LOSE THIS ROUND!");
        
        Optional<ButtonType> confirm=alert.showAndWait();
        
        if(confirm.get()==ButtonType.OK){
            System.out.println(SelectQuestionsController.indexForTopic+" "+(ContestantsReg.topics.size()-1));
            if(lastButtonClick!=nOfQuest){
                ContestantsReg.selectQuestionStage.show();
            }
            else{
                if(SelectQuestionsController.indexForTopic!=ContestantsReg.topics.size()-1){
                     lastButtonClick=0;
                     SelectQuestionsController.indexForTopic++;
                     //if(!ContestantsRegController.randomTopBool)
                    //sqc.setTopic(ContestantsReg.topics.get(indexForTopic));
                     ContestantsRegController.Reg.selectQestionWindow();
                }
                else{
                    MainScreen mainScreen = new MainScreen();
                    MainScreen.notFirstTime = true;
                    mainScreen.start(new Stage());
                }
            }
            
            ContestantsReg.questionStage.close();
        }
        else{
            alert.close();
        }
    }

    @FXML
    private void next(ActionEvent event) throws IOException {
         ContestantsRegController.Reg.correctAnswerWindow();
         ContestantsReg.questionStage.close();
         ifClicked=true;
    }
    
    void retrieveAllQuestions(){
        List<Integer> ids = new ArrayList<>();
        for (int j = 0; j < ContestantsRegController.selectedTopics.size(); j++) {
            ids.add(ContestantsRegController.selectedTopics.get(i).getId());
        }
        
        
    }
    
}
