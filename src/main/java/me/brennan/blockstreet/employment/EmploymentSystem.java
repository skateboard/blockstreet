package me.brennan.blockstreet.employment;

import me.brennan.blockstreet.BlockStreet;
import me.brennan.blockstreet.employment.object.Employment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/12/2020
 **/
public class EmploymentSystem {
    private final Connection connection = BlockStreet.INSTANCE.getConnection();

    public void addEmploymentRequest(Employment employmentRequest) {
        if(connection != null) {
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO employment_request(uuid, company, role) VALUES (?, ?, ?)");
                preparedStatement.setString(1, employmentRequest.getUuid().toString());
                preparedStatement.setString(2, employmentRequest.getCompany());
                preparedStatement.setString(3, employmentRequest.getRole());

                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
