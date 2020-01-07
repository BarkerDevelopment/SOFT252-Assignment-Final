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
}
