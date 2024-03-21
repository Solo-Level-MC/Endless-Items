package com.airijko.endlessitems.mechanics;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class DirectoryInitializer {
    private final JavaPlugin plugin;

    public DirectoryInitializer(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void initializeDirectories() {
        createDirectory();
        loadDefaultClasses();
    }

    private void createDirectory() {
        File directory = new File(plugin.getDataFolder(), "items");
        if (!directory.exists()) {
            plugin.saveResource("items" + "/.keep", false);
        }
    }

    private void loadDefaultClasses() {
        saveClassIfNotExists("items/Sword.yml");
        saveClassIfNotExists("items/Axe.yml");
        saveClassIfNotExists("items/Bow.yml");
        saveClassIfNotExists("items/Helmet.yml");
        saveClassIfNotExists("items/Chestplate.yml");
        saveClassIfNotExists("items/Leggings.yml");
        saveClassIfNotExists("items/Boots.yml");
        saveClassIfNotExists("items/Shield.yml");

    }


    private void saveClassIfNotExists(String resourcePath) {
        File resourceFile = new File(plugin.getDataFolder(), resourcePath);
        if (!resourceFile.exists()) {
            plugin.saveResource(resourcePath, false);
        }
    }
}
