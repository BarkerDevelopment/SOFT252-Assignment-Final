package controllers.serialisation;

import controllers.repository.I_EnumRepositoryController;
import controllers.repository.I_EnumRepositoryControllerKey;
import controllers.repository.I_RepositoryController;
import controllers.repository.I_SingleRepositoryController;
import controllers.serialisation.strategies.DefaultSerialisationStrategy;
import controllers.serialisation.strategies.I_SerialisationStrategy;
import models.repositories.Repository;

import java.util.Map.Entry;

/**
 * A class that controls the serialisation of the repositories.
 */
public class RepositorySerialisationController {
    private final I_SerialisationStrategy _serialisationStrategy;

    /**
     * Default constructor. Creates a controller with the default strategies.
     *
     */
    public RepositorySerialisationController() {
        _serialisationStrategy = new DefaultSerialisationStrategy();
    }

    /**
     * Alternative constructor. Allows for the definition of a serialisation strategy.
     *
     * @param serialisationStrategy the serialisation strategy.
     */
    public RepositorySerialisationController(I_SerialisationStrategy serialisationStrategy) {
        _serialisationStrategy = serialisationStrategy;
    }

    /**
     * @return the _serialisationStrategy variable. Represents the strategy that will be used to serialise the objects.
     */
    public I_SerialisationStrategy getSerialisationStrategy() {
        return _serialisationStrategy;
    }

    /**
     * Saves the repository of the repository controller.
     *
     * @param repositoryController the target SingleRepositoryController.
     */
    public void save(I_RepositoryController< ? > repositoryController) throws Exception {
        if(repositoryController instanceof I_SingleRepositoryController) save(( (I_SingleRepositoryController< ? >) repositoryController ));

        else if(repositoryController instanceof I_EnumRepositoryController< ?, ? >) save(( (I_EnumRepositoryController< ?, ? >) repositoryController ));

        else throw new ClassCastException("RepositoryController not of a supported type.");


    }

    /**
     * Loads the repository of the repository controller.
     *
     * @param repositoryController the target SingleRepositoryController.
     */
    public void load(I_RepositoryController< ? > repositoryController) throws Exception {
        if(repositoryController instanceof I_SingleRepositoryController) load(( (I_SingleRepositoryController< ? >) repositoryController ));

        else if(repositoryController instanceof I_EnumRepositoryController< ?, ? >) load(( (I_EnumRepositoryController< ?, ? >) repositoryController ));

        else throw new ClassCastException("RepositoryController not of a supported type.");
    }

    /**
     * Saves the repository of the repository controller.
     *
     * @param repositoryController the target SingleRepositoryController.
     */
    public void save(I_SingleRepositoryController< ? > repositoryController) throws Exception {
            _serialisationStrategy.serialise(repositoryController.getFileName(), repositoryController.getRepository());
    }

    /**
     * Saves the repository of the repository controller.
     *
     * @param repositoryController the target EnumRepositoryController.
     */
    public void save(I_EnumRepositoryController< ?, ? > repositoryController) throws Exception {
        for (Entry< ? , Repository > entry : repositoryController.getRepositories())
            _serialisationStrategy.serialise(((I_EnumRepositoryControllerKey) entry.getKey()).getFileName(), entry.getValue());
    }

    /**
     * Loads the repository of the repository controller.
     *
     * @param repositoryController the target SingleRepositoryController.
     */
    public void load(I_SingleRepositoryController< ? > repositoryController) throws Exception {
        repositoryController.setRepository( ( Repository ) _serialisationStrategy.deserialise( repositoryController.getFileName() ));
    }

    /**
     * Loads the repository of the repository controller.
     *
     * @param repositoryController the target EnumRepositoryController.
     */
    public void load(I_EnumRepositoryController< I_EnumRepositoryControllerKey, ? > repositoryController) throws Exception {
        for (Entry< ?, Repository > entry : repositoryController.getRepositories()) {
            I_EnumRepositoryControllerKey key = ( (I_EnumRepositoryControllerKey) entry.getKey() );
            repositoryController.setRepository(key, (Repository) _serialisationStrategy.deserialise(key.getFileName()));
        }
    }
}
