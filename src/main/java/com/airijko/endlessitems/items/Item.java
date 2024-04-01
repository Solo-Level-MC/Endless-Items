package com.airijko.endlessitems.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

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

        this.itemLore = buildItemLore(config);
    }

    private String buildItemLore(YamlConfiguration config) {
        return "\nLife Force: " + formatAttribute(lifeForce) +
                "\nStrength: " + formatAttribute(strength) +
                "\nToughness: " + formatAttribute(toughness) +
                "\nKnockback Resistance: " + formatAttribute(knockbackResistance) +
                "\nSpeed: " + formatAttribute(speed) +
                "\nAttack Speed: " + formatAttribute(attackSpeed) +
                "\nPrecision: " + formatAttribute(precision) +
                "\nFerocity: " + formatAttribute(ferocity) +
                "\n" + Objects.requireNonNull(config.getString("Item.ItemLore"));
    }

    private String formatAttribute(double attribute) {
        if (attribute % 1 == 0) {
            return String.format("%.0f", attribute);
        } else {
            return String.format("%.2f", attribute);
        }
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

        meta.displayName(Component.text(this.rarity + " " +  this.itemName));

        meta.lore(Arrays.stream(this.itemLore.split("\n"))
                .map(Component::text)
                .collect(Collectors.toList()));

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    protected void applyRarityBonus() {
        double factor;
        if (rarity == Rarity.COMMON) {
            factor = 1.0;
        } else {
            factor = 1 + (rarity.ordinal() - 1);
        }

        lifeForce *= factor * generateRandomFactor();
        strength *= factor * generateRandomFactor();
        toughness *= factor * generateRandomFactor();
        knockbackResistance *= factor * generateRandomFactor();
        speed *= factor * generateRandomFactor();
        attackSpeed *= factor * generateRandomFactor();
        precision *= factor * generateRandomFactor();
        ferocity *= factor * generateRandomFactor();
    }

    private double generateRandomFactor() {
        Random random = new Random();
        return 1 + 0.4 + (0.75 - 0.4) * random.nextDouble();
    }

    public Rarity getRarity() {
        return this.rarity;
    }
}
