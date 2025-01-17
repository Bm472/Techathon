package uk.co.robertwalters.techathon;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.tools.Helper;
import javafx.fxml.FXML;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import data.Database;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/*import javafx.scene.layout.*/

public class HomeController {

    @FXML
    public ImageView logoImage;
    @FXML
    private Label menuName;

    @FXML
    private Tile Mortgages;

    @FXML
    private Tile Pensions;

    @FXML
    private Tile Savings;

    @FXML
    private Tile Investments;


    @FXML
    protected void onTestClick() throws IOException {
        HelloApplication.setContentScene();
    }

    @FXML
    protected void initialize() {
        try {
            menuName.setText(HelloApplication.firstName + " " + HelloApplication.lastName);
            System.out.println(HelloApplication.customerID);
            ResultSet resultSet = Database.viewProgress(HelloApplication.customerID);
            if (resultSet != null) {
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    switch (name) {
                        case "Mortgages":
                            Mortgages.setTitle(resultSet.getString("name"));
                            Mortgages.setDescription(resultSet.getString("description"));
                            Mortgages.setValue(resultSet.getDouble("value"));
                            Mortgages.setValueColor(Color.web("#f07f09"));
                            Mortgages.setBarColor(Color.web("#f07f09"));
                            Mortgages.setValueVisible(false);
                            break;
                        case "Pensions":
                            Pensions.setTitle(resultSet.getString("name"));
                            Pensions.setDescription(resultSet.getString("description"));
                            Pensions.setValue(resultSet.getDouble("value"));
                            Pensions.setValueColor(Color.web("#f07f09"));
                            Pensions.setBarColor(Color.web("#f07f09"));
                            Pensions.setValueVisible(false);
                            break;
                        case "Savings":
                            Savings.setTitle(resultSet.getString("name"));
                            Savings.setDescription(resultSet.getString("description"));
                            Savings.setValue(resultSet.getDouble("value"));
                            Savings.setValueColor(Color.web("#f07f09"));
                            Savings.setBarColor(Color.web("#f07f09"));
                            Savings.setValueVisible(false);
                            break;
                        case "Investments":
                            Investments.setTitle(resultSet.getString("name"));
                            Investments.setDescription(resultSet.getString("description"));
                            Investments.setValue(resultSet.getDouble("value"));
                            Investments.setValueColor(Color.web("#f07f09"));
                            Investments.setBarColor(Color.web("#f07f09"));
                            Investments.setValueVisible(false);
                            break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onMortgagesTileClicked() throws SQLException {
        HelloApplication.currentCourseID = 1;
        ResultSet resultSet = Database.viewCourse(1);
        resultSet.next();
        CourseContent.cont1 = resultSet.getString("content");
        HelloApplication.setContentScene();
    }

    @FXML
    protected void onPensionsTileClicked() throws SQLException {
        HelloApplication.currentCourseID = 2;
        ResultSet resultSet = Database.viewCourse(2);
        resultSet.next();
        CourseContent.cont1 = resultSet.getString("content");
        HelloApplication.setContentScene();
    }

    @FXML
    protected void onSavingsTileClicked() throws SQLException {
        HelloApplication.currentCourseID = 3;
        ResultSet resultSet = Database.viewCourse(3);
        resultSet.next();
        CourseContent.cont1 = resultSet.getString("content");
        HelloApplication.setContentScene();
    }

    @FXML
    protected void onInvestmentsTileClicked() throws SQLException {
        HelloApplication.currentCourseID = 4;
        ResultSet resultSet = Database.viewCourse(4);
        resultSet.next();
        CourseContent.cont1 = resultSet.getString("content");
        HelloApplication.setContentScene();
    }

    @FXML
    protected void onLogoutClick() throws SQLException {
        HelloApplication.customerID = -1;
        HelloApplication.currentCourseID = -1;
        HelloApplication.firstName = null;
        HelloApplication.lastName = null;
        HelloApplication.setLoginScene();
    }
}
