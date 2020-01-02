package controllers.repository;

import models.repositories.Repository;
import models.requests.Request;
import models.requests.RequestType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map.Entry;
import java.util.Set;

/**
 * A class that controls the interactions with the Request repositories.
 */
public class RequestRepositoryController
        implements I_EnumRepositoryController< RequestType, Request > {

    private EnumMap< RequestType, Repository > _repositories;

    /**
     * @return all repositories stored by the EnumRepositoryController.
     */
    @Override
    public Set< Entry< RequestType, Repository> > getRepositories() {

        return _repositories.entrySet();
    }

    /**
     * @param type the type of repository to get.
     * @return the content of the _repository variable. Represents the repository that holds the data.
     */
    @Override
    public Repository getRepository(RequestType type) {
        return _repositories.get(type);
    }

    /**
     * @param type the type of repository to return.
     * @param repository the new repository to replace the repository in the _repository variable.
     */
    @Override
    public void setRepository(RequestType type, Repository repository) {
        _repositories.put(type, repository);
    }

    /**
     * @return the contents of the repository cast to the correct type.
     */
    @Override
    public ArrayList< Request > get() {
        ArrayList< Request > content = new ArrayList<>();
        _repositories.forEach(
                (requestType, repository) -> repository.get().forEach(i -> content.add( (Request) i))
        );

        return content;
    }

    public ArrayList< Request > get(RequestType type){
        ArrayList< Request > content = new ArrayList<>();
        _repositories.get(type).get().forEach(i -> content.add( (Request) i) );

        return content;
    }

    /**
     * Adds an item to the repository.
     *
     * @param item the item to be added.
     */
    @Override
    public void add(Request item) {
        _repositories.get(item.getType()).get().add(item);
    }

    /**
     * Add a collection of items to the repository.
     *
     * @param items the collection of items to be added.
     */
    @Override
    public void add(ArrayList< Request > items) {
        items.forEach(this::add);
    }

    /**
     * Removes an item from the repository.
     *
     * @param item the item to be removed.
     */
    @Override
    public void remove(Request item) {
        _repositories.get(item.getType()).get().remove(item);
    }

    /**
     * Removes a collection of items from the repository.
     *
     * @param items the collection of items to be removed.
     */
    @Override
    public void remove(ArrayList< Request > items) {
        items.forEach(this::remove);
    }

    /**
     * Clears the repository.
     */
    @Override
    public void clear() {
        _repositories.forEach(
                (requestType, repository) -> repository.get().clear()
        );
    }
}
