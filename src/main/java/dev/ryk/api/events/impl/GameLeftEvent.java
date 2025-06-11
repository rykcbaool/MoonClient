package dev.ryk.api.events.impl;

import dev.ryk.api.events.Event;

public class GameLeftEvent extends Event {
    public GameLeftEvent() {
        super(Stage.Post);
    }
}
