package fr.dotmazy.dotplugin.util;

public class ConsoleColors {
    public static String RESET = "\u001B[0m";
    public static String BLACK = "\u001B[30m";
    public static String DARK_BLUE = "\u001B[34m";
    public static String DARK_GREEN = "\u001B[32m";
    public static String DARK_AQUA = "\u001B[36m";
    public static String DARK_RED = "\u001B[31m";
    public static String DARK_PURPLE = "\u001B[35m";
    public static String GOLD = "\u001B[33m";
    public static String GRAY = "\u001B[90m";
    public static String DARK_GRAY = "\u001B[37m";
    public static String BLUE = "\u001B[94m";
    public static String GREEN = "\u001B[92m";
    public static String AQUA = "\u001B[96m";
    public static String RED = "\u001B[91m";
    public static String LIGHT_PURPLE = "\u001B[95m";
    public static String YELLOW = "\u001B[93m";
    public static String WHITE = "\u001B[97m";
    public static String B_BLACK = "\u001B[40m";
    public static String B_DARK_BLUE = "\u001B[44m";
    public static String B_DARK_GREEN = "\u001B[42m";
    public static String B_DARK_AQUA = "\u001B[46m";
    public static String B_DARK_RED = "\u001B[41m";
    public static String B_DARK_PURPLE = "\u001B[45m";
    public static String B_GOLD = "\u001B[43m";
    public static String B_GRAY = "\u001B[100m";
    public static String B_DARK_GRAY = "\u001B[47m";
    public static String B_BLUE = "\u001B[104m";
    public static String B_GREEN = "\u001B[102m";
    public static String B_AQUA = "\u001B[106m";
    public static String B_RED = "\u001B[101m";
    public static String B_LIGHT_PURPLE = "\u001B[105m";
    public static String B_YELLOW = "\u001B[103m";
    public static String B_WHITE = "\u001B[107m";

    public static String format(String text){
        return text
                .replaceAll("&0",BLACK)
                .replaceAll("&1",DARK_BLUE)
                .replaceAll("&2",DARK_GREEN)
                .replaceAll("&3",DARK_AQUA)
                .replaceAll("&4",DARK_RED)
                .replaceAll("&5",DARK_PURPLE)
                .replaceAll("&6",GOLD)
                .replaceAll("&7",GRAY)
                .replaceAll("&8",DARK_GRAY)
                .replaceAll("&9",BLUE)
                .replaceAll("&a",GREEN)
                .replaceAll("&b",AQUA)
                .replaceAll("&c",RED)
                .replaceAll("&d",LIGHT_PURPLE)
                .replaceAll("&e",YELLOW)
                .replaceAll("&f",WHITE)
                .replaceAll("&b0",B_BLACK)
                .replaceAll("&b1",B_DARK_BLUE)
                .replaceAll("&b2",B_DARK_GREEN)
                .replaceAll("&b3",B_DARK_AQUA)
                .replaceAll("&b4",B_DARK_RED)
                .replaceAll("&b5",B_DARK_PURPLE)
                .replaceAll("&b6",B_GOLD)
                .replaceAll("&b7",B_GRAY)
                .replaceAll("&b8",B_DARK_GRAY)
                .replaceAll("&b9",B_BLUE)
                .replaceAll("&ba",B_GREEN)
                .replaceAll("&bb",B_AQUA)
                .replaceAll("&bc",B_RED)
                .replaceAll("&bd",B_LIGHT_PURPLE)
                .replaceAll("&be",B_YELLOW)
                .replaceAll("&bf",B_WHITE)
                .replaceAll("&tr",RESET)+RESET;
    }

    public static void info(String text){
        System.out.println(format(text));
    }

    public static void err(String text){
        System.err.println(format(text));
    }
}
