package controllers.repository;

import models.repositories.I_RepositoryItem;
import models.repositories.Repository;

import java.util.Map.Entry;
import java.util.Set;

/**
 * Defines a RepositoryController that contains multiple types of controller.
 *
 * @param <T> the class of the Enummap key.
 * @param <R> the super class of object stored in the repositories.
 */
public interface I_EnumRepositoryController< T extends I_EnumRepositoryControllerKey, R extends I_RepositoryItem >
        extends I_RepositoryController< R >{

    /**
     * @return all repositories stored by the EnumRepositoryController.
     */
    public abstract Set< Entry< T, Repository> > getRepositories();

    /**
     * @param type the type of repository to get.
     * @return the content of the _repository variable. Represents the repository that holds the data.
     */
    public abstract Repository getRepository(T type);

    /**
     * @param type the type of repository to replace.
     * @param repository the new repository to replace the repository in the _repository variable.
     */
    public abstract void setRepository(T type, Repository repository);
}
