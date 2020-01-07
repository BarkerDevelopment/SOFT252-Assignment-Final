package controllers.serialisation;

import controllers.repository.*;
import controllers.serialisation.strategies.DefaultSerialisationStrategy;
import controllers.serialisation.strategies.I_SerialisationStrategy;
import models.repositories.Repository;

import java.io.IOException;
import java.util.Map.Entry;

/**
 * A class that controls the serialisation of the repositories.
 */
public class RepositorySerialisationController {
    private final static String DESTINATION = "resources/repositories";
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
        if(repositoryController instanceof I_SingleRepositoryController) singleSave(( (I_SingleRepositoryController< ? >) repositoryController ));

        else if(repositoryController instanceof I_EnumRepositoryController< ?, ? >) enumSave(( (I_EnumRepositoryController< ?, ? >) repositoryController ));

        else throw new ClassCastException("RepositoryController not of a supported type.");
    }

    /**
     * Loads the repository of the repository controller.
     *
     * @param repositoryController the target SingleRepositoryController.
     */
    public void load(I_RepositoryController< ? > repositoryController) throws Exception {
        if(repositoryController instanceof I_SingleRepositoryController) singleLoad(( (I_SingleRepositoryController< ? >) repositoryController ));

        else if(repositoryController instanceof I_EnumRepositoryController< ?, ? >) enumLoad(( (I_EnumRepositoryController< I_EnumRepositoryControllerKey, ? >) repositoryController ));

        else throw new ClassCastException("RepositoryController not of a supported type.");
    }

    /**
     * Saves the repository of the repository controller.
     *
     * @param repositoryController the target SingleRepositoryController.
     */
    public void singleSave(I_SingleRepositoryController< ? > repositoryController) throws Exception {
            _serialisationStrategy.serialise(DESTINATION, repositoryController.getFileName(), repositoryController.getRepository());
    }

    /**
     * Saves the repository of the repository controller.
     *
     * @param repositoryController the target EnumRepositoryController.
     */
    public void enumSave(I_EnumRepositoryController< ?, ? > repositoryController) throws Exception {
        for (Entry< ? , Repository > entry : repositoryController.getRepositories())
            _serialisationStrategy.serialise(DESTINATION, ((I_EnumRepositoryControllerKey) entry.getKey()).getFileName(), entry.getValue());
    }

    /**
     * Loads the repository of the repository controller.
     *
     * @param repositoryController the target SingleRepositoryController.
     */
    public void singleLoad(I_SingleRepositoryController< ? > repositoryController) throws Exception {
        try{
            repositoryController.setRepository( ( Repository ) _serialisationStrategy.deserialise( DESTINATION, repositoryController.getFileName() ));

        }catch (IOException e){
            repositoryController.setRepository(new Repository());
        }
    }

    /**
     * Loads the repository of the repository controller.
     *
     * @param repositoryController the target EnumRepositoryController.
     */
    public void enumLoad(I_EnumRepositoryController< I_EnumRepositoryControllerKey, ? > repositoryController) throws Exception {
        for (Entry< ?, Repository > entry : repositoryController.getRepositories()) {
            I_EnumRepositoryControllerKey key = ( (I_EnumRepositoryControllerKey) entry.getKey() );

            try {
                repositoryController.setRepository(key, (Repository) _serialisationStrategy.deserialise(DESTINATION, key.getFileName()));

            }catch (IOException e){
                repositoryController.setRepository(key, new Repository());
            }
        }
    }

    /**
     * Loads all the repositories with their data.
     */
    public static void loadAll(){
        AppointmentRepositoryController.getInstance().load();
        DrugRepositoryController.getInstance().load();
        RequestRepositoryController.getInstance().load();
        UserRepositoryController.getInstance().load();
    }

    /**
     * Save all the repositories.
     */
    public static void saveAll(){
        AppointmentRepositoryController.getInstance().save();
        DrugRepositoryController.getInstance().save();
        RequestRepositoryController.getInstance().save();
        UserRepositoryController.getInstance().save();
    }
}
