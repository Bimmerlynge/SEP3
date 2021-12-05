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
    public ResponseEntity postUser(@RequestBody User user) throws URISyntaxException {
        try
        {
            userDAO.registerUser(user);
            URI uriToFindUser = new URI("http://localhost:8080/user/" + user.getUsername() +"&" + user.getPassword());
            return ResponseEntity.created(uriToFindUser).build();

        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/user")
    public ResponseEntity validateUser(@RequestParam String username, @RequestParam String password){
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
