package fr.dotmazy.dotplugin.util.raw;

import java.util.ArrayList;
import java.util.List;

public class RawBuilder {
    private final List<Component> components;

    public RawBuilder(){
        this.components = new ArrayList<>();
    }

    public RawBuilder addComponent(Component component){
        components.add(component);
        return this;
    }

    public String toString(){
        String result = "[";
        boolean isFirst = true;
        for(Component component : components){
            if(!isFirst) result += ",";
            result+="{\"text\":\""+component.getText()+"\"";
            if(component.getInsertion() != null) result+=",\"insertion\":\""+component.getInsertion()+"\"";
            if(component.getClickEvent() != null) result+=",\"clickEvent\":{\"action\":\""+component.getClickEvent().getAction().toString()+"\",\"value\":\""+component.getClickEvent().getValue()+"\"}";
            if(component.getHoverEvent() != null) result+=",\"hoverEvent\":{\"action\":\""+component.getHoverEvent().getAction().toString()+"\",\"contents\":\""+component.getHoverEvent().getContents()+"\"}";
            if(component.isItalic()) result+=",\"italic\":\"true\"";
            if(component.isStrikethrough()) result+=",\"strikethrough\":\"true\"";
            if(component.isUnderlined()) result+=",\"underlined\":\"true\"";
            if(component.getColor() != null) result+=",\"color\":\""+component.getColor()+"\"";
            result+="}";
            isFirst=false;
        }
        return result+"]";
    }

}
