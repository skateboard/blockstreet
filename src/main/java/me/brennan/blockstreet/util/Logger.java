package me.brennan.blockstreet.util;

import me.brennan.blockstreet.BlockStreet;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/15/2020
 **/
public class Logger {

    public static void printConsole(String message) {
        System.out.println(String.format("[%s] %s", "BlockStreet", message));
    }

    public static void printConsole(ChatColor chatColor, String message) {
        BlockStreet.getINSTANCE().getServer().getConsoleSender().sendMessage(String.format("[%s] %s%s", "BlockStreet", chatColor, message));
    }

    public static void sendMessage(Player player, String message) {
        player.sendMessage(String.format("%s[%s%s%s]%s %s", ChatColor.GRAY, ChatColor.GOLD, "BlockStreet", ChatColor.GRAY, ChatColor.WHITE, message));
    }

    public static void sendMessage(Player player, ChatColor chatColor, String message) {
        player.sendMessage(String.format("%s[%s%s%s]%s %s", ChatColor.GRAY, ChatColor.GOLD, "BlockStreet", ChatColor.GRAY, chatColor, message));
    }

    public static void broadCast(String message) {
        BlockStreet.getINSTANCE().getServer().broadcastMessage(String.format("%s[%s%s%s]%s %s", ChatColor.GRAY, ChatColor.GOLD, "BlockStreet", ChatColor.GRAY, ChatColor.WHITE, message));
    }

    public static void broadCast(ChatColor chatColor, String message) {
        BlockStreet.getINSTANCE().getServer().broadcastMessage(String.format("%s[%s%s%s]%s %s", ChatColor.GRAY, ChatColor.GOLD, "BlockStreet", ChatColor.GRAY, chatColor, message));
    }
}
