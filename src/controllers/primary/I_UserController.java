package controllers.primary;

import views.I_Form;

/**
 * Annotates a controller as being a view controller; one that shows views to the user.
 */
public interface I_UserController {
    /**
     * Shows the initial view of the controller.
     * @return the initial form of the controller.
     */
    public abstract I_Form index();

    /**
     * Take the user back a form.
     */
    public abstract void back();

    /**
     * Log the user out and return to the login screen.
     */
    public abstract void logout();
}
