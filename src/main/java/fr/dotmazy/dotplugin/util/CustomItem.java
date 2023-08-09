package fr.dotmazy.dotplugin.util;

import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentOffer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomItem {

    private String name;
    private String displayName;
    private Material material;
    private List<String> lore;
    private List<EnchantmentOffer> enchants;

    public CustomItem(String name, String displayName, Material material){
        this.name = name;
        this.displayName = displayName;
        this.material = material;
        this.lore = new ArrayList<>();
        this.enchants = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public List<EnchantmentOffer> getEnchants() {
        return enchants;
    }

    public void setEnchants(List<EnchantmentOffer> enchant) {
        this.enchants = enchant;
    }
}
