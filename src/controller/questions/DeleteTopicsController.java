/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.questions;

import com.jfoenix.controls.JFXButton;
import database.DatabaseHandler;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import model.Topic;

/**
 * FXML Controller class
 *
 * @author YoungDON
 */
public class DeleteTopicsController implements Initializable {

    @FXML
    private ListView<String> topicsTable;
    private Loader loader;
    @FXML
    private JFXButton editBut;
    public static List<Topic> topics;
    private DatabaseHandler dh;
    public QuestionsScreenController qsc;
    public static QuestionsScreenController qsc2;
    private AddTopicController atc;
    @FXML
    private JFXButton deleteBut;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loader = new Loader();
        dh = QuestionsScreenController.dh;
        editBut.setDisable(true);
        deleteBut.setDisable(true);
        showTopics();
    }    

    void showTopics(){
        ObservableList<String> list = FXCollections.<String>observableArrayList();
        
        for (int i = 0; i < topics.size(); i++) {
            String text = Integer.toString(i + 1) + ".\t" + topics.get(i).getTopic();
            list.add(text);
        }
        
        topicsTable.setItems(list);
        topicsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        topicsTable.setOnMouseClicked((MouseEvent event) -> {
            deleteBut.setDisable(false);
            if(topicsTable.getSelectionModel().getSelectedItems().size() > 1)
                editBut.setDisable(true);
            else
                editBut.setDisable(false);
        });
    }
    
    void refresh(int pos, String newTopic){
        Topic t = new Topic(topics.get(pos).getId(), newTopic);
        topics.set(pos, t);
        showTopics();
    }
    
    @FXML
    private void Edit(ActionEvent event) throws IOException {
        AddTopicController.isRename = true;
        AddTopicController atc = new AddTopicController();
        atc.setTopicIDandOldTopic(topics.get(topicsTable.getSelectionModel().getSelectedIndex()).getId(),
                topics.get(topicsTable.getSelectionModel().getSelectedIndex()).getTopic());
        AddTopicController.qsc2 = qsc;
        AddTopicController.pos = topicsTable.getSelectionModel().getSelectedIndex();
        AddTopicController.dtc = this;
        loader.showAddTopicWindow();
    }

    @FXML
    private void Delete(ActionEvent event) {
        String q = topicsTable.getSelectionModel().getSelectedIndices().size() > 1 ? "topics " : "topic ";
        
        int[] selected = new int[topicsTable.getSelectionModel().getSelectedIndices().size()];
        for (int i = 0; i < selected.length; i++) {
            selected[i] = topicsTable.getSelectionModel().getSelectedIndices().get(i);
            if(i != selected.length - 1)
                q += (selected[i] + 1) + ", ";
            else
                q += "and " + (selected[i] + 1);
        }
        if(selected.length == 1) q = "topic " + Integer.toString(topicsTable.getSelectionModel().getSelectedIndex() + 1);
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + q + "?", ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> showAndWait = alert.showAndWait();
        if(showAndWait.get() == ButtonType.OK){
            List<Topic> rem = new ArrayList<>();
            for (int i = 0; i < selected.length; i++) {
                String update = "DELETE FROM topics WHERE id = " + Integer.toString(topics.get(selected[i]).getId());
                String update2 = "DELETE FROM questions WHERE questions.topicID = " + Integer.toString(topics.get(selected[i]).getId());
                if(!dh.executeUpdate(update) || !dh.executeUpdate(update2)){
                    System.out.println("Couldn't delete");
                    return;
                }
                rem.add(topics.get(selected[i]));
            }
            topics.removeAll(rem);
            showTopics();
            qsc.refresh();
        }else if(showAndWait.get() == ButtonType.CANCEL){
            System.out.println("Canceled");
        }
    }

    private void Cancel(ActionEvent event) throws IOException {
        Loader.deleteTopicsStage.close();
    }
    void setQsc(QuestionsScreenController qsc){
        this.qsc = qsc;
    }
    void setAtc(AddTopicController atc){
        this.atc = atc;
    }
}
