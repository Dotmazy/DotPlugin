package fr.dotmazy.dotplugin.util.hypixel_items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentOffer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HypixelItem {

    private ItemType type;
    private String name;
    private Rarity rarity;
    private Material material;
    private List<String> lore;
    private boolean glowEnchant;
    private List<EnchantmentOffer> enchants;
    private Map<FlagType,Integer> flags;

    public HypixelItem(ItemType type, String name, Rarity rarity, Material material){
        this.type = type;
        this.name = name;
        this.rarity = rarity;
        this.material = material;
        this.glowEnchant = false;
        this.lore = new ArrayList<>();
        this.enchants = new ArrayList<>();
        this.flags = new HashMap<>();
    }

    public HypixelItem(ItemType type, String name, Rarity rarity, Material material, List<String> lore, boolean glowEnchant, Map<FlagType,Integer> flags){
        this.name = name;
        this.rarity = rarity;
        this.material = material;
        this.glowEnchant = glowEnchant;
        this.lore = lore;
        this.enchants = new ArrayList<>();
        this.flags = flags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
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

    public void setEnchants(List<EnchantmentOffer> enchants) {
        this.enchants = enchants;
    }

    public boolean isGlowEnchant() {
        return glowEnchant;
    }

    public void setGlowEnchant(boolean glowEnchant) {
        this.glowEnchant = glowEnchant;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public Map<FlagType, Integer> getFlags() {
        return flags;
    }

    public void setFlags(Map<FlagType, Integer> flags) {
        this.flags = flags;
    }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        List<String> list = new ArrayList<>(lore);
        //for (FlagType t : flags.keySet()) list.add();
        list.add(rarity.getColor()+rarity.getPrefix()+type.toString()+rarity.getSuffix());
        meta.setLore(list);
        for (EnchantmentOffer enchant : enchants) meta.addEnchant(enchant.getEnchantment(), enchant.getEnchantmentLevel(), true);
        if (!meta.hasEnchants() && glowEnchant) meta.addEnchant(
                type==ItemType.HELMET||type==ItemType.CHESTPLATE||type==ItemType.LEGGINGS||type==ItemType.BOOTS||type==ItemType.DUNGEON_HELMET||type==ItemType.DUNGEON_CHESTPLATE||type==ItemType.DUNGEON_LEGGINGS||type==ItemType.DUNGEON_BOOTS?Enchantment.MULTISHOT: Enchantment.PROTECTION_FALL
                ,1, true);
        item.setItemMeta(meta);
        return item;
    }

}
