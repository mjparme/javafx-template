package com.example.app;

import javafx.application.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import org.slf4j.*;

/**
 * User: mjparme
 * Date: 3/21/22
 * Time: 8:49 PM
 */
public class MyApplication extends Application {
    private final static Logger logger = LoggerFactory.getLogger(MyApplication.class);

    private Button okButton;
    private Button cancelButton;
    private TextArea textArea;

    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(createTextAreaBox());
        borderPane.setBottom(createButtonBox());

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    private Node createButtonBox() {
        okButton = new Button("Ok");
        okButton.setOnAction(okAction);

        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(cancelAction);

        HBox box = new HBox(5);
        box.setPadding(new Insets(3, 3, 3, 3));
        box.getChildren().addAll(okButton, cancelButton);
        box.setAlignment(Pos.CENTER);

        return box;
    }

    private Node createTextAreaBox() {
        textArea = new TextArea();
        ScrollPane scrollPane = new ScrollPane(textArea);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        return scrollPane;
    }

    private final EventHandler<ActionEvent> okAction = event -> {
        logger.debug("Ok pressed");
        textArea.appendText("Ok Pressed\n");
    };

    private final EventHandler<ActionEvent> cancelAction = event -> {
        textArea.appendText("Cancel Pressed\n");
    };

    public static void main(String[] args) {
        launch(args);
    }
}
