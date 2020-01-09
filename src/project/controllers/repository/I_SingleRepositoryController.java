package project.controllers.repository;

import project.models.repositories.I_RepositoryItem;
import project.models.repositories.Repository;

/**
 * Defines the functions of an object that controls interactions concerning a repository.
 *
 * @param <T> the type of object the repository stores.
 */
public interface I_SingleRepositoryController< T extends I_RepositoryItem > extends I_RepositoryController< T >{
    /**
     * @return the _fileName variable. Represents the file that stores the repository contents.
     */
    public abstract String getFileName();

    /**
     * @return the content of the _repository variable. Represents the repository that holds the data.
     */
    public abstract Repository getRepository();

    /**
     * @param repository the new repository to replace the repository in the _repository variable.
     */
    public abstract void setRepository(Repository repository);
}
