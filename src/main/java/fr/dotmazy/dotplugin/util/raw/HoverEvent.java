package fr.dotmazy.dotplugin.util.raw;

import java.util.UUID;

public class HoverEvent {
    private final HoverAction action;
    private final String contents;

    public HoverEvent(HoverAction action, String contents){
        this.action = action;
        this.contents = contents;
    }

    public HoverEvent(HoverAction action, String type, String name, UUID uuid){
        this.action = action;
        this.contents = "{\"type\":\""+type+"\",\"name\":\""+name+"\",\"id\":\""+uuid.toString()+"\"}";
    }

    public HoverAction getAction() {
        return action;
    }

    public String getContents() {
        return contents;
    }
}
