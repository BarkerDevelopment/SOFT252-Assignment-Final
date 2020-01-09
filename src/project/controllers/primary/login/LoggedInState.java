package project.controllers.primary.login;

import project.controllers.serialisation.RepositorySerialisationController;
import project.exceptions.LoginException;
import project.models.users.User;

/**
 * A class that represents that there is someone logged into the system.
 */
public class LoggedInState implements I_LoginState {
    private final User _user;

    /**
     * Default constructor. It saves the logged in user.
     * @param user the logged in user.
     */
    public LoggedInState(User user){
        _user = user;
    }

    /**
     * @return the _user variable. Represents the logged in user.
     */
    public User getUser() {
        return _user;
    }

    /**
     * An attempt to login.
     *
     * @param controller the LoginHandler.
     */
    @Override
    public void login(LoginController controller) throws LoginException{
        controller.setState(this);
        throw new LoginException("Cannot login because a user is already logged in.");
    }

    /**
     * An attempt to logout.
     *
     * @param controller the LoginHandler
     */
    @Override
    public void logout(LoginController controller) {
        RepositorySerialisationController.saveAll();
        controller.setState(new LoggedOutState());
    }
}
