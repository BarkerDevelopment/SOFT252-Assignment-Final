package models.repositories;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class that stores instances of objects for a centralised access point to them.
 *
 * @param <T> the type of object to be stored.
 */
public interface I_Repository<T extends I_RepositoryItem>
        extends Serializable {
    /**
     * @return the list of all items in the repo.
     */
    public abstract ArrayList< T > get();

    /**
     * @param items
     */
    public abstract void set(ArrayList< T > items);
}
