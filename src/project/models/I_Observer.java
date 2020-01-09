package project.models;

/**
 * Annotates an object as implementing the Observer pattern.
 *
 * @param <T> the type of object that the Observable will update the Observer with.
 */
public interface I_Observer< T > {
    /**
     * Updates a variable within the Observer.
     *
     * @param item the variable to update.
     */
    public abstract void update(T item);
}
