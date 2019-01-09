package controller.quiz;

import com.jfoenix.controls.JFXButton;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SelectQuestionsController implements Initializable {
    @FXML
    private JFXButton backButton;
    @FXML
    private Label contestant;
    
    @FXML
    private VBox vBox;
    
    static int nOfQuest=0;
    
    List<String> names1=new LinkedList<>();
    
    int limit=0;
    
    static int lastButtonClick=0;
    
    int topicCount=0;
    
    static int indexForTopic=0;
    @FXML
    private Label topicLbl;
    
    public SelectQuestionsController() throws FileNotFoundException{
        for(String names:ContestantsRegController.contestants)
            names1.add(names);
        
        nOfQuest=ContestantsReg.parameters.get(0)*names1.size();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        load();
        
        if(!names1.isEmpty())
            contestant.setText(names1.get(0));
        
        ContestantsReg.topics=new ArrayList<>();
        
        for (int i = 0; i < ContestantsRegController.selectedTopics.size(); i++) {
            ContestantsReg.topics.add(ContestantsRegController.selectedTopics.get(i).getTopic());
        } 
        
        if(!ContestantsRegController.randomTopBool)
            topicLbl.setText(ContestantsReg.topics.get(indexForTopic));
        else
            topicLbl.setText("General");
        
    }    

    @FXML
    private void back(ActionEvent event) throws IOException {
        ContestantsReg.primaryStage.show();
        
        ContestantsReg.selectQuestionStage.close();
    }
    
    //what should determine the number of buttons per load is the number of questions selected by the admin
    public void load(){
        vBox.setStyle("-fx-background-color:rgb(220,220,220)");
        
        int count=0; 
        int buttonCount=(int)(Math.ceil(nOfQuest/10)+1);
        int noOfQuestions=0;
        
        if(String.valueOf(nOfQuest).length()==1)
            noOfQuestions=nOfQuest;
        else
            noOfQuestions=(( nOfQuest%2!=0 )? (nOfQuest+1): (nOfQuest))/2;
        
        for(int i=0;i<noOfQuestions;i++){
            
            HBox hBox=new HBox();

            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setSpacing(4);
            hBox.setPadding(new Insets(0,0,0,5));
            
            for(int j=0;j<buttonCount;j++){
                Button btn=new Button(String.valueOf(count+1));

                btn.setPrefWidth(25000);
                btn.setPrefHeight(1000);
                btn.setStyle("-fx-font-weight: bolder;-fx-text-fill: rgb(0,0,0);-fx-font-size:30px;");
                
                btn.setOnMouseClicked((MouseEvent event) -> {
                    try {
                        onButtonClick(btn);
                    } catch (IOException ex) {
                        Logger.getLogger(SelectQuestionsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                
                count++;
                
                if(count>nOfQuest)
                    break;
                
                hBox.getChildren().add(btn);
            }
            
            vBox.getChildren().add(hBox);
        }
    }
    
    public void onButtonClick(Button btn) throws IOException{
        btn.setDisable(true);
        btn.setStyle("-fx-background-color: white");
        
        //increments to count the number of clicks until the last button is clicked
        lastButtonClick++;
       
        ContestantsReg.selectQuestion=new ArrayList<>();
        
         if(!ContestantsRegController.randomTopBool)
            topicLbl.setText(ContestantsReg.topics.get(indexForTopic));
        else
            topicLbl.setText("General");
        //writes the present contestant and the button number to a stack
//        ContestantsReg.selectQuestion.add(ContestantsReg.topics.get(indexForTopic));
        ContestantsReg.selectQuestion.add(names1.get(limit++));
        
        if(limit>names1.size()-1){
            limit=0;
        }
        contestant.setText(names1.get(limit));
        
        ContestantsReg.selectQuestion.add(btn.getText());
        
        //opens the Questions window
        ContestantsRegController.Reg.QuestionWindow();
        
        ContestantsReg.selectQuestionStage.hide();
    }
    
    public void setTopic(String topic){
        System.out.println(topic+" topi");
        topicLbl.setText(topic);
    }
}