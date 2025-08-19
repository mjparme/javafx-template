package com.example.app;

import javafx.application.Application;

/**
 * Launcher class to start the JavaFX application.
 * This separate launcher class helps resolve the "JavaFX runtime components are missing" error
 * by ensuring proper initialization of the JavaFX runtime.
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(MyApplication.class, args);
    }
}