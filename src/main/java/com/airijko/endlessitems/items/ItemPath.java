package com.airijko.endlessitems.items;

public enum ItemPath {
    ITEM_DISPLAY_NAME("Item.DisplayName"),
    ITEM_DESCRIPTION("Item.ItemLore");

    private final String path;

    ItemPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
