package controllers.primary;

import views.I_Form;

/**
 * An interface defining a class as being an entry point to the user's story.
 */
public interface I_UserStoryIndexController {
    /**
     * Shows the initial view of the controller.
     * @return the initial form of the controller.
     */
    public abstract I_Form index();
}
