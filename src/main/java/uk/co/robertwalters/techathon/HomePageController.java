package uk.co.robertwalters.techathon;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
/*import javafx.scene.layout.*/
import javafx.scene.control.TextField;

public class HomePageController {
    @FXML
    private Button Budget;

    @FXML
    private Button Investing;

    @FXML
    private Button Mortgages;

    @FXML
    private ButtonBar Home;
    @FXML
    private Button Pensions;

    @FXML
    private ButtonBar Quizzes;


    @FXML
    private ButtonBar Performance;

    @FXML
    private ButtonBar Topics;

    @FXML
    private Label search;

    @FXML
    private TextField searchTopics;

    @FXML
    protected void onBudgetButtonClick() {
        boolean test = true;

        /*if(Database.verifyLogin(username.getText(),password.getText())) {

        }*/
        /*
        if(test) {
            errorMessage.setText("Valid Username or Password");
            errorMessage.setVisible(true);
        }
        else {
            errorMessage.setText("Invalid Username or Password");
            errorMessage.setVisible(true);
        }
    }

}
