package com.thebadprogrammer.autoclicker;

import javafx.application.Platform;
import javafx.scene.input.MouseButton;
import javafx.scene.robot.Robot;

import java.util.Timer;
import java.util.TimerTask;

/**
 * User: mjparme
 * Date: 3/19/22
 * Time: 7:30 PM
 */
public class MouseClicker {
    private final ClickType clickType;
    private final Long value;
    private Robot robot;
    private final Timer clickTimer;

    public MouseClicker(ClickType clickType, Long value) {
        this.clickType = clickType;
        this.value = value;

        Platform.runLater(() -> robot = new Robot());
        clickTimer = new Timer("Clicker");
    }

    public void startClicking() {
        long period = 0;
        if (clickType == ClickType.PER_SECOND) {
            period = 1000 / value;
        }

        System.out.println("starting clicker with period: " + period + " ms");
        clickTimer.scheduleAtFixedRate(new Clicker(), 2000, period);
    }

    public void stopClicking() {
        System.out.println("stopping clicker");
        clickTimer.cancel();
    }

    private class Clicker extends TimerTask {
        @Override
        public void run() {
            Platform.runLater(() -> robot.mouseClick(MouseButton.PRIMARY));
        }
    }
}
