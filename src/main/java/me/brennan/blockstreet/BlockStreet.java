package me.brennan.blockstreet;

import me.brennan.blockstreet.command.BlockStreetCommand;
import me.brennan.blockstreet.command.CompanyCommand;
import me.brennan.blockstreet.command.EmploymentCommand;
import me.brennan.blockstreet.command.StockCommand;
import me.brennan.blockstreet.company.Company;
import me.brennan.blockstreet.company.CompanyManager;
import me.brennan.blockstreet.company.CompanySystem;
import me.brennan.blockstreet.employment.EmploymentManager;
import me.brennan.blockstreet.employment.EmploymentSystem;
import me.brennan.blockstreet.gui.GUIManager;
import me.brennan.blockstreet.listeners.PlayerListener;
import me.brennan.blockstreet.stock.StockManager;
import me.brennan.blockstreet.transaction.TransactionManager;
import me.brennan.blockstreet.stock.StockSystem;
import me.brennan.blockstreet.util.Logger;
import me.brennan.blockstreet.util.MySQL;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/7/2020
 **/
public class BlockStreet extends JavaPlugin {
    public static BlockStreet INSTANCE;

    public final String VERSION = "1.0.0";

    private FileConfiguration config;

    private Connection connection;

    private Economy economy = null;

    private StockManager stockManager;
    private StockSystem stockSystem;

    private TransactionManager transactionManager;

    private CompanyManager companyManager;
    private CompanySystem companySystem;

    private EmploymentManager employmentManager;
    private EmploymentSystem employmentSystem;

    private GUIManager guiManager;

    @Override
    public void onEnable() {
        super.onEnable();
        if (!setupEconomy() ) {
            System.out.println(String.format("[%s] - Disabled because Vault was not found!", "BlockStreet"));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        INSTANCE = this;
        final File configFile = new File(this.getDataFolder(), "config.yml");
        this.config = new YamlConfiguration();

        if (!configFile.exists()) {
            try {
                InputStream stream = getResource("config.yml");
                Files.copy(stream, configFile.toPath());

                config.load(configFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(!MySQL.createDatabase()) {
            Logger.printConsole("Cannot load database!");
        }
        connection = MySQL.getConnection();

        stockSystem = new StockSystem();
        stockManager = new StockManager(this);
        stockSystem.loadStocks();

        transactionManager = new TransactionManager();
        transactionManager.loadTransactions();

        companySystem = new CompanySystem();
        companyManager = new CompanyManager();
        companySystem.loadCompanies();

        employmentManager = new EmploymentManager();
        employmentSystem = new EmploymentSystem();

        guiManager = new GUIManager(this);

        registerCommands();

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void registerCommands() {
        getCommand("stock").setExecutor(new StockCommand(this));
        getCommand("company").setExecutor(new CompanyCommand(this));
        getCommand("blockstreet").setExecutor(new BlockStreetCommand(this));
        getCommand("employment").setExecutor(new EmploymentCommand(this));
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

    public EmploymentManager getEmploymentManager() {
        return employmentManager;
    }

    public EmploymentSystem getEmploymentSystem() {
        return employmentSystem;
    }

    public GUIManager getGuiManager() {
        return guiManager;
    }

    public Connection getConnection() {
        return connection;
    }

    public StockSystem getStockSystem() {
        return stockSystem;
    }

    public CompanyManager getCompanyManager() {
        return companyManager;
    }

    public CompanySystem getCompanySystem() {
        return companySystem;
    }

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public StockManager getStockManager() {
        return stockManager;
    }

    public Economy getEconomy() {
        return economy;
    }
}
