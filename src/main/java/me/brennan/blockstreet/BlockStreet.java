package me.brennan.blockstreet;

import me.brennan.blockstreet.command.CommandManager;
import me.brennan.blockstreet.company.AbstractCompanyManager;
import me.brennan.blockstreet.player.AbstractPlayerManager;
import me.brennan.blockstreet.stock.AbstractStockManager;
import me.brennan.blockstreet.util.Logger;
import me.brennan.blockstreetapi.StocksPlugin;
import me.brennan.blockstreetapi.company.CompanyManager;
import me.brennan.blockstreetapi.player.PlayerManager;
import me.brennan.blockstreetapi.stock.StockManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/15/2020
 **/
public class BlockStreet extends JavaPlugin implements StocksPlugin {
    private static BlockStreet INSTANCE;

    public static final String VERSION = "1.1";

    private boolean marketStatus;

    private final StockManager stockManager = new AbstractStockManager();
    private final CompanyManager companyManager = new AbstractCompanyManager();
    private final PlayerManager playerManager = new AbstractPlayerManager();

    private final CommandManager commandManager = new CommandManager();

    private Economy economy = null;

    @Override
    public void onEnable() {
        super.onEnable();
        if(!setupEconomy()) {
            Logger.printConsole(ChatColor.RED, "Vault was not! Disabling BlockStreet");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        INSTANCE = this;

        commandManager.load();

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static BlockStreet getINSTANCE() {
        return INSTANCE;
    }

    public Economy getEconomy() {
        return economy;
    }

    @Override
    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    @Override
    public StockManager getStockManager() {
        return stockManager;
    }

    @Override
    public CompanyManager getCompanyManager() {
        return companyManager;
    }

    @Override
    public String[] getPopularStocks() {
        return new String[0];
    }

    @Override
    public void setMarketStatus(boolean status) {
        this.marketStatus = status;
    }

    @Override
    public boolean isMarketClosed() {
        return marketStatus;
    }

    @Override
    public double getTax() {
        return 0;
    }

    @Override
    public double getMaxTrade() {
        return 0;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        final RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        this.economy = rsp.getProvider();

        return economy != null;
    }
}
