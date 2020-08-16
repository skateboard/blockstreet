package me.brennan.blockstreet.player;

import me.brennan.blockstreetapi.player.PlayerData;
import me.brennan.blockstreetapi.player.PlayerManager;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/15/2020
 **/
public class AbstractPlayerManager implements PlayerManager {
    private final List<PlayerData> playerData = new LinkedList<>();

    @Override
    public List<PlayerData> getData() {
        return playerData;
    }

    @Override
    public PlayerData getPlayerData(UUID uuid) {
        return null;
    }

    @Override
    public PlayerData getPlayerData(String s) {
        return null;
    }

    @Override
    public boolean savePlayerData(PlayerData playerData) {
        return false;
    }

    @Override
    public PlayerData getOfflineData(UUID uuid) {
        return null;
    }

    @Override
    public PlayerData getOfflineData(String s) {
        return null;
    }
}
