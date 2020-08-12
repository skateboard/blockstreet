package me.brennan.blockstreet.transaction;

import me.brennan.blockstreet.BlockStreet;
import me.brennan.blockstreet.transaction.object.Transaction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/9/2020
 **/
public class TransactionManager {
    private final List<Transaction> transactions = new LinkedList<>();

    public void createTransaction(Transaction transaction) {
        if(BlockStreet.INSTANCE.getConnection() != null) {
            try {
                final PreparedStatement preparedStatement = BlockStreet.INSTANCE.getConnection().prepareStatement("INSERT INTO transactions(owner_uuid, uuid, symbol, date, type, quantity, total) VALUES (?, ?, ?, ? ,?, ?, ?)");
                preparedStatement.setString(1, transaction.getOwnerUUID().toString());
                preparedStatement.setString(2, transaction.getUuid().toString());
                preparedStatement.setString(3, transaction.getSymbol());
                preparedStatement.setString(4, transaction.getDate());
                preparedStatement.setString(5, transaction.getType());
                preparedStatement.setInt(6, transaction.getQuantity());
                preparedStatement.setDouble(7, transaction.getQuantity());
                preparedStatement.execute();

                final PreparedStatement preparedStatement1 = BlockStreet.INSTANCE.getConnection().prepareStatement("SELECT id FROM transactions WHERE uuid = ? and owner_uuid = ? ");
                preparedStatement1.setString(1, transaction.getUuid().toString());
                preparedStatement1.setString(2, transaction.getOwnerUUID().toString());
                final ResultSet resultSet = preparedStatement1.executeQuery();

                transaction.setId(resultSet.getInt("id"));
                transactions.add(transaction);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void loadTransactions() {
        if(BlockStreet.INSTANCE.getConnection() != null) {
            try {
                final PreparedStatement preparedStatement = BlockStreet.INSTANCE.getConnection().prepareStatement("SELECT * FROM transactions");
                final ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    final int id = resultSet.getInt("id");
                    final String ownerUUID = resultSet.getString("owner_uuid");
                    final String uuid = resultSet.getString("uuid");
                    final String symbol = resultSet.getString("symbol");
                    final String date = resultSet.getString("date");
                    final String type = resultSet.getString("type");
                    final int quantity = resultSet.getInt("quantity");
                    final double total = resultSet.getDouble("total");

                    add(new Transaction(id, UUID.fromString(ownerUUID), UUID.fromString(uuid), symbol, date, type, quantity, total));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void add(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
