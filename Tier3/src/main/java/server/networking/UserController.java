package server.networking;

import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.DAO.IUserDAO;
import server.DAO.UserDAO;
import shared.User;

@RestController

public class UserController {

    private IUserDAO userDAO = new UserDAO();


    @PostMapping("/users")
    public synchronized Object postUser(@RequestBody User user)
    {
        try
        {
            userDAO.registerUser(user);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest();
        }

        return ResponseEntity.ok();
    }

    @GetMapping("/users/{username}&{password}")
    public synchronized Object validateUser(@PathVariable String username, @PathVariable String password){
        User user = null;
        try
        {
            user = userDAO.validateUser(new User(username, password, null));
        } catch (NullPointerException e){
            return ResponseEntity.notFound();
        }
        String userAsJson = new Gson().toJson(user);
        return ResponseEntity.ok(userAsJson);
    }



}
