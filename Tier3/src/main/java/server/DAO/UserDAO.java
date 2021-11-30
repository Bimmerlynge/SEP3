package server.DAO;

import shared.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO extends BaseDAO implements IUserDAO {

    @Override
    public void registerUser(User user) {

        try (Connection connection = getConnection()) {

      PreparedStatement preparedStatement = connection.prepareStatement(
          "insert into _User (username,password,role) VALUES  (?, ?, ? )");
      preparedStatement.setString(1, user.getUsername());
      preparedStatement.setString(2, user.getPassword());
      preparedStatement.setString(3, user.getRole());
      preparedStatement.executeUpdate();

    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
    }

  }

  @Override public User validateUser(User user)
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement preparedStatement = connection.prepareStatement(
          "SELECT * FROM _User WHERE username = (?) AND password = (?)");
      preparedStatement.setString(1, user.getUsername());
      preparedStatement.setString(2, user.getPassword());
      ResultSet resultSet = preparedStatement.executeQuery();
      User toReturn = null;
      if (resultSet.next())
      {
        toReturn = new User(resultSet.getString("username"), null,
            resultSet.getString("role"));

      }
      return toReturn;
    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
      return null;
    }

  }

  @Override public User getUser(String username)
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection
          .prepareStatement("SELECT * FROM _User WHERE username ILIKE ?");
      statement.setString(1, username);
      ResultSet resultSet = statement.executeQuery();
      User user = null;
      while (resultSet.next())
      {
        user = new User(resultSet.getString("username"),
            resultSet.getString("password"), resultSet.getString("role"));
      }
      return user;
    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
      return null;
    }
}
