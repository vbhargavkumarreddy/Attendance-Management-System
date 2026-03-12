package attendance.ui;

import java.io.Console;
import java.util.Scanner;

public class ConsoleUI {
    public static final String RESET  = "\033[0m";
    public static final String BOLD   = "\033[1m";
    public static final String GREEN  = "\033[32m";
    public static final String RED    = "\033[31m";
    public static final String YELLOW = "\033[33m";
    public static final String CYAN   = "\033[36m";
    public static final String BLUE   = "\033[34m";
    public static final String WHITE  = "\033[97m";

    public static final Scanner sc = new Scanner(System.in);

    public static void printHeader(String title) {
        String line = repeat("═", 60);
        System.out.println(CYAN + BOLD);
        System.out.println("╔" + line + "╗");
        System.out.printf("║  %-57s║%n", title);
        System.out.println("╚" + line + "╝" + RESET);
    }

    public static void printBanner() {
        System.out.println(CYAN + BOLD);
        System.out.println("  ╔══════════════════════════════════════════════╗");
        System.out.println("  ║      ATTENDANCE MANAGEMENT SYSTEM  v1.0     ║");
        System.out.println("  ║         Educational & Corporate Tool        ║");
        System.out.println("  ╚══════════════════════════════════════════════╝");
        System.out.println(RESET);
    }

    public static void printSuccess(String msg) {
        System.out.println(GREEN + BOLD + "  ✔ " + msg + RESET);
    }

    public static void printError(String msg) {
        System.out.println(RED + BOLD + "  ✘ " + msg + RESET);
    }

    public static void printInfo(String msg) {
        System.out.println(YELLOW + "  ℹ " + msg + RESET);
    }

    public static void printDivider() {
        System.out.println(CYAN + "  " + repeat("─", 58) + RESET);
    }

    public static void printTableRow(String... cells) {
        StringBuilder sb = new StringBuilder("  │");
        for (String cell : cells) {
            sb.append(String.format(" %-18s│", cell));
        }
        System.out.println(sb);
    }

    public static void printTableHeader(String... headers) {
        StringBuilder top = new StringBuilder("  ┌");
        StringBuilder mid = new StringBuilder("  │");
        StringBuilder bot = new StringBuilder("  ├");
        for (int i = 0; i < headers.length; i++) {
            top.append("────────────────────");
            mid.append(String.format(" %-18s│", headers[i]));
            bot.append("────────────────────");
            if (i < headers.length - 1) { top.append("┬"); bot.append("┼"); }
        }
        top.append("┐"); bot.append("┤");
        System.out.println(CYAN + top + RESET);
        System.out.println(BOLD + mid + RESET);
        System.out.println(CYAN + bot + RESET);
    }

    public static void printTableFooter(int cols) {
        StringBuilder bot = new StringBuilder("  └");
        for (int i = 0; i < cols; i++) {
            bot.append("────────────────────");
            if (i < cols - 1) bot.append("┴");
        }
        bot.append("┘");
        System.out.println(CYAN + bot + RESET);
    }

    public static String prompt(String label) {
        System.out.print(YELLOW + "  → " + label + ": " + RESET);
        return sc.nextLine().trim();
    }

    public static String promptPassword(String label) {
        System.out.print(YELLOW + "  → " + label + ": " + RESET);

        // if we have a real console object we can use its built-in password
        // reader which hides input.  unfortunately this returns null when the
        // program is launched from an IDE or some wrappers, so we also provide
        // a fallback that works everywhere.
        Console console = System.console();
        if (console != null) {
            char[] pwd = console.readPassword();
            System.out.println(); // move to next line after user hits enter
            return new String(pwd);
        }

        // fallback: read directly from System.in and echo '*' for each
        // character so the user has some visual feedback.  this works in the
        // VS Code integrated terminal, Windows `cmd`, etc.
        StringBuilder sb = new StringBuilder();
        try {
            int ch;
            while ((ch = System.in.read()) != -1) {
                if (ch == '\n' || ch == '\r') {
                    break;
                }
                sb.append((char) ch);
                System.out.print("*");
            }
            System.out.println();
            return sb.toString().trim();
        } catch (java.io.IOException e) {
            // if something goes wrong just fall back to Scanner so the app
            // remains usable.
            return sc.nextLine().trim();
        }
    }

    public static void pause() {
        System.out.print(CYAN + "\n  Press ENTER to continue..." + RESET);
        sc.nextLine();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Compatibility helper to repeat a string several times.
     * Java 11 introduced String.repeat; older runtimes require a manual implementation.
     */
    private static String repeat(String s, int count) {
        if (count <= 0) return "";
        StringBuilder sb = new StringBuilder(s.length() * count);
        for (int i = 0; i < count; i++) {
            sb.append(s);
        }
        return sb.toString();
    }
}
