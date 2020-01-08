package controllers.primary.login;

import controllers.primary.I_UserStoryIndexController;
import controllers.primary.ViewController;
import controllers.repository.RequestRepositoryController;
import views.I_Form;
import views.LoginForm;
import views.RegisterForm;

/**
 * A class that encapsulates logging in. It implements the state pattern to enforce either logged in or logged out.
 */
public class LoginController
        implements I_UserStoryIndexController {
    private static LoginController INSTANCE;
    private ViewController _controller;
    private I_LoginState _state;

    /**
     *
     */
    private LoginController() {
        _controller = ViewController.getInstance();
        _state = new LoggedOutState();
    }

    /**
     * @return
     */
    public static LoginController getInstance() {
        if(INSTANCE == null) INSTANCE = new LoginController();

        return INSTANCE;
    }


    /**
     * Shows the initial view of the controller.
     * @return the initial form of the controller.
     */
    @Override
    public I_Form index() {
        return new LoginForm(this);
    }

    /**
     * Show the user a registration page.
     */
    public void register(){
        _controller.show(new RegisterForm(_controller, this, RequestRepositoryController.getInstance()));
    }

    /**
     * Take the user back a form.
     */
    public void back(){
        _controller.undo();
    }

    /**
     * @return the state of the handler.
     */
    public I_LoginState getState() {
        return _state;
    }

    /**
     * @param state changes the state of the LoginHandler.
     */
    public void setState(I_LoginState state) {
        _state = state;
    }

    /**
     * A login attempt.
     */
    public void login(){
        _state.login(this);
    }

    /**
     * A logout attempt.
     */
    public void logout(){
        _state.logout(this);
    }
}
