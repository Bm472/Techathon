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
    public static int currentCourseID;
    private static final int WINDOW_WIDTH = 1500;
    private static final int WINDOW_HEIGHT = 800;

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

    public static void setHomeScene()  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("home-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), stage.getScene().getWidth(), stage.getScene().getHeight());
            stage.setScene(scene);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setContentScene()  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("content-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), stage.getScene().getWidth(), stage.getScene().getHeight());
            stage.setScene(scene);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



    // sets up the content that will be shown within the controller
    public static void setupContentController() {

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("content-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), stage.getScene().getWidth(), stage.getScene().getHeight());
            stage.setScene(scene);

        }catch (Exception e) {
            e.printStackTrace();
        }

    }



    public static void main(String[] args) {
        launch();
    }
}