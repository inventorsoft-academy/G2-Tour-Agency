package co.inventorsoft.touragency.service;

import co.inventorsoft.touragency.controller.dao.BaseDao;
import co.inventorsoft.touragency.controller.dao.files.UserFileSystemDao;
import co.inventorsoft.touragency.model.User;
import co.inventorsoft.touragency.model.validation.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {
    private BaseDao<User> dao;

    /**
     * A {@link List} collection that contains all instances of {@link User} objects that
     * correspond to registered users.
     * */
    private List<User> users;


    @Autowired
    public AuthenticationService(UserFileSystemDao userDao) {
        this.dao = userDao;
    }

    @PostConstruct
    private void readUsers() {
        users = dao.getAll();
    }

    /**
     * This method is invoked when authentication service has done all of its tasks for
     * a particular use case and is no longer required by other objects of the application.
     * The main function of the {@code close()} method is to perform save operation of
     * users' dao modified in memory during this service's lifecycle. This method
     * guarantees that in case of <strong>PROPER</strong> closing of the service all
     * modified dao would be safely stored and would not be lost.
     * This method does not take any parameters.
     * */
    @PreDestroy
    private void saveData() {
        boolean status = dao.saveAll(users);
    }

    /**
     * This method performs user authentication with the application and, in particular,
     * login operation. It takes two parameters that are user's username and password.
     * The username parameter must be unique in the application's system.
     * All users are stored in the same dao source and are distinguished as regular users
     * and administrators (moderators) by scanning a boolean flag that indicates a particular
     * user's administrative privileges.
     * This method returns {@link User} object which corresponds to the user with the same
     * username and the same password as those passed as parameters. If there is no such user,
     * an exception of class {@link co.inventorsoft.touragency.exceptions.UserNotFoundException}
     * will be thrown.
     * @param username username of a user who attempts to authenticate
     *                 @param password password of a user who attempts to authenticate
     *                                 @return an instance of a {@link User} objects that
     *                                 corresponds to a user whose credentials were found in the
     *                                 dao storage.
     * */
    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    /**
     * The {@code register()} method performs an operation to add a new user to the system.
     * It takes user credentials and performs checking of uniqueness of username and email.
     * If both of these are unique, then a new instance of {@link User} object is created and
     * then added to a {@link List} of users. After successfully performing register operation,
     * this method automatically invocation of {@code login()} method passing a username and a
     * password of the newly created user. It returns true if the register operation was
     * performed successfully and a new user was created. Otherwise, this method would return false.
     * @param username a username of a new user
     *                 @param password new user's password
     *                                 @param email a new user's email address
     *                                              @param isAdmin indicates if a user would have
     *                                                             administrative privileges
     *                                                             @param agency an employer agency of
     *                                                                           an administrator
     * @return true if a new user was successfully created.
     * */
    public boolean register(String username, String password, String email, boolean isAdmin,
                            String agency) {
        boolean canAddUser = true;
        for (User user : users) {
            if (user.getUsername().equals(username) ||
                    user.getEmail().equals(email)) {
                canAddUser = false;
                break;
            }
        }

        if (canAddUser) {
            Optional<User> userOptional = Optional.of(new UserFactory(username,
                    password, email, isAdmin, agency).create());
            userOptional.ifPresent(user -> users.add(user));
            return true;
        }

        return false;
    }

    public User registerUser(String username, String password, String email, boolean isAdmin,
                             String agency) {
        boolean result = register(username, password, email, isAdmin, agency);
        if (result) {
            return new UserFactory(username, password, email, isAdmin, agency).create();
        } else {
            return null;
        }
    }

    public List<User> getUsers() {
        return users;
    }
}
