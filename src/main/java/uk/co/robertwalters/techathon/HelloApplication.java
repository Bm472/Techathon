package uk.co.robertwalters.techathon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    public static Stage stage;
    public static int customerID;
    public static String firstName;
    public static String lastName;
    private static final int WINDOW_WIDTH = 960;
    private static final int WINDOW_HEIGHT = 540;

    // this makes it accessible within the controller so event triggers can be done.
    private static CourseContent courseContent;
    private static ContentSceneController contentSceneController;  // updating the scene content

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        setLoginScene();
        stage.show();
    }

    public static void setLoginScene()  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
            stage.setScene(scene);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setRegistrationScene()  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("registration-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), stage.getScene().getWidth(), stage.getScene().getHeight());
            stage.setScene(scene);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Updates the text area within the space
    public static void updatePage(boolean isForward) {

        courseContent.TurnPage(isForward);
        System.out.println("max page is: " +courseContent.getMaxPages());
        System.out.println("current page is.. "+courseContent.getCurrentPage());

        TextArea paragraph = new TextArea(courseContent.getCoursePages().get(courseContent.getCurrentPage()));
        paragraph.setStyle("-fx-font-size: 24");

        // sets the text to the paragraph and makes sure it can be resized depending on the screen sizing.
        contentSceneController.contentArea.setText(paragraph.getText());
        contentSceneController.contentArea.setWrapText(true);
        // the percentage is set and displayed to the screen
        contentSceneController.percentLabel.setText(courseContent.getUserPercentage() + "%");

        // percentage is taken from the class and divided by 100 as the values within the progress bar works from 0 to 1.
        contentSceneController.progressBar.setProgress(courseContent.getUserPercentage()/100.00);

    }


    public static void main(String[] args) {
        launch();
    }
}