package controller.session;

public interface Session {
    void login(String username, String password);
    void register(String username, String email, String password);
}
