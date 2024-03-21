package com.airijko.endlessitems.items;

import com.airijko.endlessitems.managers.ConfigManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum Rarity {
    COMMON("rarity_weights.common"),
    UNCOMMON("rarity_weights.uncommon"),
    RARE("rarity_weights.rare"),
    EPIC("rarity_weights.epic"),
    LEGENDARY("rarity_weights.legendary"),
    MYTHIC("rarity_weights.mythic");

    private int weight;
    private final String path;

    Rarity(String path) {
        this.path = path;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public static void loadWeights(ConfigManager configManager) {
        for (Rarity rarity : Rarity.values()) {
            if (rarity != Rarity.COMMON) {
                int weight = configManager.getConfig().getInt(rarity.path);
                rarity.setWeight(weight);
            }
        }
    }

    public static Rarity getRandomRarity() {
        List<Rarity> weightedRarities = new ArrayList<>();
        for (Rarity rarity : Rarity.values()) {
            if (rarity != Rarity.COMMON) {
                for (int i = 0; i < rarity.getWeight(); i++) {
                    weightedRarities.add(rarity);
                }
            }
        }
        Random random = new Random();
        int randomIndex = random.nextInt(weightedRarities.size());
        return weightedRarities.get(randomIndex);
    }
}
