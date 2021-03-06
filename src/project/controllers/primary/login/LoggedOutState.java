package project.controllers.primary.login;

import project.controllers.primary.I_UserController;
import project.controllers.primary.ViewController;
import project.controllers.repository.UserRepositoryController;
import project.exceptions.LoginException;
import project.exceptions.ObjectNotFoundException;
import project.models.users.User;

/**
 * A class that represents that there is no one logged into the system.
 */
public class LoggedOutState implements I_LoginState {
    private String _username;
    private String _password;

    /**
     * Default constructor. Creates an empty LoggedOutState.
     */
    public LoggedOutState() {
        _username = null;
        _password = null;
    }

    /**
     * @param username the new value to set _username to.
     */
    public void setUsername(String username) {
        _username = username;
    }

    /**
     * @param password the new value to set _password to.
     */
    public void setPassword(String password) {
        _password = password;
    }

    /**
     * An attempt to login.
     *
     * @param controller the LoginHandler.
     */
    @Override
    public void login(LoginController controller) throws LoginException{
        UserRepositoryController repositoryController = UserRepositoryController.getInstance();

        if(_username == null || _password == null){
            controller.setState(this);
            throw new LoginException();

        }else{
            if(repositoryController.contains(_username)){
                try {
                    User user = repositoryController.get(_username);

                    if (user.getPassword() == ( _password.hashCode() )) {
                        showUserIndex(controller, user);

                    } else {
                        controller.setState(new LoggedOutState());
                        throw new LoginException("Username or password is incorrect.");
                    }

                }catch (ObjectNotFoundException e){
                    e.printStackTrace();
                }

            }else{
                controller.setState(new LoggedOutState());
                throw new LoginException("User does not exist.");
            }
        }

    }

    /**
     * An attempt to logout.
     *
     * @param controller the LoginHandler.
     */
    @Override
    public void logout(LoginController controller) throws LoginException {
        controller.setState(this);
        throw new LoginException("Cannot logout if no one is logged in.");
    }

    /**
     * Show the user's index.
     *
     * @param controller the LoginController.
     * @param user the resultant user.
     */
    private void showUserIndex(LoginController controller, User user){
        controller.setState(new LoggedInState(user));

        I_UserController userController = user.getId().getRole().getViewController(user);
        ViewController.getInstance().show(userController.index());
    }
}
