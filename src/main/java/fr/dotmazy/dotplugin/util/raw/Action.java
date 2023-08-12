package fr.dotmazy.dotplugin.util.raw;

public enum Action{
    OPEN_URL("open_url"),
    RUN_COMMAND("run_command"),
    SUGGEST_COMMAND("suggest_command"),
    COPY_TO_CLIPBOARD("copy_to_clipboard");

    private final String name;

    Action(String name){
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
