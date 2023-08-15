package fr.dotmazy.dotplugin.gui;

import fr.dotmazy.dotplugin.util.gui.AbstractGui;
import fr.dotmazy.dotplugin.util.gui.GuiItem;
import fr.dotmazy.dotplugin.util.gui.GuiType;
import org.bukkit.Material;

public class ExampleGui extends AbstractGui {

    protected void init() {
        makeFrame(new GuiItem(Material.STONE).onMiddleClick((item,player,number)->{
            player.sendMessage("Don't touch the decoration !");
        }, true), 1);

        makeSquare(Material.ITEM_FRAME, 2, 2, 3, 3);

        setItem(new GuiItem(Material.STICKY_PISTON).onNumberClick((item,player,number)->{
            if(number==5){
                player.sendMessage("Wow, you have find the secret.");
            }
        }, true), 33);
    }

    protected String getName() {
        return "Example Gui";
    }

    protected GuiType getType() {
        return GuiType.CHEST;
    }

    protected int getSize() {
        return 54;
    }

    public boolean isDefaultCanceled() {
        return true;
    }

}
