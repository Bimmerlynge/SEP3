package server.DAO;

import shared.User;

public interface IUserDAO {

    public void registerUser(User user);
    public User validateUser(User user);
    public User getUser(String username);

}
