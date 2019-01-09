/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.questions;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author YoungDON
 */
public class AddTopicController implements Initializable {

    @FXML
    private JFXTextField topicTxt;
    public static boolean isRename;
    @FXML
    private JFXButton addTopicBut;
    private DatabaseHandler dh;
    private QuestionsScreenController qsc;
    public static QuestionsScreenController qsc2;
    public static DeleteTopicsController dtc;
    static String oldTopic;
    static int topicID;
    static int pos;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if(isRename){
            topicTxt.setText(oldTopic);
            topicTxt.setPromptText("Enter new title");
            addTopicBut.setText("Rename topic");
        }
        dh = QuestionsScreenController.dh;
    }    

    
    @FXML
    private void AddTopic(ActionEvent event) {
        String topic = topicTxt.getText();
        
        if(topic.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Topic title cannot be empty.");
            alert.show();
            return;
        }
        
        boolean success;
        if(!isRename){
            success = dh.executeUpdate("INSERT INTO TOPICS VALUES(0, '" + topic + "')");
        }else{
            success = dh.executeUpdate("UPDATE topics SET topic = \"" + topic + "\" WHERE id = " + Integer.toString(topicID) + "");
        }
        if(!success){
                return;
            }
        if(qsc != null)
            qsc.refresh();
        else{
            qsc2.refresh();
            dtc.refresh(pos, topic);
        }
        Loader.addTopicStage.close();
    }

    void setQSC(QuestionsScreenController aThis) {
        qsc = aThis;
    }
    

    void setTopicIDandOldTopic(int topicID, String oldTopic) {
        AddTopicController.topicID = topicID;
        AddTopicController.oldTopic = oldTopic;
    }
}
