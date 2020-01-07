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
    public abstract void remove(T item) throws Exception;

    /**
     * Removes a collection of items from the repository.
     *
     * @param items the collection of items to be removed.
     */
    public abstract void remove(ArrayList< T > items) throws Exception;

    /**
     * Clears the repository.
     */
    public abstract void clear();

    /**
     * Loads the content repository.
     *
     * @return the repository controller.
     */
    public abstract I_RepositoryController< T > load();

    /**
     * Saves the contents of the repository.
     */
    public abstract void save();
}
