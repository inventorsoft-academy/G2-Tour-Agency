package co.inventorsoft.touragency.model.validation;

import co.inventorsoft.touragency.model.User;
import co.inventorsoft.touragency.util.validation.BasicValidator;

public class UserFactory implements BaseFactory<User> {

    private String username;
    private String password;
    private String email;
    private boolean isAdmin;
    private String agency;

    public UserFactory(String username, String password, String email,
                       boolean isAdmin, String agency) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isAdmin = isAdmin;
        this.agency = agency;
    }

    @Override
    public User create() {
        boolean isUsernameValid = BasicValidator.validateCredential(username);
        boolean isPasswordValid = BasicValidator.validateCredential(password);
        boolean isEmailValid = BasicValidator.validateEmail(email);
        boolean isAgencyValid = BasicValidator.validateAgency(agency);

        if (isUsernameValid && isPasswordValid && isEmailValid && isAgencyValid) {
            return new User(username, password, email, isAdmin, agency);
        }

        return null;
    }
}
