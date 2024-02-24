package fr.dotmazy.dotplugin.util.raw;

public class Component{
    private final String text;
    private String insertion = null;
    private ClickEvent clickEvent = null;
    private HoverEvent hoverEvent = null;
    private boolean italic = false;
    private boolean underlined = false;
    private boolean strikethrough = false;
    private String color = null;

    public Component(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getInsertion() {
        return insertion;
    }

    public Component setInsertion(String insertion) {
        this.insertion = insertion;
        return this;
    }

    public ClickEvent getClickEvent() {
        return clickEvent;
    }

    public Component setClickEvent(ClickEvent event) {
        this.clickEvent = event;
        return this;
    }

    public HoverEvent getHoverEvent() {
        return hoverEvent;
    }

    public Component setHoverEvent(HoverEvent event) {
        this.hoverEvent = event;
        return this;
    }

    public boolean isItalic() {
        return italic;
    }

    public Component setItalic(boolean italic) {
        this.italic = italic;
        return this;
    }

    public boolean isStrikethrough() {
        return strikethrough;
    }

    public Component setStrikethrough(boolean strikethrough) {
        this.strikethrough = strikethrough;
        return this;
    }

    public boolean isUnderlined() {
        return underlined;
    }

    public Component setUnderlined(boolean underlined) {
        this.underlined = underlined;
        return this;
    }

    public String getColor() {
        return color;
    }

    public Component setColor(String color){
        this.color = color;
        return this;
    }
}
