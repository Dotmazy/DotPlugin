package fr.dotmazy.dotplugin.util.gui;

import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMultiGui {
    protected final List<AbstractGui> guis;

    public AbstractMultiGui(){
        this.guis = new ArrayList<>();
        init();
    }

    /**
     * Initialization of the guis.
     * */
    protected abstract void init();

    /**
     * Get the first inventory of the multi gui.
     * */
    public Inventory getMainInventory(){
        if(this.guis.size()>0) return this.guis.get(0).getInventory();
        else return null;
    }

    /**
     * @param gui The gui to add.
     *
     * <p></p>
     * Add a gui to the multi gui.
     * */
    protected void addGui(AbstractGui gui){
        guis.add(gui);
    }

    /**
     * @param index The index of the gui to set.
     * @param gui The gui to set.
     * <p></p>
     * Set a gui on the multi gui.
     * */
    protected void setGui(int index, AbstractGui gui){
        guis.set(index,gui);
    }

}
