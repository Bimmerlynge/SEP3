package server.networking;

import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.DAO.IUserDAO;
import server.DAO.UserDAO;
import shared.User;

import java.net.URI;
import java.net.URISyntaxException;

@RestController

public class UserController {

    private IUserDAO userDAO = new UserDAO();


    @PostMapping("/user")
    public synchronized ResponseEntity postUser(@RequestBody User user) throws URISyntaxException {
        try
        {
            userDAO.registerUser(user);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
        URI uriToFindUser = new URI("http://localhost:8080/user/" + user.getUsername() +"&" + user.getPassword());
        return ResponseEntity.created(uriToFindUser).build();
    }

    @GetMapping("/user/{username}&{password}")
    public synchronized ResponseEntity validateUser(@PathVariable String username, @PathVariable String password){
        User user = null;
        try
        {
            user = userDAO.validateUser(new User(username, password, null));
        } catch (NullPointerException e){

            return  ResponseEntity.notFound().build();
        }
        String userAsJson = new Gson().toJson(user);
        return ResponseEntity.ok(userAsJson);
    }



}
