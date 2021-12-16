package server.networking;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.DAO.IUserDAO;
import server.DAO.UserDAO;
import shared.User;
import java.net.URI;

/**
 * Controller end point håndtere requests angående Users
 *
 * ValidateUser fungere også som Log ind
 */
@RestController
public class UserController {

    private IUserDAO userDAO = new UserDAO();


    @PostMapping("/user")
    public ResponseEntity<URI> postUser(@RequestBody User user) {
        try {
            userDAO.registerUser(user);
            URI uriToFindUser = new URI("http://localhost:8080/user?username=" + user.getUsername() + "&password=" + user.getPassword());
            return ResponseEntity.created(uriToFindUser).build();
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        catch (Exception | InternalError e){
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Fungere også som login
     *
     * @param username username på user man prøver at validere
     * @param password password for user man prøver at validere
     * @return Hvis fundet returneres den givne user, eller relevant fejlkode.
     */
    @GetMapping("/user")
    public ResponseEntity<User> validateUser(@RequestParam String username, @RequestParam String password) {
        try {
            User user = userDAO.validateUser(new User(username, password, null));
            return ResponseEntity.ok(user);
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception | InternalError e){
            return ResponseEntity.internalServerError().build();
        }
    }


}
