package controllers.repository;

import models.drugs.DrugStock;
import models.repositories.Repository;

import java.util.ArrayList;

public class DrugRepositoryController
        implements I_RepositoryController< DrugStock > {
    private Repository _repository;

    /**
     * @return the _repository variable. Represents the repository the object controls.
     */
    public Repository getRepository() {
        return _repository;
    }

    /**
     * @param repository the new contents of the _repository variable.
     */
    public void setRepository(Repository repository) {
        _repository = repository;
    }

    /**
     * @return the contents of the repository cast to the correct type.
     */
    @Override
    public ArrayList< DrugStock > get() {
        ArrayList< DrugStock > content = new ArrayList<>();
        _repository.get().forEach(i -> content.add( (DrugStock) i) );

        return content;
    }

    /**
     * Adds an item to the repository.
     *
     * @param item the item to be added.
     */
    @Override
    public void add(DrugStock item) {
        _repository.get().add(item);
    }

    /**
     * Add a collection of items to the repository.
     *
     * @param items the collection of items to be added.
     */
    @Override
    public void add(ArrayList< DrugStock > items) {
        _repository.get().addAll(items);
    }

    /**
     * Removes an item from the repository.
     *
     * @param item the item to be removed.
     */
    @Override
    public void remove(DrugStock item) {
        _repository.get().remove(item);
    }

    /**
     * Removes a collection of items from the repository.
     *
     * @param items the collection of items to be removed.
     */
    @Override
    public void remove(ArrayList< DrugStock > items) {
        _repository.get().removeAll(items);
    }

    /**
     * Clears the repository.
     */
    @Override
    public void clear() {
        _repository.get().clear();
    }
}