package controllers.primary;

/**
 * Annotates a controller as being a view controller; one that shows views to the user.
 */
public interface I_UserController extends I_UserStoryIndexController {


    /**
     * Take the user back a form.
     */
    public abstract void back();

    /**
     * Log the user out and return to the login screen.
     */
    public abstract void logout();
}
