package me.minion325.utils.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerUtils {

    private static String OBC = Bukkit.getServer().getClass().getPackage().getName();
    private static String NMS = OBC.replace("org.bukkit.craftbukkit", "net.minecraft.server");

    /**
     * Respawns a dead player so that they do not have to click
     * the respawn button. (Useful in minigames)
     *
     * @param player The player you would like to respawn.
     */
    public static void respawnPlayer(Player player) {
        try {
            Object enumClientCommand = Class.forName(NMS + ".PacketPlayInClientCommand$EnumClientCommand").getField("PERFORM_RESPAWN").get(null);
            Object packet = Class.forName(NMS + ".PacketPlayInClientCommand").getConstructor(enumClientCommand.getClass()).newInstance(enumClientCommand);

            Object nmsPlayer = (player).getClass().getMethod("getHandle").invoke(player);
            Object connection = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);

            connection.getClass().getMethod("a", packet.getClass()).invoke(connection, packet);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Gets the ping of a player.
     *
     * @param player the player you would like to get the ping of.
     * @return The player's ping.
     */

    public static int getPing(Player player) {
        try {
            Object nmsPlayer = (player).getClass().getMethod("getHandle").invoke(player);
            return (int) nmsPlayer.getClass().getField("ping").get(nmsPlayer);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
