# DotPlugin
This plugin is a plugin that can do everything's that essential.

## Table of Contents
- [Commands](#commands)
  - [/spawn](#spawn)
  - [/lobby](#lobby)
  - [/msg](#msg)
  - [/warn](#warn)
  - [/home](#home)
  - [/sethome](#sethome)
  - [/music](#music)
  - [/warp](#warp)
  - [/nick](#nick)
  - [/craft](#craft)
  - [/smelt](#smelt)
  - [/enderchest](#enderchest)
  - [/adminchat](#vanish)
  - [/warns](#warns)
  - [/freeze](#freeze)
  - [/unfreeze](#unfreeze)
  - [/mute](#mute)
  - [/unmute](#unmute)
  - [/mod](#mod)
  - [/gamemode](#gamemode)
  - [/vanish](#vanish)
  - [/setwarp](#setwarp)
  - [/setspawn](#setspawn)
  - [/setlobby](#setlobby)
  - [/ranks](#ranks)
  - [/drl](#drl)
- [Api](#gui)
  - [Player](#player)
  - [Rank](#rank)
  - [Text](#text)
  - [Gui](#gui)

## Commands

### /spawn
- Usage: `/spawn`
- Description: `Teleport to the spawn of the actual world`
- Permissions: `dotplugin.commands.spawn`

### /lobby
- Usage: `/lobby`
- Description: `Teleport to the lobby`
- Permission: `dotplugin.commands.lobby`

### /msg
- Usage: `/msg <player> <message>`
- Alias: `message`
- Description: `Send a message to a player`
- Permission: `dotplugin.commands.msg`

### /warn
- Usage: `/warn <player> <reason>`
- Alias: `report`
- Description: `Warn a player`
- Permission: `dotplugin.commands.warn`

### /home
- Usage: `/home <name>`
- Description: `Teleport to a home`
- Permission: `dotplugin.commands.home`

### /sethome
- Usage: `/sethome <name>`
- Description: `Set a home`
- Permission: `dotplugin.commands.sethome`

### /music
- Usages:
    - `/music`
    - `/music <music>`
    - `/music stop`
    - `/music auto`
- Description: `Manage music (need the resource pack)`
- Permission: `dotplugin.commands.music`

### /warp
- Usage: `/warp <name>`
- Description: `Teleport to a warp`
- Permission: `dotplugin.commands.warp`

### /nick
- Usage: `/nick (<name>)`
- Description: `Change or remove your nick name`
- Permission: `dotplugin.commands.nick`

### /craft
- Usage: `/craft`
- Description: `Open a virtual crafting table`
- Permission: `dotplugin.commands.craft`

### /smelt
- Usage: `/smelt`
- Description: `Smelt the item on your hand`
- Permission: `dotplugin.commands.smelt`

### /enderchest
- Usage: `/enderchest`
- Aliases: `ec,echest,enderc`
- Permission: `dotplugin.commands.enderchest`

### /adminchat
- Usage: `/adminchat`
- Aliases: `ac,achat,adminc`
- Description: `Toggle admin chat`
- Permission: `dotplugin.commands.adminchat`

### /warns
- Usage: `/warns <player> (<warn-id>)`
- Alias: `getwarns`
- Description: `Get the warns of a player`
- Permission: `dotplugin.commands.warns`

### /freeze
- Usage: `/freeze <player> (<reason>)`
- Description: `Freeze a player`
- Permission: `dotplugin.commands.freeze`

### /unfreeze
- Usage: `/unfreeze <player> (<reason>)`
- Description: `Unfreeze a player`
- Permission: `dotplugin.commands.unfreeze`

### /mute
- Usage: `/mute <player> (<reason>)`
- Description: `Mute a player`
- Permission: `dotplugin.commands.mute`

### /unmute
- Usage: `/unmute <player> (<reason>)`
- Description: `Unmute a player`
- Permission: `dotplugin.commands.unmute`

### /mod
- Usage: `/mod`
- Alias: `moderation`
- Description: `Toggle moderation mode (when enabled vanish is also enabled)`
- Permission: `dotplugin.commands.mod`

### /vanish
- Usage: `/vanish (<player>)`
- Description: `Toggle vanish of you or an other player`
- Permission: `dotplugin.commands.vanish`

### /gamemode
- Usage: `/gamemode <gamemode> (<player>)`
- Aliases: `gm,gmc,gamemodecreative,gmcreative,gamemodec,gmc,gamemodecreative,gmcreative,gamemodec,gms,gamemodesurvival,gmsurvival,gamemodes,gma,gamemodeadventure,gmadventure,gamemodea,gmsp,gamemodespectator,gmspectator,gamemodesp`
- Description: `Set your gamemode or the gamemode of an other player`
- Permission: `dotplugin.commands.gamemode` (`*.everyone` if you want that he can set the gamemode of another player)

### /setwarp
- Usage: `/setwarp <name>`
- Description: `Set a warp`
- Permission: `dotplugin.commands.setwarp`

### /setspawn
- Usage: `/setspawn`
- Description: `Set the spawn of the actual world`
- Permission: `dotplugin.commands.setspawn`

### /setlobby
- Usage: `/setlobby`
- Description: `Set the lobby`
- Permission: `dotplugin.commands.setlobby`

### /ranks
- Usages:
  - `/ranks`
  - `/ranks create <name> (-pf <prefix>) (-sf <suffix>) (-p <perms>,...)`
  - `/ranks remove <name>`
  - `/ranks modify <name> (-pf <prefix>) (-sf <suffix>) (-p <perms>,...)`
- Alias: `rank`
- Description: `Manage ranks`

### /drl
- Usage: `/drl`
- Aliases: `dotreload,dotrl,dotpluginreload,dotpluginrl,dprl`
- Description: `Reload the Dot Plugin`
- Permission: `dotplugin.commands.drl`


## Api

### Player

### Rank

### Text

### Gui
Here is an example of a simple gui
```java
package com.example.exampleplugin;

import fr.dotmazy.dotplugin.util.gui.*;
import org.bukkit.inventory.Material;
import org.bukkit.entity.Player;

public class ExampleGui extends AbstractGui {
    private void init(){
        makeFrame(new GuiItem(Material.STONE).onLeftClick((player,item,number)->{
            player.sendMessage("Please don't touch the decoration !");
        },true),1);
        
        setItem(new GuiItem(Material.SLIME).setDisplayName("Example Item").onMiddleClick((player,item,number)->{
            player.sendMessage("This is an example");
        },true),0);
    }
    
    private void onOpen(Player player){
        player.sendMessage("You have open the example gui.");
    }
    
    private void onClose(Player player){
        player.sendMessage("You have closed the example gui.");
    }
    
    private String getName(){
        return "Example Gui";
    }
    
    private GuiType getType(){
        return GuiType.CHEST;
    }
    
    private int getSize(){
        return 54;
    }
    
    private boolean isDefaultCancelled(){
        return true;
    }
}
```

Here is an example of a multi gui
```java
package com.example.exampleplugin;

import fr.dotmazy.dotplugin.util.gui.*;
import org.bukkit.inventory.Material;
import org.bukkit.entity.Player;

public class ExampleMultiGui extends AbstractMultiGui{
    private void init(){
        addGui(new ExampleGui1());
        addGui(new ExampleGui2());
    }

    private class ExampleGui1 extends AbstractGui{
        private void init(){
            setItem(new GuiItem(Material.RED_WOOL).setDisplayName("Next").onLeftClick((player,item,number)->{
                player.openInventory(guis.get(1).getInventory());
            }),8);
        }

        private String getName(){
            return "Example Gui 1";
        }

        private int getSize(){
            return 9;
        }

        private boolean isDefaultCancel(){
            return true;
        }
    }

    private class ExampleGui2 extends AbstractGui{
        private void init(){
            setItem(new GuiItem(Material.RED_WOOL).setDisplayName("Previous").onLeftClick((player,item,number)->{
                player.openInventory(guis.get(0).getInventory());
            }),0);
        }

        private String getName(){
            return "Example Gui 2";
        }

        private int getSize(){
            return 9;
        }

        private boolean isDefaultCancel(){
            return true;
        }
    }
}
```
