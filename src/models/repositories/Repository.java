package models.repositories;

import java.util.ArrayList;

/**
 * A collection of objects.
 *
 * @param <T> the type of object to store in the repository.
 */
public class Repository< T extends I_RepositoryItem >
        implements I_Repository< T > {

    protected ArrayList< T > _items;

    /**
     * Creates a repository with an empty _items list.
     */
    public Repository() {
        _items = new ArrayList<>();
    }

    /**
     * Creates a repository with a instantiated _items list.
     * @param items the list of items to create the repository with.
     */
    public Repository(ArrayList< T > items) {
        _items = items;
    }

    /**
     * @return the list of all items in the repo.
     */
    @Override
    public ArrayList< T > get() {
        return _items;
    }

    /**
     * @param items the new items.
     */
    @Override
    public void set(ArrayList< T > items) {
        _items = items;
    }
}
