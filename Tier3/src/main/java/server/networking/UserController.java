package server.networking;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;
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
    public synchronized void postUser(@RequestBody User user)
    {
        userDAO.registerUser(user);
    }

    @GetMapping("/users/{username}&{password}")
    public synchronized String validateUser(@PathVariable String username, @PathVariable String password){

        User user = userDAO.validateUser(new User(username, password, null));

        return new Gson().toJson(user);
    }



}
