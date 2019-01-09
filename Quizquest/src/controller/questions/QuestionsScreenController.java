/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.questions;

import com.jfoenix.controls.JFXTextField;
import controller.mainscreen.MainScreen;
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
import java.util.stream.Stream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import model.Question;
import model.Topic;

/**
 * FXML Controller class
 *
 * @author YoungDON
 */
public class QuestionsScreenController implements Initializable {

    @FXML
    private VBox topicsBox;
    @FXML
    private Label topicLbl;
    @FXML
    private VBox questionsBox;
    final int butWidth = 220, butHeight = 50;
    int selected = 0, realSel = 0;
    List<Integer> realTops;
    @FXML
    private JFXTextField searchTopicTxt;
    @FXML
    private Label noTopicsLbl;
    @FXML
    private JFXTextField searchQuestionTxt;
    @FXML
    private Label noQuestionsLbl;
    @FXML
    private ScrollPane topicsScrollPane;
    private Loader loader;
    public List<List<Question>> questions;
    public List<Topic> topics;
    public static DatabaseHandler dh;
    @FXML
    private MenuItem renameTMenu;
    @FXML
    private MenuItem editQMenu;
    @FXML
    private MenuItem deleteTMenu;
    @FXML
    private MenuItem deleteQMenu;
    @FXML
    private MenuItem deleteMultiQMenu;
    @FXML
    private MenuItem addQMenu;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        questionsBox.getChildren().clear();
        topicsBox.getChildren().clear();
        dh = new DatabaseHandler();
        dh.executeUpdate("USE " + MainScreen.DATABASENAME);
        retrieveData();
        if(topics.isEmpty())
            emptyDatabase();
        else{
            List<Question> quests = questions.get(selected);
            instantiateTopics(topics);
            instantiateQuestions(selected, quests);
        }
    }

    void instantiateTopics(List<Topic> topics){
        topicsBox.getChildren().clear();
        for (int i = 0; i < topics.size(); i++) {
            Button button = new Button(topics.get(i).getTopic());
            button.setPrefWidth(butWidth);
            button.setPrefHeight(butHeight);
            if(i == selected){
                button.setStyle("-fx-background-color: #fa5252; -fx-font-size: 15;");
                button.setTextFill(Paint.valueOf("#ffffff"));
            }else{
                button.setStyle("-fx-font-size: 15");
                button.setTextFill(Paint.valueOf("#fa5252"));
            }
            button.setOnMouseClicked((MouseEvent event) -> {
                if(selected != topicsBox.getChildren().indexOf((Button)button)){
                    button.setStyle("-fx-background-color: #fa5252; -fx-font-size: 15;");
                    button.setTextFill(Paint.valueOf("#ffffff"));
                    ((Button)topicsBox.getChildren().get(selected)).setStyle("-fx-font-size: 15");
                    ((Button)topicsBox.getChildren().get(selected)).setTextFill(Paint.valueOf("#fa5252"));
                    selected = topicsBox.getChildren().indexOf((Button)button);
                    topicLbl.setText(topics.get(selected).getTopic());
                    List<Question> quests = realTops == null || realTops.isEmpty() ? questions.get(selected) : questions.get(realTops.get(selected));
                    instantiateQuestions(selected, quests);
                }
            });
            topicsBox.getChildren().add(button);
        }
        topicLbl.setText(topics.get(selected).getTopic());
        if(this.topics.isEmpty()){
            emptyDatabase();
        }else{
            notEmptyDatabase();
        }
    }
    
    public void instantiateQuestions(int selected, List<Question> quests){
        questionsBox.getChildren().clear();
        for (int i = 0; i < quests.size(); i++) {
            String text = Integer.toString(i + 1) + ".\t" + quests.get(i).getQuestion() + "\n"
                    + "\tA.\t" + quests.get(i).getOptionA()
                    + "\n\tB.\t" + quests.get(i).getOptionB()
                    + "\n\tC.\t" + quests.get(i).getOptionC()
                    + "\n\tD.\t" + quests.get(i).getOptionD()
                    + "\nAnswer: " + Character.toString(quests.get(i).getAnswer()).toUpperCase();
            Text question = new Text(text);
            question.setWrappingWidth(1000);
            question.setStyle("-fx-font-size: 20px");
            questionsBox.getChildren().add(question);
            if(i != quests.size()){
                //Seperator
            }
        }
        if(questions.get(selected).isEmpty())
            emptyTopic();
        else
            notEmptyTopic();
    }

    void emptyDatabase(){
        topicsScrollPane.setVisible(false);
        questionsBox.setVisible(false);
        noTopicsLbl.setVisible(true);
        noTopicsLbl.setDisable(false);
        noQuestionsLbl.setVisible(true);
        noQuestionsLbl.setDisable(false);
        noQuestionsLbl.setText(noTopicsLbl.getText());
        topicLbl.setText("No topics and questions");
        searchQuestionTxt.setDisable(true);
        searchTopicTxt.setDisable(true);
        editQMenu.setDisable(true);
        renameTMenu.setDisable(true);
        deleteMultiQMenu.setDisable(true);
        deleteQMenu.setDisable(true);
        deleteTMenu.setDisable(true);
        addQMenu.setDisable(true);
    }
    void notEmptyDatabase(){
        topicsScrollPane.setVisible(true);
        questionsBox.setVisible(true);
        noTopicsLbl.setVisible(false);
        noTopicsLbl.setDisable(true);
        searchQuestionTxt.setDisable(false);
        searchTopicTxt.setDisable(false);
        editQMenu.setDisable(false);
        renameTMenu.setDisable(false);
        deleteMultiQMenu.setDisable(false);
        deleteQMenu.setDisable(false);
        deleteTMenu.setDisable(false);
        addQMenu.setDisable(false);
    }
    
    void emptyTopic(){
        questionsBox.setVisible(false);
        noQuestionsLbl.setVisible(true);
        noQuestionsLbl.setDisable(false);
        noQuestionsLbl.setText("There are no questions available for this topic. "
                + "Click the ADD menu to add questions to this topic.");
        editQMenu.setDisable(true);
        deleteQMenu.setDisable(true);
        searchQuestionTxt.setDisable(true);
        
    }
    void notEmptyTopic(){
        questionsBox.setVisible(true);
        noQuestionsLbl.setVisible(false);
        noQuestionsLbl.setDisable(true);
        editQMenu.setDisable(false);
        deleteQMenu.setDisable(false);
        searchQuestionTxt.setDisable(false);
    }
    
    public void retrieveData(){
        String query = "SELECT * FROM topics";
        ResultSet results = dh.executeQuery(query);
        topics = new ArrayList<>();
        try {
            while(results.next()){
                topics.add(new Topic(results.getInt(1), results.getString(2)));
            }
        } catch (SQLException ex) {
            //Logger.getLogger(QuestionsScreenController.class.getName()).log(Level.SEVERE, null, ex);
            showError(ex);
        }
        
        questions = new ArrayList<>();
        try {
            for (int i = 0; i < topics.size(); i++) {
                List<Question> quests = new ArrayList<>();
                query = "SELECT * FROM questions WHERE questions.topicID = " + Integer.toString(topics.get(i).getId());
                results = dh.executeQuery(query);
                while(results.next()){
                Question question = new Question(
                        results.getInt(1),
                        results.getString(2), 
                        results.getString(3), 
                        results.getString(4), 
                        results.getString(5), 
                        results.getString(6), 
                        results.getString(7).charAt(0),
                        results.getInt(8));
                quests.add(question);
                }
                questions.add(quests);
            }
        } catch (SQLException ex) {
            //Logger.getLogger(QuestionsScreenController.class.getName()).log(Level.SEVERE, null, ex);
            showError(ex);
        }
    }
    
    public void refresh(){
        retrieveData();
        if(topics.isEmpty()){
            emptyDatabase();
            return;
        }
        selected = Integer.min(selected, topics.size() - 1);
        List<Question> quests = questions.get(selected);
        instantiateTopics(topics);
        instantiateQuestions(selected, quests);
    }
    
    @FXML
    private void AddTopic(ActionEvent event) throws IOException {
        AddTopicController.isRename = false;
        loader.showAddTopicWindow();
    }

    @FXML
    private void AddQuestion(ActionEvent event) throws IOException {
        AddQuestionController.isEdit = false;
        AddQuestionController.topicID = topics.get(selected).getId();
        loader.showAddQuestionWindow();
    }

    @FXML
    private void RenameTopic(ActionEvent event) throws IOException {
         AddTopicController.isRename = true;
         new AddTopicController().setTopicIDandOldTopic(topics.get(selected).getId(), topics.get(selected).getTopic());
        loader.showAddTopicWindow();
    }

    @FXML
    private void EditQuestion(ActionEvent event) throws IOException{
        EditquestionsController.setTopicIDandQuestions(topics.get(selected).getId(), questions.get(selected));
        loader.showEditQuestionsWindow();
    }

    @FXML
    private void DeleteTopic(ActionEvent event) {
        String topic = topics.get(selected).getTopic();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + topic + "?", ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> showAndWait = alert.showAndWait();
        if(showAndWait.get() == ButtonType.OK){
            String update = "DELETE FROM topics WHERE id = " + Integer.toString(topics.get(selected).getId());
            String update2 = "DELETE FROM questions WHERE questions.topicID = " + Integer.toString(topics.get(selected).getId());
            if(!dh.executeUpdate(update) || !dh.executeUpdate(update2)){
                showError("Couldn't delete");
                return;
            }
            refresh();
        }else if(showAndWait.get() == ButtonType.CANCEL){
            System.out.println("Canceled");
        }
    }

    @FXML
    private void DeleteQuestion(ActionEvent event) throws IOException {
        EditquestionsController.setTopicIDandQuestions(topics.get(selected).getId(), questions.get(selected));
        loader.showEditQuestionsWindow();
    }


    void setLoader(Loader loader) {
        this.loader = loader;
    }


    @FXML
    private void DeleteMultipleTopics(ActionEvent event) throws IOException {
        DeleteTopicsController.topics = topics;
        loader.showDeleteTopicsWindow();
    }
    
    void showError(Exception ex){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ex.getClass().getName());
        alert.setContentText(ex.getMessage());
        alert.show();
    }
    void showError(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
    void showError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }

    @FXML
    private void SearchTopics(KeyEvent event) {
        List<Topic> ts = new ArrayList<>();
        String text = searchTopicTxt.getText();
        if(text.isEmpty()){
            instantiateTopics(topics);
            List<Question> quests = questions.get(selected);
            instantiateQuestions(selected, quests);
            realSel = -1;
            realTops.clear();
            return;
        }
        
        //Stream<Topic> newr = topics.stream().filter((topic) -> (topic.getTopic().startsWith(text)));
        realTops = new ArrayList<>();
        topics.stream().filter((topic) -> (topic.getTopic().toLowerCase().startsWith(text.toLowerCase()))).forEachOrdered((topic) -> {
            ts.add(topic);
            realTops.add(topics.indexOf(topic));
        });
        
        selected = 0;
        List<Question> quests = ts.isEmpty() ? new ArrayList<>() : questions.get(topics.indexOf(ts.get(0)));
        instantiateTopics(ts);
        instantiateQuestions(selected, quests);
    }

    @FXML
    private void SearchQuestions(KeyEvent event) {
        List<Question> quests = questions.get(selected);
        List<Question> qs = new ArrayList<>();
        String text = searchQuestionTxt.getText();
        if(text.isEmpty()){
            instantiateQuestions(selected, quests);
            return;
        }
        
        quests.stream().filter((quest) -> (quest.getQuestion().replaceFirst("\\d+.\\s", "").toLowerCase().startsWith(text.toLowerCase()))).forEachOrdered((quest) -> {
            qs.add(quest);
        });
        
        instantiateQuestions(selected, qs);
    }
}
