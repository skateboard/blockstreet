package me.brennan.blockstreet.stock;

import me.brennan.blockstreet.BlockStreet;
import me.brennan.blockstreet.stock.object.Stock;
import me.brennan.blockstreet.util.MySQL;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/9/2020
 **/
public class StockSystem {
    private final Connection connection = BlockStreet.INSTANCE.getConnection();

    public void loadStocks() {
        if(connection != null) {
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM stocks");
                final ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    final int id = resultSet.getInt("id");
                    final String symbol = resultSet.getString("symbol");
                    final String companyName = resultSet.getString("company_name");
                    final double price = resultSet.getDouble("price");
                    final double volume = resultSet.getDouble("volume");

                    BlockStreet.INSTANCE.getStockManager().getStocks().add(new Stock(id, symbol, companyName, price, volume));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setValue(String symbol, double price) {
        if(connection != null) {
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE stocks SET price = ? WHERE symbol = ?");
                preparedStatement.setDouble(1, price);
                preparedStatement.setString(2, symbol);

                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteStock(String symbol) {
        if(connection != null) {
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM stocks WHERE symbol = ?");
                preparedStatement.setString(1, symbol);

                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createStock(String symbol, String name) {
        if(connection != null) {
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO stocks(symbol, company_name) VALUES (?, ?)");
                preparedStatement.setString(1, symbol);
                preparedStatement.setString(2, name);

                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeCertainAmountOfStocks(Player player, String symbol, int amount) {
        if(connection != null) {
            try {
                for(int i = 0; i < amount; i++) {
                    final PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM owned_stocks WHERE owned_uuid = ? AND symbol = ? BETWEEN  1 AND ?");
                    preparedStatement.setString(1, player.getUniqueId().toString());
                    preparedStatement.setString(2, symbol);
                    preparedStatement.setInt(3, amount);

                    preparedStatement.executeQuery();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setVolume(String symbol, double volume) {
        if(connection != null) {
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE stocks SET volume = ? WHERE symbol = ?");
                preparedStatement.setDouble(1, volume);
                preparedStatement.setString(2, symbol);

                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addOwner(Player player, String symbol, int amount, int priceOfEach, int finalPrice) {
        if(connection != null) {
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO owned_stocks(owned_uuid, symbol, amount, price_each, final_price) VALUES (?, ?, ?, ?, ?)");
                preparedStatement.setString(1, player.getUniqueId().toString());
                preparedStatement.setString(2, symbol);
                preparedStatement.setInt(3, amount);
                preparedStatement.setInt(4, priceOfEach);
                preparedStatement.setInt(5, finalPrice);

                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int getPlayersStockAmount(Player player, String symbol) {
        if(connection != null) {
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) FROM owned_stocks WHERE owned_uuid = ? AND symbol = ?");
                preparedStatement.setString(1, player.getUniqueId().toString());
                preparedStatement.setString(2, symbol);
                final ResultSet set = preparedStatement.executeQuery();

                return set.getInt("count(*)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

}
