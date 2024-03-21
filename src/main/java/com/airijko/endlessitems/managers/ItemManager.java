package com.airijko.endlessitems.managers;

import com.airijko.endlessitems.items.Item;
import com.airijko.endlessitems.items.Rarity;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ItemManager {
    private final JavaPlugin plugin;

    public ItemManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public Item loadItems(String itemName, Rarity rarity) {
        File configFile = new File(plugin.getDataFolder() + "/items", itemName + ".yml");
        if (!configFile.exists()) {
            plugin.getLogger().severe("Item not found: " + itemName);
            return null;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        String rarityInFile = config.getString("Item.Rarity");

        if ("DYNAMIC".equals(rarityInFile)) {
            config.set("Item.Rarity", rarity.name());
        }

        return new Item(configFile, rarity);
    }

    public Item chooseItem(String chosenItemName) {
        Rarity randomRarity = Rarity.getRandomRarity();
        return loadItems(chosenItemName, randomRarity);
    }

    public void addItemToPlayerInventory(Player player, Item item) {
        player.getInventory().addItem(item.toItemStack());
    }
}
