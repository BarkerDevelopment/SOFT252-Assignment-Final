package controllers.repository;

import models.repositories.Repository;
import models.requests.Request;
import models.requests.RequestType;

import java.util.ArrayList;
import java.util.EnumMap;

public class RequestRepositoryController
        implements I_RepositoryController< Request > {

    private EnumMap< RequestType, Repository > _repositories;

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
