/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.questions;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import database.DatabaseHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import model.Question;

/**
 * FXML Controller class
 *
 * @author YoungDON
 */
public class AddQuestionController implements Initializable {

    @FXML
    private JFXTextArea questionsTxt;
    @FXML
    private JFXTextArea optionATxt;
    @FXML
    private JFXTextArea optionBTxt;
    @FXML
    private JFXTextArea optionCTxt;
    @FXML
    private JFXTextArea optionDTxt;
    @FXML
    private ToggleGroup OptionsGroup;
    @FXML
    private JFXButton addQuestion;
    public static boolean isEdit;
    @FXML
    private JFXRadioButton optionA;
    @FXML
    private JFXRadioButton optionB;
    @FXML
    private JFXRadioButton optionC;
    @FXML
    private JFXRadioButton optionD;
    private QuestionsScreenController qsc;
    private DatabaseHandler dh;
    EditquestionsController ec;
    static Question quest;
    static int pos;
    static int topicID;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dh = QuestionsScreenController.dh;
        if(isEdit){
            questionsTxt.setText(quest.getQuestion());
            optionATxt.setText(quest.getOptionA());
            optionBTxt.setText(quest.getOptionB());
            optionCTxt.setText(quest.getOptionC());
            optionDTxt.setText(quest.getOptionD());
            switch (quest.getAnswer()) {
                case 'a':
                    optionA.setSelected(true);
                    break;
                case 'b':
                    optionB.setSelected(true);
                    break;
                case 'c':
                    optionC.setSelected(true);
                    break;
                case 'd':
                    optionD.setSelected(true);
                    break;
                default:
                    throw new AssertionError();
            }
            addQuestion.setText("Edit question");
        }
    }    

    @FXML
    private void AddQuestion(ActionEvent event) {
        String question = questionsTxt.getText();
        String a = optionATxt.getText();
        String b = optionBTxt.getText();
        String c = optionCTxt.getText();
        String d = optionDTxt.getText();
        String ans = "";
        if(optionA.isSelected())
            ans = "a";
        else if(optionB.isSelected())
            ans = "b";
        else if(optionC.isSelected())
            ans = "c";
        else if(optionD.isSelected())
            ans = "d";
        
        if(question.isEmpty() || a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty() || ans.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("The question doesn't have all the fields filled.");
            alert.show();
            return;
        }
        
        boolean success = false;
        if(!isEdit){
            String update = "INSERT INTO questions VALUES(0, \""
                    + question +"\", \""
                    + a + "\", \"" + b + "\", \"" + c + "\", \"" + d + "\", \"" + ans + "\","
                    + Integer.toString(topicID) + ")";
            success = dh.executeUpdate(update);
        }else{
            String update = "UPDATE questions SET question = \""
                    + question + "\","
                    + "optionA = \"" + a + "\","
                    + "optionB = \"" + b + "\","
                    + "optionC = \"" + c + "\","
                    + "optionD = \"" + d + "\","
                    + "answer = \"" + ans + "\""
                    + "WHERE id = " + Integer.toString(quest.getID());
            success = dh.executeUpdate(update);
            quest = new Question(quest.getID(), question, a, b, c, d, ans.charAt(0), quest.getTopicID());
            ec.refresh(quest, pos);
        }
        if(!success) return;
        qsc.refresh();
        Loader.addQuestionStage.close();
    }

    void setQSC(QuestionsScreenController qsc) {
        this.qsc = qsc;
    }
    
    void setEc(EditquestionsController ec){
        this.ec = ec;
    }

    public static void setQuestion(Question question, int ind) {
        AddQuestionController.quest = question;
        pos = ind;
    }
    
}
