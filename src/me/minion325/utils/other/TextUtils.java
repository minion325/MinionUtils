package me.minion325.utils.other;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class TextUtils {

    public static String colorMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String decolorMessage(String message) {
        return message.replace(ChatColor.COLOR_CHAR, '&');
    }

    public static List<String> colorMessages(List<String> messages) {
        List<String> toReturn = new ArrayList<>();

        for (String msg : messages) {
            toReturn.add(colorMessage(msg));
        }

        return toReturn;
    }

    public static List<String> decolorMessages(List<String> messages) {
        List<String> toReturn = new ArrayList<>();

        for (String msg : messages) {
            toReturn.add(decolorMessage(msg));
        }

        return toReturn;
    }

}
