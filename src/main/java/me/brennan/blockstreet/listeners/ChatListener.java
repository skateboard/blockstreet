package me.brennan.blockstreet.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/11/2020
 **/
public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerEarlyChat(AsyncPlayerChatEvent event) {

    }
}
