/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.quiz;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import database.DatabaseHandler;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import model.Topic;

public class SelectTopicsController implements Initializable {

    @FXML
    private JFXListView<String> listView;
    @FXML
    private JFXButton okbutton;
    public List<Integer> selected;
    DatabaseHandler dh;
    List<Topic> topics;
    public static List<Topic> selectedTopics;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            dh = ContestantsRegController.dh;
            topics = new ArrayList<>();
            
            String query = "SELECT * FROM TOPICS";
            ResultSet set = dh.executeQuery(query);
            
            while(set.next()){
                topics.add(new Topic(set.getInt(1), set.getString(2)));
            }
            
            showTopics();
        } catch (SQLException ex) {
            Logger.getLogger(SelectTopicsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void Ok(ActionEvent event) {
        ContestantsRegController.selectedTopics = new ArrayList<>();
        ContestantsRegController.selectedTopicsInd = new ArrayList<>();
        
        for (int i = 0; i < listView.getSelectionModel().getSelectedIndices().size(); i++) {
            System.out.println(listView.getSelectionModel().getSelectedIndices().get(i));
            if(listView.getSelectionModel().getSelectedIndices().get(i) < 0) continue;
            ContestantsRegController.selectedTopics.add(topics.get(listView.getSelectionModel().getSelectedIndices().get(i)));
            ContestantsRegController.selectedTopicsInd.add(listView.getSelectionModel().getSelectedIndices().get(i));
        }
        ContestantsReg.secondStag.close();
    }
    
    void showTopics() throws SQLException{
        ObservableList<String> list = FXCollections.<String>observableArrayList();
        
        for (int i = 0; i < topics.size(); i++) {
            list.add(topics.get(i).getTopic());
        }
        
        listView.setItems(list);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        if(ContestantsRegController.selectedTopicsInd != null)
        for (int i = 0; i < ContestantsRegController.selectedTopicsInd.size(); i++) {
            listView.getSelectionModel().select(ContestantsRegController.selectedTopicsInd.get(i));
        }
        
    }
    
}
