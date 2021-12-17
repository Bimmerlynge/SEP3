package server.DAO;

import shared.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interaktion med databasen hvad angår Users.
 *
 * Her indgår det at registerer en bruger og validere om en bruger findes i systemet.
 * Man kan også GetUser på username
 */
public class UserDAO extends BaseDAO implements IUserDAO
{


  @Override public void registerUser(User user)
  {
    if (user.getUsername().isEmpty() || user.getUsername().isBlank() || user.getPassword().isBlank() || user.getPassword().isBlank())
      throw new IllegalArgumentException();
    try (Connection connection = getConnection())
    {

      PreparedStatement preparedStatement = connection.prepareStatement(
          "insert into _User (username,password,role) VALUES  (?, ?, ? )");
      preparedStatement.setString(1, user.getUsername());
      preparedStatement.setString(2, user.getPassword());
      preparedStatement.setString(3, user.getRole());
      int rowsEffected = preparedStatement.executeUpdate();

      if (rowsEffected == 0){
        throw new IllegalArgumentException("No rows effected by call");
      }
    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
      throw new InternalError(throwables.getMessage());
    }

  }

  @Override public User validateUser(User user)
  {
    if (user.getUsername().isEmpty() || user.getUsername().isBlank() || user.getPassword().isBlank() || user.getPassword().isBlank())
      throw new IllegalArgumentException();
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
        toReturn = new User(resultSet.getString("username"), resultSet.getString("password"),
            resultSet.getString("role"));
      }

      if (toReturn == null){
        throw new NullPointerException("No user found");
      }
      return toReturn;
    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
      throw new InternalError(throwables.getMessage());
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
      throw new InternalError(throwables.getMessage());
    }
  }
}
