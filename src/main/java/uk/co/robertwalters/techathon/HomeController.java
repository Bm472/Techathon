package uk.co.robertwalters.techathon;

import eu.hansolo.tilesfx.Tile;
import javafx.fxml.FXML;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import data.Database;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
/*import javafx.scene.layout.*/

public class HomeController {

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
        HelloApplication.setupContentController();
    }

    @FXML
    protected void initialize() {
        try {
            menuName.setText(HelloApplication.firstName + " " + HelloApplication.lastName);
            ResultSet resultSet = Database.viewCourses();
            if (resultSet != null) {
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    switch (name) {
                        case "Mortgages":
                            Mortgages.setTitle(resultSet.getString("name"));
                            Mortgages.setDescription(resultSet.getString("description"));
                            break;
                        case "Pensions":
                            Pensions.setTitle(resultSet.getString("name"));
                            Pensions.setDescription(resultSet.getString("description"));
                            break;
                        case "Savings":
                            Savings.setTitle(resultSet.getString("name"));
                            Savings.setDescription(resultSet.getString("description"));
                            break;
                        case "Investments":
                            Investments.setTitle(resultSet.getString("name"));
                            Investments.setDescription(resultSet.getString("description"));
                            break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
