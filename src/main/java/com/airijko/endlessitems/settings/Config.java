package com.airijko.endlessitems.settings;

public class Config {

    private final String path;
    private final OptionType type;

    Config(String path, OptionType type) {
        this.path = path;
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public OptionType getType() {
        return type;
    }
}
