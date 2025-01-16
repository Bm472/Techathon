package uk.co.robertwalters.techathon;

import javafx.fxml.FXML;
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
    public TextArea contentArea;

    @FXML
    public void onForwardButtonClick() {
        updatePage(true);  // true is a sign to go forward
    }


    @FXML
    public void onPreviousButtonClick() {
        updatePage(false);  // true is a sign to go forward
    }
}
