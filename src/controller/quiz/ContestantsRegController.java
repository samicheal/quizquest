package controller.quiz;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseHandler;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Topic;

public class ContestantsRegController implements Initializable {

    @FXML
    private JFXButton selecttopics;
    @FXML
    private JFXButton Contestants;
    
    @FXML
    private JFXTextField txtFieldNoOfQuestions;
    private JFXTextField txtFieldNoOfRounds;
    @FXML
    private JFXTextField txtFieldTime;
    @FXML
    private JFXTextField txtFieldPointsPerQuestion;
    @FXML
    private JFXCheckBox timer;
    @FXML
    private JFXTextField txtFieldBonus;
    @FXML
    private JFXCheckBox bonusPoints;
    @FXML
    private JFXButton creatematch;

    public static ContestantsReg Reg;
       
    @FXML
    private AnchorPane anchorPane;
    private Label contestantError;
    
    @FXML
    private Label errorForTimer;
    @FXML
    private Label errorForBonus;
    
    private Stage primaryStage;
    @FXML
    private JFXCheckBox randomTopics;
    @FXML
    private Label errorForQuestions;
    @FXML
    private Label pointsPerQuestionError;
    public static DatabaseHandler dh;
    public static List<Topic> selectedTopics;
    public static List<Integer> selectedTopicsInd;
    public  static List<String> contestants;
    public static boolean randomTopBool;
    
    public void setReg(ContestantsReg Reg,Stage primaryStage){
        this.Reg=Reg;
        this.primaryStage=primaryStage;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dh = new DatabaseHandler();
        contestants = new ArrayList<>();
        selectedTopics = new ArrayList<>();
    }    

    @FXML
    private void selectTopics(ActionEvent event) throws IOException {
        Reg.selectTopicsWindow();
    }

    @FXML
    private void setTimer(ActionEvent event) {
        if(txtFieldTime.isDisable())
            txtFieldTime.setDisable(false);
        else
            txtFieldTime.setDisable(true);
    }

    @FXML
    private void awardBonus(ActionEvent event) {
        if(txtFieldBonus.isDisable())
            txtFieldBonus.setDisable(false);
        else
            txtFieldBonus.setDisable(true);
    }
    
    @FXML
    private void randomizeTopics(ActionEvent event) {
        randomTopBool = !randomTopBool;
        
        if(randomTopBool)
            txtFieldNoOfQuestions.setPromptText("Total Number of Questions");
        else{
            txtFieldNoOfQuestions.setPromptText("No of Questions");
        }
    }
    
    @FXML
    private void contestants(ActionEvent event) throws IOException {
        Reg.contestantNamesWindow();
    }
    
    @FXML
    private void createMatch(ActionEvent event) throws FileNotFoundException, IOException, SQLException {
        String noOfQuest=txtFieldNoOfQuestions.getText();
        String time=txtFieldTime.getText();
        String pointsPerQuest=txtFieldPointsPerQuestion.getText();
        String bonus=txtFieldBonus.getText();
        int moveToNextWindow=0;
        
        //clears the error labels
        errorForQuestions.setText("");
        pointsPerQuestionError.setText("");
        errorForTimer.setText("");
        errorForBonus.setText("");
        
        ContestantsReg.parameters=new ArrayList<>();
        
        //for no of questions field
        if(noOfQuest.isEmpty()){
            errorForQuestions.setText("Number of questions field cannot be empty");
        }
        else if(!noOfQuest.matches("[\\d]*")){
            errorForQuestions.setText("No of questions field must be a number");
        }
        else{
             ContestantsReg.parameters.add(Integer.valueOf(noOfQuest));
             moveToNextWindow++;
        }
        
        //for timer textField and textBox
        if(timer.isSelected()){
            if(time.isEmpty()){
                errorForTimer.setText("Time in seconds field cannot be empty");
            }
            else if(!time.matches("[\\d]*"))
                errorForTimer.setText("Time in seconds field must be a number");
            else{
                ContestantsReg.parameters.add(Integer.valueOf(time));
                moveToNextWindow++;
            }
        }
        else{
            ContestantsReg.parameters.add(0);
            moveToNextWindow++;
        }
        
        //for points per question field
        if(pointsPerQuest.isEmpty()){
            pointsPerQuestionError.setText("points per question field cannot be empty");
        }
        else if(!pointsPerQuest.matches("[\\d]*"))
            pointsPerQuestionError.setText("Points awarded per questions field must be a number");
        else{
            ContestantsReg.parameters.add(Integer.valueOf(pointsPerQuest));
            moveToNextWindow++;
        }

        //for pointsAwardedFor textField and textBox
        if(bonusPoints.isSelected()){
            if(bonus.isEmpty()){
                errorForBonus.setText("points awarded for bonus field cannot be empty");
            }
            else if(!bonus.matches("[\\d]*"))
                errorForBonus.setText("points awarded for bonus field must be a number");
            else{
                ContestantsReg.parameters.add(Integer.valueOf(bonus));
                moveToNextWindow++;
            }
        }
        else{
            ContestantsReg.parameters.add(0);
            moveToNextWindow++;
        }
        
        int totalQuests = 0;
        if(selectedTopics != null){
            for (int i = 0; i < selectedTopics.size(); i++) {
                totalQuests += numberOfQuestions(selectedTopics.get(i));
            }
        }
        
        if(ContestantsRegController.contestants.isEmpty()){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("No Contestants Available");
                alert.setContentText("GO BACK!...and register contestants");
                
                alert.show();
        }else if(selectedTopics.isEmpty()){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Please select at least a topic");
                alert.setHeaderText("No topics selected");
                alert.show();
        }else if(totalQuests < Integer.parseInt(txtFieldNoOfQuestions.getText()) * contestants.size()){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setContentText("Not enough questions in the database. Go to the View Questions section to add"
                        + "more questions");
                alert.setHeaderText("Not enough questions");
                
                alert.show();
        }else{
            if(moveToNextWindow==4){
                    primaryStage.close();
                    Reg.selectQestionWindow();
                    //QuizQuestLogic.overWrite("", "contestants.txt");
                }
        }
        txtFieldNoOfQuestions.requestFocus();
    }
    
    int numberOfQuestions(Topic topic) throws SQLException{
        String query = "SELECT question FROM QUESTIONS WHERE questions.TOPICID = " + topic.getId();
        ResultSet set = dh.executeQuery(query);
        
        int noOfQuest = 0;
        while(set.next()){
            noOfQuest++;
        }
        
        return noOfQuest;
        
    }
}
