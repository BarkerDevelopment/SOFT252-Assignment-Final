package controllers.primary.login;

import controllers.primary.I_ViewController;

/**
 * A class that encapsulates logging in. It implements the state pattern to enforce either logged in or logged out.
 */
public class LoginController implements I_ViewController {
    private I_LoginState _state;

    /**
     * Shows the initial view of the controller.
     */
    @Override
    public void index() {

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
