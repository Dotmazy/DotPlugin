package fr.dotmazy.dotplugin.util.gui;

import com.google.common.collect.Multimap;
import fr.dotmazy.dotplugin.listeners.InventoryEvents;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GuiItem {

    private final ItemStack item;
    private int slot;

    private final ItemEvent onRightClick;
    private final ItemEvent onShiftRightClick;
    private final ItemEvent onLeftClick;
    private final ItemEvent onShiftLeftClick;
    private final ItemEvent onMiddleClick;
    private final ItemEvent onNumberClick;

    private boolean rightClick = false;
    private boolean shiftRightClick = false;
    private boolean leftClick = false;
    private boolean shiftLeftClick = false;
    private boolean middleClick = false;
    private boolean numberClick = false;

    public GuiItem(@NotNull ItemStack item){
        if(item==null) throw new Error("Item can't be null.");
        this.item = item;
        this.onRightClick = new ItemEvent(ClickType.RIGHT, item);
        this.onShiftRightClick = new ItemEvent(ClickType.SHIFT_RIGHT, item);
        this.onLeftClick = new ItemEvent(ClickType.LEFT, item);
        this.onShiftLeftClick = new ItemEvent(ClickType.SHIFT_LEFT, item);
        this.onMiddleClick = new ItemEvent(ClickType.MIDDLE, item);
        this.onNumberClick = new ItemEvent(ClickType.NUMBER_KEY, item);
    }

    public GuiItem(@NotNull Material item){
        this(new ItemStack(item));
    }

    /**Don't use this function (it's will break the system).*/
    public void create(Inventory inventory, boolean cancelByDefault){
        registerEvent(inventory, onRightClick, rightClick, cancelByDefault);
        registerEvent(inventory, onShiftRightClick, shiftRightClick, cancelByDefault);
        registerEvent(inventory, onLeftClick, leftClick, cancelByDefault);
        registerEvent(inventory, onShiftLeftClick, shiftLeftClick, cancelByDefault);
        registerEvent(inventory, onMiddleClick, middleClick, cancelByDefault);
        registerEvent(inventory, onNumberClick, numberClick, cancelByDefault);
    }

    private void registerEvent(Inventory inv, ItemEvent event, boolean isSet, boolean cancelByDefault){
        event.setInventory(inv);
        if(!isSet) event.setCanceled(cancelByDefault);
        event.setSlot(this.slot);
        InventoryEvents.itemClickEvents.add(event);
    }

    public ItemStack getItem(){
        return this.item;
    }

    /**Don't use this function (it's will change nothing).*/
    public void setSlot(int slot){
        this.slot = slot;
    }

    public GuiItem setDisplayName(String name){
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(name);
        return setItemMeta(meta);
    }

    public GuiItem addEnchant(Enchantment enchant, int level){
        ItemMeta meta = this.item.getItemMeta();
        meta.addEnchant(enchant, level, true);
        return setItemMeta(meta);
    }

    public GuiItem addEnchants(Map<Enchantment,Integer> enchants){
        for(Enchantment enchant : enchants.keySet())
            addEnchant(enchant, enchants.get(enchant));
        return this;
    }

    public GuiItem removeEnchants(Enchantment... enchants){
        ItemMeta meta = this.item.getItemMeta();
        for(Enchantment enchant : enchants)
            meta.removeEnchant(enchant);
        return setItemMeta(meta);
    }

    public GuiItem removeEnchants(List<Enchantment> enchants){
        ItemMeta meta = this.item.getItemMeta();
        for(Enchantment enchant : enchants)
            meta.removeEnchant(enchant);
        return setItemMeta(meta);
    }

    public GuiItem removeEnchants(){
        ItemMeta meta = this.item.getItemMeta();
        for(Enchantment enchant : meta.getEnchants().keySet())
            meta.removeEnchant(enchant);
        return setItemMeta(meta);
    }

    public GuiItem addItemFlags(ItemFlag... flags){
        ItemMeta meta = this.item.getItemMeta();
        meta.addItemFlags(flags);
        return setItemMeta(meta);
    }

    public GuiItem removeItemFlags(ItemFlag... flags){
        ItemMeta meta = this.item.getItemMeta();
        meta.removeItemFlags(flags);
        return setItemMeta(meta);
    }

    public GuiItem removeItemFlags(List<ItemFlag> flags){
        ItemMeta meta = this.item.getItemMeta();
        meta.removeItemFlags(flags.toArray(ItemFlag[]::new));
        return setItemMeta(meta);
    }

    public GuiItem removeItemFlags(){
        ItemMeta meta = this.item.getItemMeta();
        meta.removeItemFlags();
        return setItemMeta(meta);
    }

    public GuiItem setUnbreakable(boolean unbreakable){
        ItemMeta meta = this.item.getItemMeta();
        meta.setUnbreakable(unbreakable);
        return setItemMeta(meta);
    }

    public GuiItem setLocalizedName(String name){
        ItemMeta meta = this.item.getItemMeta();
        meta.setLocalizedName(name);
        return setItemMeta(meta);
    }

    public GuiItem addAttributeModifier(Attribute attribute, AttributeModifier attributeModifier){
        ItemMeta meta = this.item.getItemMeta();
        meta.addAttributeModifier(attribute, attributeModifier);
        return setItemMeta(meta);
    }

    public GuiItem setCustomModelData(int data){
        ItemMeta meta = this.item.getItemMeta();
        meta.setCustomModelData(data);
        return setItemMeta(meta);
    }

    public GuiItem setType(Material type){
        this.item.setType(type);
        return this;
    }

    public GuiItem setAmount(int amount){
        this.item.setAmount(amount);
        return this;
    }

    public GuiItem setItemMeta(ItemMeta meta){
        this.item.setItemMeta(meta);
        return this;
    }

    public GuiItem removeAttributeModifiers(EquipmentSlot... slots){
        ItemMeta meta = this.item.getItemMeta();
        for(EquipmentSlot slot : slots)
            meta.removeAttributeModifier(slot);
        return setItemMeta(meta);
    }

    public GuiItem removeAttributeModifiers(Attribute... attributes){
        ItemMeta meta = this.item.getItemMeta();
        for(Attribute attribute : attributes)
            meta.removeAttributeModifier(attribute);
        return setItemMeta(meta);
    }

    public GuiItem removeAttributeModifiers(List<Attribute> attributes){
        ItemMeta meta = this.item.getItemMeta();
        for(Attribute attribute : attributes)
            meta.removeAttributeModifier(attribute);
        return setItemMeta(meta);
    }

    public GuiItem removeAttributeModifiers(){
        ItemMeta meta = this.item.getItemMeta();
        for(Attribute attribute : meta.getAttributeModifiers().keySet())
            meta.removeAttributeModifier(attribute);
        return setItemMeta(meta);
    }

    public GuiItem removeAttributeModifier(Attribute attribute, AttributeModifier modifier){
        ItemMeta meta = this.item.getItemMeta();
        meta.removeAttributeModifier(attribute, modifier);
        return setItemMeta(meta);
    }

    public GuiItem setLore(String... lore){
        return setLore(Arrays.asList(lore));
    }

    public GuiItem setLore(List<String> lore){
        ItemMeta meta = this.item.getItemMeta();
        meta.setLore(lore);
        return setItemMeta(meta);
    }

    public GuiItem clearLore(){
        ItemMeta meta = this.item.getItemMeta();
        meta.setLore(new ArrayList<>());
        return setItemMeta(meta);
    }

    public GuiItem addLore(String... loreLines){
        List<String> lore = this.item.getItemMeta().getLore();
        for(String l:loreLines)
            lore.add(l);
        return setLore(lore);
    }

    /**@eventParams <pre>{@code (ItemStack item, Player player, int number = -1)}</pre> */
    public GuiItem onRightClick(IItemEvent event, boolean cancelEvent){
        this.onRightClick.setEvent(event,cancelEvent);
        this.rightClick = true;
        return this;
    }

    /**@eventParams <pre>{@code (ItemStack item, Player player, int number = -1)}</pre> */
    public GuiItem onShiftRightClick(IItemEvent event, boolean cancelEvent){
        this.onShiftRightClick.setEvent(event,cancelEvent);
        this.shiftRightClick = true;
        return this;
    }

    /**@eventParams <pre>{@code (ItemStack item, Player player, int number = -1)}</pre> */
    public GuiItem onLeftClick(IItemEvent event, boolean cancelEvent){
        this.onLeftClick.setEvent(event,cancelEvent);
        this.leftClick = true;
        return this;
    }

    /**@eventParams <pre>{@code (ItemStack item, Player player, int number = -1)}</pre> */
    public GuiItem onShiftLeftClick(IItemEvent event, boolean cancelEvent){
        this.onShiftLeftClick.setEvent(event,cancelEvent);
        this.shiftLeftClick = true;
        return this;
    }

    /**@eventParams <pre>{@code (ItemStack item, Player player, int number = -1)}</pre> */
    public GuiItem onMiddleClick(IItemEvent event, boolean cancelEvent){
        this.onMiddleClick.setEvent(event,cancelEvent);
        this.middleClick = true;
        return this;
    }

    /**@eventParams <pre>{@code (ItemStack item, Player player, int number)}</pre> */
    public GuiItem onNumberClick(IItemEvent event, boolean cancelEvent){
        this.onNumberClick.setEvent(event,cancelEvent);
        this.numberClick = true;
        return this;
    }

}
