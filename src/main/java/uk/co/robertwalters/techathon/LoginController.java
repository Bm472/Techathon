package uk.co.robertwalters.techathon;

import data.Database;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private Label welcomeText;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private DatePicker dob;
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label errorMessage;

    @FXML
    protected void onLoginButtonClick() {
        if(username.getText().isEmpty() || password.getText().isEmpty()) {
            errorMessage.setText("Username and password are required");
            errorMessage.setVisible(true);
        }
        else if(Database.verifyLogin(username.getText(), password.getText())) {
            HelloApplication.setHomeScene();
        }
        else {
            errorMessage.setText("Invalid Username or Password");
            errorMessage.setVisible(true);
        }
    }

    @FXML
    protected void onRegisterButtonClick() {
        HelloApplication.setRegistrationScene();
    }
}