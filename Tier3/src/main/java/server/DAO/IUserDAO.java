package server.DAO;

import shared.User;

public interface IUserDAO {

    void registerUser(User user);
    User validateUser(User user);
    User getUser(String username);

}
