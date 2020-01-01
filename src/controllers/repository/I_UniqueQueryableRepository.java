package controllers.repository;

import exceptions.ObjectNotFoundException;
import models.I_Unique;
import models.repositories.I_RepositoryItem;

/**
 * Defines the functions of a repository controller which can be queried by a string. These functions only work if the
 * queried objects have uniquely identifiable strings.
 *
 * @param <T> the type of the queryable unique the object the repository stores has.
 * @param <R> the type of object the repository stores.
 */
public interface I_UniqueQueryableRepository< T, R extends I_RepositoryItem & I_Unique< T > > {
    /**
     *
     * @param identifier the unique sting which identifies an object.
     * @return the found object.
     *
     * @throws ObjectNotFoundException if an object cannot be found.
     */
    public abstract R get(T identifier) throws ObjectNotFoundException;

    /**
     * Queries the contnents of the repository to see if it contains an object that can be identified by the passed string.
     *
     * @param identifier the unique sting which identifies an object.
     * @return TRUE if the repository contains an object with the identifier, FALSE otherwise.
     */
    public abstract boolean contains(T identifier);
}
