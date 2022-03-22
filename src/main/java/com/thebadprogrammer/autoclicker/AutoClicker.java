package com.thebadprogrammer.autoclicker;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.Clock;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class AutoClicker extends Application {
    private TextField timeTextField;
    private MouseClicker currentClicker;
    private Button startButton;
    private Button stopButton;

    @Override
    public void start(Stage stage) {
        GlobalScreen.addNativeKeyListener(new GlobalKeyListener());

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(buttonBox());
        borderPane.setBottom(fieldBox());

        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();

      stage.setOnCloseRequest(event -> {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public HBox fieldBox() {
        timeTextField = new TextField("1");

        List<String> clickTypes = Arrays.stream(ClickType.values())
            .map(ClickType::getDisplayName)
            .collect(Collectors.toList());
        ObservableList<String> timeElements = FXCollections.observableArrayList(clickTypes);

        ComboBox<String> comboBox = new ComboBox<>(timeElements);
        comboBox.getSelectionModel().select(0);

        HBox box = new HBox(5);
        box.setPadding(new Insets(3, 3, 3, 3));
        box.getChildren().addAll(timeTextField, comboBox);

        return box;
    }

    public HBox buttonBox() {
        startButton = new Button("Start");
        startButton.setOnAction(startAction);

        stopButton = new Button("Stop");
        stopButton.setOnAction(stopAction);
        stopButton.setDisable(true);

        HBox box = new HBox(5);
        box.setPadding(new Insets(3, 3, 3, 3));
        box.getChildren().addAll(startButton, stopButton);
        box.setAlignment(Pos.CENTER);

        return box;
    }

    private final EventHandler<ActionEvent> startAction = event -> {
        System.out.println("start event = " + event);
        startButton.setDisable(true);
        stopButton.setDisable(false);
        long timeValue = Long.parseLong(timeTextField.getText());
        currentClicker = new MouseClicker(ClickType.PER_SECOND, timeValue);
        currentClicker.startClicking();
    };

    private final EventHandler<ActionEvent> stopAction = event -> {
        System.out.println("stop event = " + event);
        startButton.setDisable(false);
        stopButton.setDisable(true);
        currentClicker.stopClicking();
        currentClicker = null;
    };

    private class GlobalKeyListener implements NativeKeyListener {
        private final static long DOUBLE_CLICK_TIME_MS = 250;
        private long firstPress = 0;
        private long pressCount;

        public void nativeKeyPressed(NativeKeyEvent e) {
            System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
            //29 is left control
            if (e.getKeyCode() == 29) {
                if (firstPress == 0) {
                    firstPress = Clock.systemUTC().millis();
                }

                pressCount++;
                //System.out.println("pressCount = " + pressCount);
                long currentTime = Clock.systemUTC().millis();
                long pressTimeDiff = currentTime - firstPress;
                //System.out.println("pressTimeDiff = " + pressTimeDiff);
                if (pressTimeDiff >= DOUBLE_CLICK_TIME_MS) {
                    //System.out.println("double click time not met, resetting count and first press time");
                    pressCount = 0;
                    firstPress = 0;
                } else {
                    if (pressCount == 2) {
                        if (currentClicker == null) {
                            startButton.fire();
                        } else {
                            stopButton.fire();
                        }

                        pressCount = 0;
                        firstPress = 0;
                    }
                }
            }
        }

        public void nativeKeyReleased(NativeKeyEvent e) {

        }

        public void nativeKeyTyped(NativeKeyEvent e) {

        }
    }

    public static void main(String[] args) {
        System.out.println("args = " + args);
        //Fix for unsatisfied link error when using jnativehook from an jpackage create .app file
        //https://stackoverflow.com/questions/70677583/java-lang-unsatisfiedlinkerror-uri-scheme-is-not-file-with-javafxjlink/70679296#70679296
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        launch();
    }
}