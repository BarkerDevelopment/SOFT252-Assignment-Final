package project.controllers.primary;

import project.models.users.User;

/**
 * Annotates a controller as being a view controller; one that shows project.views to the user.
 */
public interface I_UserController extends I_UserStoryIndexController {
    /**
     * @return the user.
     */
    public abstract User getUser();

    /**
     * Take the user back a form.
     */
    public abstract void back();

    /**
     * Log the user out and return to the login screen.
     */
    public abstract void logout();
}
