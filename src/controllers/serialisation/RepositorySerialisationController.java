package controllers.serialisation;

import controllers.repository.I_EnumRepositoryController;
import controllers.repository.I_EnumRepositoryControllerKey;
import controllers.repository.I_SingleRepositoryController;
import controllers.serialisation.strategies.DefaultDeserialisationStrategy;
import controllers.serialisation.strategies.DefaultSerialisationStrategy;
import controllers.serialisation.strategies.I_DeserialisationStrategy;
import controllers.serialisation.strategies.I_SerialisationStrategy;
import models.repositories.Repository;

/**
 * A class that controls the serialisation of the repositories.
 */
public class RepositorySerialisationController {
    private final I_SerialisationStrategy _serialisationStrategy;
    private final I_DeserialisationStrategy _deserialisationStrategy;

    /**
     * Default constructor. Creates a controller with the default strategies.
     *
     */
    public RepositorySerialisationController() {
        _serialisationStrategy = new DefaultSerialisationStrategy();
        _deserialisationStrategy = new DefaultDeserialisationStrategy();
    }

    /**
     * Alternative constructor. Allows for the definition of a serialisation strategy.
     *
     * @param serialisationStrategy the serialisation strategy.
     * @param deserialisationStrategy the deserialisation strategy.
     */
    public RepositorySerialisationController(I_SerialisationStrategy serialisationStrategy, I_DeserialisationStrategy deserialisationStrategy) {
        _serialisationStrategy = serialisationStrategy;
        _deserialisationStrategy = deserialisationStrategy;
    }

    /**
     * @return the _serialisationStrategy variable. Represents the strategy that will be used to serialise the objects.
     */
    public I_SerialisationStrategy getSerialisationStrategy() {
        return _serialisationStrategy;
    }

    /**
     * @return the _deserialisationStrategy variable. Represents the strategy that will be used to deserialise the objects.
     */
    public I_DeserialisationStrategy getDeserialisationStrategy() {
        return _deserialisationStrategy;
    }

    /**
     * Saves the repository of the repository controller.
     *
     * @param repositoryController the target SingleRepositoryController.
     */
    public void save(I_SingleRepositoryController< ? > repositoryController){
        _serialisationStrategy.serialise(repositoryController.getFileName(), repositoryController.getRepository());
    }

    /**
     * Saves the repository of the repository controller.
     *
     * @param repositoryController the target EnumRepositoryController.
     */
    public void save(I_EnumRepositoryController< ?, ? > repositoryController){
        repositoryController.getRepositories().forEach(
                entry -> _serialisationStrategy.serialise(entry.getKey().getFileName(), entry.getValue())
        );
    }

    /**
     * Loads the repository of the repository controller.
     *
     * @param repositoryController the target SingleRepositoryController.
     */
    public void load(I_SingleRepositoryController< ? > repositoryController){
        repositoryController.setRepository( ( Repository ) _deserialisationStrategy.deserialise( repositoryController.getFileName() ));
    }

    /**
     * Loads the repository of the repository controller.
     *
     * @param repositoryController the target EnumRepositoryController.
     */
    public void load(I_EnumRepositoryController< I_EnumRepositoryControllerKey, ? > repositoryController){
        repositoryController.getRepositories().forEach(
                entry -> {
                    I_EnumRepositoryControllerKey key = entry.getKey();
                    repositoryController.setRepository(key, ( Repository ) _deserialisationStrategy.deserialise(key.getFileName()));
            }
        );
    }
}
