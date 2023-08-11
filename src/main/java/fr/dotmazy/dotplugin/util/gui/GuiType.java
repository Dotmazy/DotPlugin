package fr.dotmazy.dotplugin.util.gui;

import org.bukkit.event.inventory.InventoryType;

public enum GuiType {
    CHEST(InventoryType.CHEST),
    DISPENSER(InventoryType.DISPENSER),
    DROPPER(InventoryType.DROPPER),
    FURNACE(InventoryType.FURNACE),
    CRAFTING_TABLE(InventoryType.WORKBENCH),
    ENCHANTING_TABLE(InventoryType.ENCHANTING),
    BREWING(InventoryType.BREWING),
    /**It's like a chest with 4 lines. */
    PLAYER_INVENTORY(InventoryType.PLAYER),
    ENDER_CHEST(InventoryType.ENDER_CHEST),
    ANVIL(InventoryType.ANVIL),
    SMITHING_TABLE(InventoryType.SMITHING),
    BEACON(InventoryType.BEACON),
    HOPPER(InventoryType.HOPPER),
    SHULKER_BOX(InventoryType.SHULKER_BOX),
    BARREL(InventoryType.BARREL),
    BLAST_FURNACE(InventoryType.BLAST_FURNACE),
    LECTERN(InventoryType.LECTERN),
    SMOKER(InventoryType.SMOKER),
    LOOM(InventoryType.LOOM),
    CARTOGRAPHY(InventoryType.CARTOGRAPHY),
    GRINDSTONE(InventoryType.GRINDSTONE),
    STONECUTTER(InventoryType.STONECUTTER),
    SMITHING_NEW(InventoryType.SMITHING_NEW);

    private final InventoryType type;

    GuiType(InventoryType type){
        this.type = type;
    }

    public InventoryType getType() {
        return type;
    }
}
