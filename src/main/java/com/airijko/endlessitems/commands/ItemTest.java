package com.airijko.endlessitems.commands;

import com.airijko.endlessitems.EndlessItems;
import com.airijko.endlessitems.items.Item;
import com.airijko.endlessitems.items.Rarity;
import com.airijko.endlessitems.managers.ItemManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ItemTest implements CommandExecutor {
    private final ItemManager itemManager;

    public ItemTest(EndlessItems plugin) {
        this.itemManager = new ItemManager(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 2) {
            player.sendMessage("Usage: /item <name> <rarity>");
            return true;
        }

        String itemName = args[0];
        String rarityName = args[1].toUpperCase();
        Rarity rarity;
        try {
            rarity = Rarity.valueOf(rarityName);
        } catch (IllegalArgumentException e) {
            player.sendMessage("Invalid rarity: " + rarityName);
            return true;
        }

        Item item = itemManager.loadItems(itemName, rarity);

        if (item == null) {
            player.sendMessage("The item " + itemName + " doesn't exist.");
            return true;
        }

        itemManager.addItemToPlayerInventory(player, item);

        return true;
    }
}