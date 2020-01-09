package project.models.repositories;

import java.util.ArrayList;

/**
 * A collection of objects.
 */
public class Repository
        implements I_Repository {

    protected ArrayList< I_RepositoryItem > _items;

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
    public Repository(ArrayList< I_RepositoryItem > items) {
        _items = items;
    }

    /**
     * @return the _items variable. Represents the list of all items in the repo.
     */
    @Override
    public ArrayList< I_RepositoryItem > get() {
        return _items;
    }

    /**
     * @param items the new contents of the _items variable.
     */
    @Override
    public void set(ArrayList< I_RepositoryItem > items) {
        _items = items;
    }
}
