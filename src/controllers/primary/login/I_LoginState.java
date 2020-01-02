package controllers.primary.login;

import exceptions.LoginException;

/**
 * A state for the LoginHandler.
 */
public interface I_LoginState {
    /**
     * An attempt to login.
     *
     * @param controller the LoginHandler.
     * @throws LoginException if an error takes place while logging in.
     */
    public abstract void login(LoginController controller) throws LoginException;

    /**
     * An attempt to logout.
     *
     * @param controller the LoginHandler.
     * @throws LoginException if an error takes place while logging out.
     */
    public abstract void logout(LoginController controller) throws LoginException;
}
