package project.models;

import project.exceptions.ObjectNotFoundException;

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
    public abstract void subscribe(I_Observer< T > o) throws ObjectNotFoundException;

    /**
     * Unsubscribes an observer object to the observable object.
     *
     * @param o the observer to unsubscribe.
     */
    public abstract void unsubscribe(I_Observer< T > o) throws ObjectNotFoundException;

    /**
     * Updates the subscribed observers with the object.
     */
    public abstract void updateObservers();
}
