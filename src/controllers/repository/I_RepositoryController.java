package controllers.repository;

import models.repositories.I_RepositoryItem;

import java.util.ArrayList;

public interface I_RepositoryController< T extends I_RepositoryItem > {
    /**
     * @return the contents of the repository cast to the correct type.
     */
    public abstract ArrayList< T > get();

    /**
     * Adds an item to the repository.
     *
     * @param item the item to be added.
     */
    public abstract void add(T item) throws Exception;

    /**
     * Add a collection of items to the repository.
     *
     * @param items the collection of items to be added.
     */
    public abstract void add(ArrayList< T > items) throws Exception;

    /**
     * Removes an item from the repository.
     *
     * @param item the item to be removed.
     */
    public abstract void remove(T item);

    /**
     * Removes a collection of items from the repository.
     *
     * @param items the collection of items to be removed.
     */
    public abstract void remove(ArrayList< T > items);

    /**
     * Clears the repository.
     */
    public abstract void clear();
}
