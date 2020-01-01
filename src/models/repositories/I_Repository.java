package models.repositories;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class that stores instances of objects for a centralised access point to them.
 *
 */
public interface I_Repository extends Serializable {
    /**
     * @return the _items variable. Represents the list of all items in the repo.
     */
    public abstract ArrayList< I_RepositoryItem > get();

    /**
     * @param items the new contents of the _items variable.
     */
    public abstract void set(ArrayList< I_RepositoryItem > items);
}
