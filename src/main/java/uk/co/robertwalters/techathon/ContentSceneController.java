package uk.co.robertwalters.techathon;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextFlow;

public class ContentSceneController extends HelloApplication{

    @FXML
    public StackPane bottomStackPane;

    @FXML
    public TextFlow contentFlow;

    @FXML
    public ScrollPane contentPane;

    @FXML
    public TextArea contentArea;

    @FXML
    public ProgressBar progressBar; // progress bar to show the percentage covered in content

    @FXML
    public Label percentLabel;

    @FXML
    public Label navName;

    private  CourseContent courseContent;


    @FXML
    public void onForwardButtonClick() {
        updatePage(true);  // true is a sign to go forward
    }


    @FXML
    public void onPreviousButtonClick() {
        updatePage(false);  // true is a sign to go forward
    }

    @FXML
    protected void initialize() {

        //Update name label in menu bar
        navName.setText(HelloApplication.firstName + " " + HelloApplication.lastName);

        // the name placed here is important as it will be used to get the information from the database.
        courseContent = new CourseContent("Investment");

        // gets the current coursePage for that specific course class that was retrieved
        TextArea paragraph = new TextArea(courseContent.getCoursePages().get(courseContent.getCurrentPage()));
        paragraph.setStyle("-fx-font-size: 24");

        // sets the text to the paragraph and makes sure it can be resized depending on the screen sizing.
        contentArea.setText(paragraph.getText());
        contentArea.setWrapText(true);
        // the percentage is set and displayed to the screen
        percentLabel.setText(courseContent.getUserPercentage() + "%");

        // percentage is taken from the class and divided by 100 as the values within the progress bar works from 0 to 1.
        progressBar.setProgress(courseContent.getUserPercentage() /100.00);

    }

    // Updates the text area within the space
    public void updatePage(boolean isForward) {

        courseContent.TurnPage(isForward);
        System.out.println("max page is: " +courseContent.getMaxPages());
        System.out.println("current page is.. "+courseContent.getCurrentPage());

        TextArea paragraph = new TextArea(courseContent.getCoursePages().get(courseContent.getCurrentPage()));
        paragraph.setStyle("-fx-font-size: 24");

        // sets the text to the paragraph and makes sure it can be resized depending on the screen sizing.
        contentArea.setText(paragraph.getText());
        contentArea.setWrapText(true);
        // the percentage is set and displayed to the screen
        percentLabel.setText(courseContent.getUserPercentage() + "%");

        // percentage is taken from the class and divided by 100 as the values within the progress bar works from 0 to 1.
        progressBar.setProgress(courseContent.getUserPercentage()/100.00);

    }


}
