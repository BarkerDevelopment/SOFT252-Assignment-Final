package controllers.primary;

/**
 * Annotates a controller as being a view controller; one that shows views to the user.
 */
public interface I_ViewController {
    /**
     * Shows the initial view of the controller.
     */
    public abstract void index();
}
