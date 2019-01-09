package controller.quiz;


import com.jfoenix.controls.JFXButton;
import controller.mainscreen.MainScreen;
import static controller.quiz.SelectQuestionsController.lastButtonClick;
import static controller.quiz.SelectQuestionsController.nOfQuest;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.ScoreBoard;

public class ScoreBoardController implements Initializable {

    @FXML
    private Label topic;
    @FXML
    private TableView<ScoreBoard> tableView;
    @FXML
    private TableColumn<ScoreBoard,String> tableViewContestant;
    @FXML
    private TableColumn<ScoreBoard,Integer> tableViewPoints;
    @FXML
    private TableColumn<ScoreBoard,Integer> tableViewBonus;
    @FXML
    private TableColumn<ScoreBoard,Integer> tableViewTotal;
   
    private ObservableList<ScoreBoard> scoreBoardList=FXCollections.observableArrayList();
    SelectQuestionsController sqc;
    
    @FXML
    private JFXButton Close;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableViewContestant.setCellValueFactory(new PropertyValueFactory<>("contestant"));
        tableViewPoints.setCellValueFactory(new PropertyValueFactory<>("points"));
        tableViewBonus.setCellValueFactory(new PropertyValueFactory<>("bonus"));
        tableViewTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        
        setView();
        
        tableView.setItems(scoreBoardList);
        tableView.setStyle("-fx-background-color:rgb(255,100,100)");
        
        
        tableViewContestant.setStyle("-fx-border-color: rgb(0,0,0);-fx-text-fill:rgb(255,255,255);-fx-background-color:rgb(210,210,210);-fx-font-size:20px");
        tableViewPoints.setStyle("-fx-border-color: rgb(0,0,0);-fx-text-fill:rgb(255,255,255);-fx-background-color:rgb(210,210,210);-fx-font-size:20px");
        tableViewBonus.setStyle("-fx-border-color: rgb(0,0,0);-fx-text-fill:rgb(255,255,255);-fx-background-color:rgb(210,210,210);-fx-font-size:20px");
        tableViewTotal.setStyle("-fx-border-color: rgb(0,0,0);-fx-text-fill:rgb(255,255,255);-fx-background-color:rgb(210,210,210);-fx-font-size:20px");
        
        topic.setText(ContestantsReg.selectQuestion.get(0));
    }    

    public void setView(){
        for (int i = 0; i < ContestantsRegController.contestants.size(); i++) {
            String contestant=ContestantsRegController.contestants.get(i);
            int point=ContestantsReg.points.get(i);
            int bonus=ContestantsReg.bonus.get(i);
            int total=ContestantsReg.points.get(i)+ContestantsReg.bonus.get(i);
            
            scoreBoardList.add(new ScoreBoard(contestant, point, bonus, total));
        }
    }
    
    @FXML
    private void close(ActionEvent event) throws IOException, Exception {
        if(lastButtonClick!=nOfQuest){
            ContestantsReg.selectQuestionStage.show();
        }
        else{
            if(SelectQuestionsController.indexForTopic!=ContestantsReg.topics.size()-1){
                 lastButtonClick=0;
                 SelectQuestionsController.indexForTopic++;
                 ContestantsRegController.Reg.selectQestionWindow();
            }
            else{
                MainScreen mainScreen = new MainScreen();
                MainScreen.notFirstTime = true;
                mainScreen.start(new Stage());
            }
        }
                
        ContestantsReg.boardStage.close();
    }


}
