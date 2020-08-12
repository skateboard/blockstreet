package me.brennan.blockstreet.command;

import me.brennan.blockstreet.BlockStreet;
import me.brennan.blockstreet.util.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/12/2020
 **/
public class EmploymentCommand implements CommandExecutor {
    private final BlockStreet blockStreet;

    public EmploymentCommand(BlockStreet blockStreet) {
        this.blockStreet = blockStreet;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if(!commandSender.hasPermission("blockstreet.use.employment")) {
                Logger.sendMessage(player, ChatColor.RED, "You do not have the permission level for that!");
                return false;
            }

            switch (args[0]) {
                case "requests":
                case "show":
                    blockStreet.getGuiManager().openEmploymentInventory(player);
                    break;
            }

        }

        return false;
    }
}
