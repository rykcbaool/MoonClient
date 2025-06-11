package dev.ryk.mod;

import dev.ryk.api.utils.Wrapper;

public class Mod implements Wrapper {
    private final String name;
    public Mod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
