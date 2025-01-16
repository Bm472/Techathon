package uk.co.robertwalters.techathon;

import data.Database;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;

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
    protected void onLoginButtonClick() throws SQLException {
        ResultSet resultSet = Database.verifyLogin(username.getText(), password.getText());
        if(username.getText().isEmpty() || password.getText().isEmpty()) {
            errorMessage.setText("Username and password are required");
            errorMessage.setVisible(true);
        }
        else if(resultSet.next()) {
            HelloApplication.customerID = resultSet.getInt("customerID");
            HelloApplication.firstName = resultSet.getString("firstName");
            HelloApplication.lastName = resultSet.getString("lastName");
            if(Database.initialiseProgress(HelloApplication.customerID)) {
                HelloApplication.setHomeScene();
            }
            else {
                errorMessage.setText("Error initialising customer progress");
                errorMessage.setVisible(true);
            }
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