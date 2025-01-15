package uk.co.robertwalters.techathon;

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
    private PasswordField confirmPassword;

    @FXML
    private Label errorMessage;

    @FXML
    protected void onLoginButtonClick() {
        boolean test = true;

        /*if(Database.verifyLogin(username.getText(),password.getText())) {

        }*/
        if(test) {
            errorMessage.setText("Valid Username or Password");
            errorMessage.setVisible(true);
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