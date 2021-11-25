package server.DAO;

import shared.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO extends BaseDAO implements IUserDAO {

    @Override
    public void registerUser(User user) {

        try (Connection connection = getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("insert into _User (username,password,role) VALUES  (?, ?, ? )");
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole());
            preparedStatement.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public User validateUser(User user) {


        return null;
    }
}
