package fr.dotmazy.dotplugin.util.raw;

public enum HoverAction {
    SHOW_TEXT("show_text"),
    SHOW_ACHIEVEMENT("show_achievement"),
    SHOW_ITEM("show_item"),
    SHOW_ENTITY("show_entity");

    private final String name;

    HoverAction(String name){
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
