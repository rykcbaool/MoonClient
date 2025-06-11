package dev.ryk.api.events.impl;

import dev.ryk.api.events.Event;

public class SprintEvent extends Event {
    public SprintEvent(Stage stage) {
        super(stage);
    }

    private boolean sprint = false;
    public boolean isSprint() {
        return sprint;
    }
    public void setSprint(boolean sprint) {
        this.sprint = sprint;
    }
}
