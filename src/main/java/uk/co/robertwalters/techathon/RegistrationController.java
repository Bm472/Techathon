package uk.co.robertwalters.techathon;

import data.Database;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationController {

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
    protected void onSubmitButtonClick() throws SQLException {
        boolean test = true;

       if(!Database.usernameExists(username.getText())) {
           if(password.getText().length() >= 6) {
               if(confirmPassword.getText().equals(password.getText())) {
                   ResultSet results = Database.addCustomer(firstName.getText(), lastName.getText(), Date.valueOf(dob.getValue()), username.getText(), password.getText());
                   if(results != null) {
                       HelloApplication.customerID = results.getInt(1);
                       HelloApplication.firstName = firstName.getText();
                       HelloApplication.lastName = lastName.getText();
                       HelloApplication.setLoginScene();
                   }
                   else {
                       errorMessage.setText("Error creating login");
                       errorMessage.setVisible(true);
                   }
               }
               else {
                   errorMessage.setText("Passwords do not match");
                   errorMessage.setVisible(true);
               }
           }
           else {
               errorMessage.setText("Invalid Password");
               errorMessage.setVisible(true);
           }
       }
       else {
           errorMessage.setText("Username already exists");
           errorMessage.setVisible(true);
       }
    }

    @FXML
    protected void onBackButtonClick() {
        HelloApplication.setLoginScene();
    }
}
