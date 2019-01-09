package controller.quiz;


import com.jfoenix.controls.JFXButton;
import controller.mainscreen.MainScreen;
import static controller.quiz.ContestantsReg.indexForTopic;
import static controller.quiz.SelectQuestionsController.lastButtonClick;
import static controller.quiz.SelectQuestionsController.nOfQuest;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CorrectAnswerController implements Initializable {

    @FXML
    private Label answer;
    @FXML
    private Label questionNo;
    @FXML
    private JFXButton Right;
    @FXML
    private JFXButton Wrong;
    @FXML
    private ComboBox<String> Select;
    @FXML
    private JFXButton Bonus;
    @FXML
    private JFXButton Exit;
    @FXML
    private Label contestant;

    private String bonusContestant="";
    
    private int contestantIndex;
    
    private int getElement;
    
    private boolean ifClicked=false;
    @FXML
    private JFXButton scores;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        questionNo.setText(ContestantsReg.selectQuestion.get(1));//question Number
        contestant.setText(ContestantsReg.selectQuestion.get(0));//name
        answer.setText(QuestionsController.answer + ". " + QuestionsController.fullAnswer);
        
        Bonus.setDisable(true);
        
        for(String i:ContestantsRegController.contestants){
            if(!i.equals(contestant.getText()))
                Select.getItems().add(i);
            
            Select.setOnAction((ActionEvent event1) -> {
                bonusContestant=Select.getSelectionModel().getSelectedItem();
                Bonus.setDisable(false);
            });
        }
        if(ContestantsReg.parameters.get(3)==0){
            Select.setDisable(true);
        }
        
        //fills up the list bonus and points with zero on the first run
        if(ContestantsReg.bonus.isEmpty() && ContestantsReg.points.isEmpty()){
            for (int i = 0; i < ContestantsRegController.contestants.size(); i++) { 
                ContestantsReg.bonus.add(0);
                ContestantsReg.points.add(0); 
            }
        }
        
        if(ContestantsReg.parameters.get(3)<=0){
            Bonus.setDisable(true);
        }
    }    

    @FXML
    private void right(ActionEvent event) {
        //getting the index of the current contestant
        contestantIndex=ContestantsRegController.contestants.indexOf(contestant.getText());
                
        getElement=ContestantsReg.points.get(contestantIndex);

        ContestantsReg.points.remove(contestantIndex);
        ContestantsReg.points.add(contestantIndex,ContestantsReg.parameters.get(2)+getElement);
        
        Right.setDisable(true);
        
        ifClicked=true;
        
        scores.setDisable(false);
    }

    @FXML
    private void wrong(ActionEvent event) {
        if(ifClicked){
            Right.setDisable(false);
            
            contestantIndex=ContestantsRegController.contestants.indexOf(contestant.getText());
                
            getElement=ContestantsReg.points.get(contestantIndex);

            ContestantsReg.points.remove(contestantIndex);
            ContestantsReg.points.add(contestantIndex,getElement-ContestantsReg.parameters.get(2));
            
            ifClicked=false;
            //scores.setDisable(true);
        }
        
        if(ContestantsReg.parameters.get(3)==0){
            scores.setDisable(false);
        }
        else{
            scores.setDisable(true);
        }
    }

    @FXML
    private void select(ActionEvent event) {
    }

    @FXML
    private void bonus(ActionEvent event) {
        if(bonusContestant.isEmpty()){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Information");
            alert.setHeaderText("No Contestants Available");
            alert.setContentText("SELECT A CONTESTANT!");

            alert.show();
        }
        
        else{
            //bonus doesn't work if either it wasn't set or it was set to zero
            if(ContestantsReg.parameters.get(3)>0){
                //getting the index of the current contestant
                contestantIndex=ContestantsRegController.contestants.indexOf(bonusContestant);
                
                getElement=ContestantsReg.bonus.get(contestantIndex);
                
                ContestantsReg.bonus.remove(contestantIndex);
                ContestantsReg.bonus.add(contestantIndex,ContestantsReg.parameters.get(3)+getElement);
            }
            
            Bonus.setDisable(true);
            //Right.setDisable(true);
            //Wrong.setDisable(true);
            //scores.setDisable(false);
            if(Right.isDisable()){
                scores.setDisable(false);
            }
            else{
                scores.setDisable(true);
            }
        }
    }

    @FXML
    private void exit(ActionEvent event) throws IOException, Exception {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("CONFIRM TO CONTINUE");
        alert.setContentText("YOU CAN'T COME BACK IF YOU EXIT");
        
        Optional<ButtonType> confirm=alert.showAndWait();
        
        if(confirm.get()==ButtonType.OK){
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
            
            ContestantsReg.answerStage.close();
        }
        
        else{
            alert.close();
        }
    }

    @FXML
    private void scoreBoard(ActionEvent event) throws IOException {
        ContestantsRegController.Reg.scoreBoardWindow();
        ContestantsReg.answerStage.close();
    }
}