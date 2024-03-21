package com.airijko.endlessitems.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

public class Item {
    public String itemName;
    public String itemLore;
    public String itemMaterial;
    public double lifeForce;
    public double strength;
    public double toughness;
    public double knockbackResistance;
    public double speed;
    public double attackSpeed;
    public double precision;
    public double ferocity;
    public Rarity rarity;

    public Item(File configFile, Rarity rarity) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        this.itemName = config.getString("Item.ItemName");
        this.itemLore = config.getString("Item.ItemLore");
        this.itemMaterial = config.getString("Item.Material");
        this.lifeForce = config.getDouble("Item.Attributes.Life_Force");
        this.strength = config.getDouble("Item.Attributes.Strength");
        this.toughness = config.getDouble("Item.Attributes.Toughness");
        this.knockbackResistance = config.getDouble("Item.Attributes.Knockback_Resistance");
        this.speed = config.getDouble("Item.Attributes.Speed");
        this.attackSpeed = config.getDouble("Item.Attributes.Attack_Speed");
        this.precision = config.getDouble("Item.Attributes.Precision");
        this.ferocity = config.getDouble("Item.Attributes.Ferocity");
        this.rarity = rarity;
        applyRarityBonus();
    }

    protected void applyRarityBonus() {
        double factor;
        if (rarity == Rarity.COMMON) {
            factor = 1.0;
        } else {
            factor = 1 + 0.2 * (rarity.ordinal() - 1);
        }
        lifeForce *= factor;
        strength *= factor;
        toughness *= factor;
        knockbackResistance *= factor;
        speed *= factor;
        attackSpeed *= factor;
        precision *= factor;
        ferocity *= factor;
    }

    public Rarity getRarity() {
        return this.rarity;
    }

    public ItemStack toItemStack() {
        if (itemMaterial == null) {
            throw new IllegalStateException("Item material is null");
        }
        if (itemName == null) {
            throw new IllegalStateException("Item name is null");
        }
        if (itemLore == null) {
            throw new IllegalStateException("Item lore is null");
        }
        Material material = Material.valueOf(itemMaterial);
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();

        meta.displayName(Component.text(this.itemName));
        meta.lore(Collections.singletonList(Component.text(Arrays.toString(this.itemLore.split("\n")))));

        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
