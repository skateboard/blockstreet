package me.brennan.blockstreet.util;

import me.brennan.blockstreet.BlockStreet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/9/2020
 **/
public class MySQL {
    private static final String URL = BlockStreet.INSTANCE.getConfig().getBoolean("use-localdb") ? "jdbc:sqlite:" + new File(BlockStreet.INSTANCE.getDataFolder(), "database.db")
            : String.format("jdbc:mysql://%s:%s/%s?user=%s&password=%s", BlockStreet.INSTANCE.getConfig().getString("host"), BlockStreet.INSTANCE.getConfig().getString("port"),
                                                                         BlockStreet.INSTANCE.getConfig().getString("database"), BlockStreet.INSTANCE.getConfig().getString("username"),
                                                                         BlockStreet.INSTANCE.getConfig().getString("password"));
    private static final String COMPANIES_TABLE = "CREATE TABLE \"companies\" (\n" +
            "\t\"id\"\tINTEGER,\n" +
            "\t\"name\"\tTEXT NOT NULL,\n" +
            "\t\"symbol\"\tTEXT NOT NULL,\n" +
            "\t\"balance\"\tNUMERIC,\n" +
            "\t\"founder_uuid\"\tTEXT NOT NULL,\n" +
            "\t\"current_ceo\"\tTEXT NOT NULL,\n" +
            "\t\"open\"\tTEXT NOT NULL DEFAULT 'no',\n" +
            "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
            ")";

    private static final String OWNED_STOCKS_TABLE = "CREATE TABLE \"owned_stocks\" (\n" +
            "\t\"id\"\tINTEGER NOT NULL,\n" +
            "\t\"owned_uuid\"\tTEXT NOT NULL,\n" +
            "\t\"symbol\"\tTEXT NOT NULL,\n" +
            "\t\"amount\"\tNUMERIC NOT NULL,\n" +
            "\t\"price_each\"\tNUMERIC NOT NULL,\n" +
            "\t\"final_price\"\tNUMERIC NOT NULL,\n" +
            "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
            ")";

    private static final String STOCKS_TABLE = "CREATE TABLE \"stocks\" (\n" +
            "\t\"id\"\tINTEGER NOT NULL,\n" +
            "\t\"symbol\"\tTEXT NOT NULL,\n" +
            "\t\"company_name\"\tTEXT NOT NULL,\n" +
            "\t\"price\"\tNUMERIC NOT NULL DEFAULT 0,\n" +
            "\t\"volume\"\tNUMERIC NOT NULL DEFAULT 0,\n" +
            "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
            ")";

    private static final String TRANSACTIONS_TABLE = "CREATE TABLE \"transactions\" (\n" +
            "\t\"id\"\tINTEGER NOT NULL,\n" +
            "\t\"owner_uuid\"\tTEXT NOT NULL,\n" +
            "\t\"uuid\"\tTEXT NOT NULL,\n" +
            "\t\"symbol\"\tTEXT NOT NULL,\n" +
            "\t\"date\"\tTEXT NOT NULL,\n" +
            "\t\"type\"\tTEXT NOT NULL,\n" +
            "\t\"quantity\"\tNUMERIC NOT NULL,\n" +
            "\t\"total\"\tNUMERIC NOT NULL,\n" +
            "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
            ")";

    private static final String EMPLOYEE_TABLE = "CREATE TABLE \"employees\" (\n" +
            "\t\"id\"\tINTEGER NOT NULL,\n" +
            "\t\"uuid\"\tTEXT NOT NULL,\n" +
            "\t\"company\"\tTEXT NOT NULL,\n" +
            "\t\"role\"\tTEXT NOT NULL,\n" +
            "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
            ")";



    public static boolean createDatabase() {
        final File dataFolder = new File(BlockStreet.INSTANCE.getDataFolder(), "database.db");

        if(dataFolder.exists()) {
            return false;
        } else {
            try {
                dataFolder.createNewFile();
            } catch (IOException e) {
                return false;
            }
        }

        try {
            if(getConnection() != null && !getConnection().isClosed()) {
                execute(COMPANIES_TABLE, getConnection());
                execute(OWNED_STOCKS_TABLE, getConnection());
                execute(STOCKS_TABLE, getConnection());
                execute(TRANSACTIONS_TABLE, getConnection());
                execute(EMPLOYEE_TABLE, getConnection());

                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(URL);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void execute(String statement, Connection connection) throws SQLException {
        final PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.execute();

        preparedStatement.close();
    }

}
