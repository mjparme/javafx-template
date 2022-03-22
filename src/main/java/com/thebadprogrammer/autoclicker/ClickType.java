package com.thebadprogrammer.autoclicker;

/**
 * User: mjparme
 * Date: 3/19/22
 * Time: 7:41 PM
 */
public enum ClickType {
    PER_SECOND("Per Second"),
    INTERVAL("Interval"),
    ;

    private final String displayName;

    ClickType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
