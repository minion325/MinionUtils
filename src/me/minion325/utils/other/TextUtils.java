package me.minion325.utils.other;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class TextUtils {

    private TextUtils(){}

    /**
     * Converts "%26" color codes to minecraft codes.
     * @param message The uncolored message to be colored.
     * @return A coloured version of the message.
     */
    public static String colorMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Removes color from a message and replaces minecraft color codes with "%26".
     * @param message The colored messaged to be decolorized.
     * @return A decolored version of the message
     */
    public static String decolorMessage(String message) {
        return message.replace(ChatColor.COLOR_CHAR, '&');
    }

    /**
     * Converts "%26" color codes to minecraft codes for multiple messages.
     * @param messages The uncolored messages to be colored.
     * @return A coloured version of the messages.
     */
    public static List<String> colorMessages(List<String> messages) {
        List<String> toReturn = new ArrayList<>();

        for (String msg : messages) {
            toReturn.add(colorMessage(msg));
        }

        return toReturn;
    }

    /**
     * Removes color from messages and replaces minecraft color codes with "%26".
     * @param messages The colored messages to be decolorized.
     * @return A decolored version of the messages.
     */

    public static List<String> decolorMessages(List<String> messages) {
        List<String> toReturn = new ArrayList<>();

        for (String msg : messages) {
            toReturn.add(decolorMessage(msg));
        }

        return toReturn;
    }

}
