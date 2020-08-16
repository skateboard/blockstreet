package me.brennan.blockstreet.command;

import co.aikar.commands.PaperCommandManager;
import me.brennan.blockstreet.BlockStreet;
import me.brennan.blockstreet.command.impl.BlockStreetCommand;
import me.brennan.blockstreet.command.impl.CompanyCommand;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/15/2020
 **/
public class CommandManager {

    public void load() {
        final PaperCommandManager paperCommandManager = new PaperCommandManager(BlockStreet.getINSTANCE());
        //base commands
        paperCommandManager.registerCommand(new BlockStreetCommand());

        //company commands
        paperCommandManager.registerCommand(new CompanyCommand());

        //stock
    }

}
