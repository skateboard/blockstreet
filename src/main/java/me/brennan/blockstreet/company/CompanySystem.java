package me.brennan.blockstreet.company;

import me.brennan.blockstreet.BlockStreet;
import me.brennan.blockstreet.company.object.CEO;
import me.brennan.blockstreet.company.object.Employee;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/9/2020
 **/
public class CompanySystem {
    private final Connection connection = BlockStreet.INSTANCE.getConnection();

    public void loadCompanies() {
        if(connection != null) {
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM companies");
                final ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    final String name = resultSet.getString("name");
                    final String symbol = resultSet.getString("symbol");
                    final double balance = resultSet.getDouble("balance");
                    final UUID foundersUUID = UUID.fromString(resultSet.getString("founder_uuid"));
                    final UUID ceoUUID = UUID.fromString(resultSet.getString("current_ceo"));
                    final String open = resultSet.getString("open");

                    final Company company = new Company(name, symbol, balance, open, foundersUUID, new CEO(ceoUUID, name));

                    final PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM employees");
                    final ResultSet resultSet1 = preparedStatement1.executeQuery();

                    while (resultSet1.next()) {
                        final UUID uuid = UUID.fromString(resultSet1.getString("uuid"));
                        final String companyName = resultSet1.getString("company");
                        final String role = resultSet1.getString("role");

                        if(companyName.equalsIgnoreCase(name))
                            company.addEmployee(new Employee(uuid, companyName, role));
                    }

                    BlockStreet.INSTANCE.getCompanyManager().add(company);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setCEO(String name, UUID uuid) {
        if(connection != null) {
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE companies SET current_ceo = ? WHERE name = ?");
                preparedStatement.setString(1, uuid.toString());
                preparedStatement.setString(2, name);

                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setBalance(String name, double balance) {
        if(connection != null) {
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE companies SET balance = ? WHERE name = ?");
                preparedStatement.setDouble(1, balance);
                preparedStatement.setString(2, name);

                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isPrivateCompany(String symbol) {
        if(connection != null) {
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement("SELECT open FROM companies WHERE symbol = ?");
                preparedStatement.setString(1, symbol);
                final ResultSet resultSet = preparedStatement.executeQuery();

                return resultSet.getString("open").equalsIgnoreCase("yes");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public void deleteCompany(String name) {
        if(connection != null) {
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM companies WHERE name = ?");
                preparedStatement.setString(1, name);

                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean doesCompanyExist(String name) {
        if(connection != null) {
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM companies WHERE name = ?");
                preparedStatement.setString(1, name);
                final ResultSet resultSet = preparedStatement.executeQuery();

                return resultSet.isBeforeFirst();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public void openCompany(String name) {
        if(connection != null) {
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE companies SET open = ? WHERE name = ?");
                preparedStatement.setString(1, "Yes");
                preparedStatement.setString(2, name);

                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addEmployee(Employee employee) {
        if(connection != null) {
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO employees(uuid, company, role) VALUES (?, ?, ?)");
                preparedStatement.setString(1, employee.getUuid().toString());
                preparedStatement.setString(2, employee.getCompany());
                preparedStatement.setString(3, employee.getRole());

                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Company createCompany(Player player, String name, String symbol, double balance) {
        if(connection != null) {
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO companies(name, symbol, balance, founder_uuid, current_ceo) VALUES (?, ?, ?, ?, ?)");
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, symbol);
                preparedStatement.setDouble(3, balance);
                preparedStatement.setString(4, player.getUniqueId().toString());
                preparedStatement.setString(5, player.getUniqueId().toString());

                preparedStatement.execute();

                final Company company = new Company(name, symbol, balance, "no", player.getUniqueId(), new CEO(player.getUniqueId(), name));
                final Employee ceoEmployee = new Employee(player.getUniqueId(), company.getName(), "CEO");
                company.addEmployee(ceoEmployee);

                addEmployee(ceoEmployee);

                return company;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public boolean isSymbolTaken(String symbol) {
        if(connection != null) {
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM companies WHERE symbol = ?");
                preparedStatement.setString(1, symbol);
                final ResultSet resultSet = preparedStatement.executeQuery();

                return resultSet.isBeforeFirst();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return true;
    }


}
