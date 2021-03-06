package project.controllers.primary;

import project.controllers.primary.login.LoginController;
import project.controllers.repository.UserRepositoryController;
import project.models.users.Admin;
import project.models.users.User;
import project.models.users.info.UserRole;
import project.views.I_Form;
import project.views.admin.AdminIndex;
import project.views.admin.NewUser;
import project.views.admin.ViewUsers;

public class AdminController implements I_UserController {
    private ViewController _controller;
    private UserRepositoryController _userRepositoryController;
    private Admin _user;

    public AdminController(User user) {
        _controller = ViewController.getInstance();
        _userRepositoryController = UserRepositoryController.getInstance();
        _user = (Admin) user;
    }

    /**
     * @return the user.
     */
    @Override
    public User getUser() {
        return _user;
    }

    /**
     * Shows the initial view of the controller.
     * @return the initial view.
     */
    @Override
    public I_Form index() {
        return new AdminIndex(_controller, this);
    }

    /**
     * Shows a form that displays all the users.
     */
    public void viewUsers(){
        _controller.show(new ViewUsers(_controller, this, _userRepositoryController));
    }

    /**
     * Shows a form to the user to create a new user.
     * @param role the user type to create.
     */
    public void createUser(UserRole role){
        _controller.show(new NewUser(_controller, this, _userRepositoryController, role));
    }

    /**
     * Take the user back a form.
     */
    @Override
    public void back() {
        _controller.undo();
    }

    /**
     * Log the user out and return to the login screen.
     */
    @Override
    public void logout() {
        LoginController loginController = LoginController.getInstance();
        loginController.logout();

        _controller.show(loginController.index());
        _controller.clear();
    }
}
