package fr.dotmazy.dotplugin.util.raw;

public class ClickEvent{
    private final Action action;
    private final String value;

    public ClickEvent(Action action, String value){
        this.action = action;
        this.value = value;
    }

    public Action getAction() {
        return action;
    }

    public String getValue() {
        return value;
    }
}
