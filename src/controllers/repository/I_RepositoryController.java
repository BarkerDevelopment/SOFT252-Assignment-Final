package controllers.repository;

import models.repositories.I_RepositoryItem;
import models.repositories.Repository;

import java.util.ArrayList;

/**
 * Defines the functions of an object that controls interactions concerning a repository.
 *
 * @param <T> the type of object the repository stores.
 */
public interface I_RepositoryController< T extends I_RepositoryItem > {
    /**
     * @return the content of the _repository variable. Represents the repository that holds the data.
     */
    public abstract Repository getRepository();

    /**
     * @param repository the new repository to replace the repository in the _repository variable.
     */
    public abstract void setRepository(Repository repository);

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
