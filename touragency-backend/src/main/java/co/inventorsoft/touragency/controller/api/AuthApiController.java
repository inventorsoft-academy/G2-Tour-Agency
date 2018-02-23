package co.inventorsoft.touragency.controller.api;

import co.inventorsoft.touragency.model.User;
import co.inventorsoft.touragency.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Class {@code AuthApiController} provides implememtation of the applications
 * Authentication API
 * */
@CrossOrigin(origins = "*", methods = {GET, POST, PUT, DELETE, OPTIONS})
@RestController
@RequestMapping(value = "/auth")
public class AuthApiController {

    private AuthenticationService authenticationService;

    @Autowired
    public AuthApiController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping(value = "/user", headers = {"username", "password"})
    public ResponseEntity<User> authenticateUser(@RequestHeader("username") String username,
                                                 @RequestHeader("password") String password) {
        return ResponseEntity.ok(authenticationService.login(username, password));
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(authenticationService.registerUser(user.getUsername(),
                user.getPassword(), user.getEmail(), user.isAdmin(),
                user.getAgency()));
    }
}
