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
import model.Question;

/**
 * FXML Controller class
 *
 * @author YoungDON
 */
public class EditquestionsController implements Initializable {

    @FXML
    private ListView<String> questionsTable;
    private Loader loader;
    private QuestionsScreenController qsc;
    private DatabaseHandler dh;
    static int topicID;
    public static List<Question> questions;
    @FXML
    private JFXButton editBut;
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
        loader  = new Loader();
        dh = QuestionsScreenController.dh;
        editBut.setDisable(true);
        deleteBut.setDisable(true);
        showQuestions();
    }    

    @FXML
    private void Edit(ActionEvent event) throws IOException {
        AddQuestionController.isEdit = true;
        AddQuestionController.setQuestion(questions.get(questionsTable.getSelectionModel().getSelectedIndex()), questionsTable.getSelectionModel().getSelectedIndex());
        loader.showAddQuestionWindow();
    }

    @FXML
    private void Delete(ActionEvent event) {
        String q = questionsTable.getSelectionModel().getSelectedIndices().size() > 1 ? "questions " : "question ";
        
        int[] selected = new int[questionsTable.getSelectionModel().getSelectedIndices().size()];
        for (int i = 0; i < selected.length; i++) {
            selected[i] = questionsTable.getSelectionModel().getSelectedIndices().get(i);
            if(i != selected.length - 1)
                q += (selected[i] + 1) + ", ";
            else
                q += "and " + (selected[i] + 1);
        }
        if(selected.length == 1) q = "question " + Integer.toString(questionsTable.getSelectionModel().getSelectedIndex() + 1);
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + q + "?", ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> showAndWait = alert.showAndWait();
        if(showAndWait.get() == ButtonType.OK){
            List<Question> rem = new ArrayList<>();
            for (int i = 0; i < selected.length; i++) {
                String update = "DELETE FROM questions WHERE id = " 
                        + questions.get(selected[i]).getID();
                dh.executeUpdate(update);
                rem.add(questions.get(selected[i]));
            }
            questions.removeAll(rem);
            showQuestions();
        }else if(showAndWait.get() == ButtonType.CANCEL){
            System.out.println("Canceled");
        }
    }

    @FXML
    private void Cancel(ActionEvent event) throws IOException {
        loader.showMainWindow();
    }

    private void showQuestions() {
        ObservableList<String> list = FXCollections.<String>observableArrayList();
        
        for (int i = 0; i < questions.size(); i++) {
            String text = Integer.toString(i + 1) + ".\t" + questions.get(i).getQuestion() + "\n"
                    + "\tA.\t" + questions.get(i).getOptionA()
                    + "\n\tB.\t" + questions.get(i).getOptionB()
                    + "\n\tC.\t" + questions.get(i).getOptionC()
                    + "\n\tD.\t" + questions.get(i).getOptionD();
            list.add(text);
        }
        
        //questionsTable.set
        questionsTable.setItems(list);
        questionsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        questionsTable.setOnMouseClicked((MouseEvent event) -> {
            deleteBut.setDisable(false);
            if(questionsTable.getSelectionModel().getSelectedItems().size() > 1)
                editBut.setDisable(true);
            else
                editBut.setDisable(false);
        });
    }
    
    public void refresh(Question question, int pos){
        questions.set(pos, question);
        showQuestions();
    }
        
    void setLoader(Loader loader) {
        this.loader = loader;
    }
    
    void setQSC(QuestionsScreenController qsc) {
        this.qsc = qsc;
    }

    public static void setTopicIDandQuestions(int topicID, List<Question> questions) {
        EditquestionsController.topicID = topicID;
        EditquestionsController.questions = questions;
    }

}
