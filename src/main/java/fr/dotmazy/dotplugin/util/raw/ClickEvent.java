package fr.dotmazy.dotplugin.util.raw;

public class ClickEvent{
    private final ClickAction action;
    private final String value;

    public ClickEvent(ClickAction action, String value){
        this.action = action;
        this.value = value;
    }

    public ClickAction getAction() {
        return action;
    }

    public String getValue() {
        return value;
    }
}
