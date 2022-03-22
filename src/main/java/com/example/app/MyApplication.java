package com.example.app;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * User: mjparme
 * Date: 3/21/22
 * Time: 8:49 PM
 */
public class MyApplication extends Application {
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
        textArea.appendText("Ok Pressed\n");
    };

    private final EventHandler<ActionEvent> cancelAction = event -> {
        textArea.appendText("Cancel Pressed\n");
    };

    public static void main(String[] args) {
        launch(args);
    }
}
