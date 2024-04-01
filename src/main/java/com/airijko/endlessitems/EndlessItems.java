package com.airijko.endlessitems;

import com.airijko.endlessitems.commands.ItemTest;
import com.airijko.endlessitems.items.ItemPath;
import com.airijko.endlessitems.managers.ConfigManager;
import com.airijko.endlessitems.mechanics.DirectoryInitializer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class EndlessItems extends JavaPlugin {

    private ConfigManager configManager;
    private DirectoryInitializer directoryInitializer;
    private ItemPath itemPath;
    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        directoryInitializer = new DirectoryInitializer(this);
        directoryInitializer.initializeDirectories();

        Objects.requireNonNull(this.getCommand("item")).setExecutor(new ItemTest(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
