package project.controllers.primary;

import project.controllers.primary.login.LoginController;
import project.controllers.repository.DrugRepositoryController;
import project.controllers.repository.RequestRepositoryController;
import project.models.users.Secretary;
import project.models.users.User;
import project.views.I_Form;
import project.views.secretary.SecretaryIndex;
import project.views.secretary.ViewDrugs;
import project.views.secretary.ViewRequests;

public class SecretaryController implements I_UserController {
    private ViewController _controller;
    private RequestRepositoryController _requestRepositoryController;
    private DrugRepositoryController _drugRepositoryController;
    private Secretary _user;

    public SecretaryController(User user) {
        _controller = ViewController.getInstance();
        _requestRepositoryController = RequestRepositoryController.getInstance();
        _drugRepositoryController = DrugRepositoryController.getInstance();
        _user = (Secretary) user;
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
     * @return the initial view for the user.
     */
    @Override
    public I_Form index() {
        return new SecretaryIndex(_controller, this);
    }

    /**
     * Show the user a form that shows theisystems requests.
     */
    public void viewRequests(){
        _controller.show(new ViewRequests(_controller, this, _requestRepositoryController));
    }

    /**
     * Show the user a form that shows the drugs in the system.
     */
    public void viewDrugs(){
        _controller.show(new ViewDrugs(_controller, this, _drugRepositoryController));
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
