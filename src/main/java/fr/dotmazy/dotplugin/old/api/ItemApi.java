package fr.dotmazy.dotplugin.old.api;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.util.CustomItem;
import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentOffer;

import java.util.List;

public class ItemApi {

    public static boolean createItem(String name, String displayName, Material material, String... lore) {
        DotPlugin.files.get("items").get().set(name+".name",displayName);
        DotPlugin.files.get("items").get().set(name+".material",material);
        DotPlugin.files.get("items").get().set(name+".lore",lore);
        return true;
    }

    public static boolean createItem(String name, String displayName, Material material, List<EnchantmentOffer> enchants, String... lore) {
        DotPlugin.files.get("items").get().set(name+".name",displayName);
        DotPlugin.files.get("items").get().set(name+".material",material);
        DotPlugin.files.get("items").get().set(name+".lore",lore);
        for (EnchantmentOffer enchant : enchants) {
            DotPlugin.files.get("items").get().set(name + ".enchants."+enchant.getEnchantment().getName(), enchant.getEnchantmentLevel());
        }
        return true;
    }

    public static boolean createItem(CustomItem item) {
        DotPlugin.files.get("items").get().set(item.getName()+".name",item.getDisplayName());
        DotPlugin.files.get("items").get().set(item.getName()+".material",item.getMaterial());
        DotPlugin.files.get("items").get().set(item.getName()+".lore",item.getLore());
        //DotPlugin.files.get("items").get().set(item.getName()+".enchants",item.getEnchant());
        return true;
    }

}
