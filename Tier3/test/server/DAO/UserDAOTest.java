package server.DAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shared.User;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
public class UserDAOTest
{
  private IUserDAO userDAO = new UserDAO();
  private String username = "";

  @BeforeEach
  void setUp(){
    Random rand = new Random();
    int i = rand.nextInt(5000);
    username = "TestName" + i;
  }

  @Test
  void TestUsernameNullThrows(){
    User user = new User("", "123456", "StandardUser");
    assertThrows(Exception.class, ()->userDAO.registerUser(user));
  }
  @Test
  void TestPassordNullThrows(){
    User user = new User(username, "", "StandardUser");
    assertThrows(Exception.class, ()->userDAO.registerUser(user));
  }

  @Test
  void RegisterUserWorks(){
    User user = new User(username, "123456", "StandardUser");
    userDAO.registerUser(user);
    assertDoesNotThrow(()->userDAO.getUser(user.getUsername()));
  }

  @Test
  void ValidateUserNoUsername(){
    User user = new User("", "123456", "StandardUser");
    assertThrows(Exception.class, ()->userDAO.registerUser(user));
  }

  @Test
  void ValidateUserNoPassword(){
    User user = new User(username, "", "StandardUser");
    assertThrows(Exception.class, ()->userDAO.registerUser(user));
  }

  @Test
  void ValidateUserWorks(){
    User user = new User(username, "UniquePassword", "StandardUser");
    userDAO.registerUser(user);

    User gotBack = userDAO.validateUser(user);

    assertEquals(user.getUsername(), gotBack.getUsername());

  }


}
