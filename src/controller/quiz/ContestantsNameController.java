package controller.quiz;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseHandler;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ContestantsNameController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private JFXTextField txtFieldcontestantName;
    @FXML
    private JFXButton Finish;
    @FXML
    private Label contestantsCount;
    
    private Stage primaryStage;

    private final Stack<String> stack=new Stack<>();
        
    int index=0;
    DatabaseHandler dh;
    @FXML
    private ListView<String> contsTable;
    @FXML
    private JFXButton delete;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showContestants();
    }    

    @FXML
    private void finish(ActionEvent event) throws FileNotFoundException, IOException {
        String cont = txtFieldcontestantName.getText();
        if(cont.isEmpty()) return;
        contsTable.getItems().add(cont);
        ContestantsRegController.contestants.add(cont);
        txtFieldcontestantName.clear();
        txtFieldcontestantName.requestFocus();
    }

    @FXML
    private void Delete(ActionEvent event) {
        index = contsTable.getSelectionModel().getSelectedIndex();
        if(index < 0) return;
        contsTable.getItems().remove(index);
        ContestantsRegController.contestants.remove(index);
    }

    private void showContestants() {
        ObservableList<String> contList = FXCollections.<String>observableArrayList();
        
        for (int i = 0; i < ContestantsRegController.contestants.size(); i++) {
            contList.add(ContestantsRegController.contestants.get(i));
        }
        
        contsTable.setItems(contList);
    }

    
}
