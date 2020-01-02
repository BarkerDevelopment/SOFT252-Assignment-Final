package models;

/**
 * Annotates an object as implementing the Observer pattern.
 *
 * @param <T> the type of object that the observers will be update with.
 */
public interface I_Observable< T > {
    /**
     * Subscribes an observer object to the observable object.
     *
     * @param o the observer to subscribe.
     */
    public abstract void subscribe(I_Observer< T > o);

    /**
     * Unsubscribes an observer object to the observable object.
     *
     * @param o the observer to unsubscribe.
     */
    public abstract void unsubscribe(I_Observer< T > o);

    /**
     * Updates the subscribed observers with the passed variable.
     *
     * @param item the object to update the observers with,
     */
    public abstract void updateObservers(T item);
}
