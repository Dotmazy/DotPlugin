package fr.dotmazy.dotplugin.util.hypixel_items;

import org.bukkit.ChatColor;

public class Rarity {

    private String name;
    private String prefix;
    private String suffix;
    private ChatColor color;

    public Rarity(String name, String prefix, String suffix, ChatColor color){
        this.name = name;
        this.prefix = prefix;
        this.suffix = suffix;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public ChatColor getColor() {
        return color;
    }

    public void setColor(ChatColor color) {
        this.color = color;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
