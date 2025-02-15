package feup.sdle.utils;

public class Color {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    public static String red(String text) {
        return ANSI_RED + text + ANSI_RESET;
    }

    public static String yellow(String text) {
        return ANSI_YELLOW + text + ANSI_RESET;
    }

    public static String green(String text) {
        return ANSI_GREEN + text + ANSI_RESET;
    }

    private Color() {}
}
