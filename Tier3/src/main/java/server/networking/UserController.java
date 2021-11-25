package server.networking;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.DAO.ISongDAO;
import server.DAO.IUserDAO;
import server.DAO.SongDAO;
import server.DAO.UserDAO;
import shared.Song;
import shared.User;

import java.util.ArrayList;

@RestController

public class UserController {

    private IUserDAO userDAO = new UserDAO();


    @PostMapping("/users")
    public synchronized void postAllSongs(@RequestBody User user)
    {
        System.out.println("Getting post request");
        System.out.println("user name " + user);
        userDAO.registerUser(user);


    }



}
