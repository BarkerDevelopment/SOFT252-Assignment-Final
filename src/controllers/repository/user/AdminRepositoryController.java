package controllers.repository.user;

import controllers.repository.I_RepositoryController;
import controllers.repository.I_UniqueQueryableRepository;
import exceptions.ObjectNotFoundException;
import models.repositories.Repository;
import models.users.Admin;

import java.util.ArrayList;

public class AdminRepositoryController
    implements I_RepositoryController< Admin >, I_UniqueQueryableRepository< String, Admin > {
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
    public ArrayList< Admin > get() {
        ArrayList< Admin > content = new ArrayList<>();
        _repository.get().forEach(i -> content.add( (Admin) i) );

        return content;
    }

    /**
     * @param identifier the unique sting which identifies an object.
     * @return the found object.
     * @throws ObjectNotFoundException if an object cannot be found.
     */
    @Override
    public Admin get(String identifier) throws ObjectNotFoundException {
        for (Admin user : this.get()) if(user.getUnique().equals(identifier)) return user;

        throw new ObjectNotFoundException();
    }

    /**
     * Queries the contnents of the repository to see if it contains an object that can be identified by the passed
     * string.
     *
     * @param identifier the unique sting which identifies an object.
     * @return TRUE if the repository contains an object with the identifier, FALSE otherwise.
     */
    @Override
    public boolean contains(String identifier) {
        try {
            return this.get(identifier).getUnique().equals(identifier);

        } catch (ObjectNotFoundException e) {
            return false;
        }
    }

    /**
     * Adds an item to the repository.
     *
     * @param item the item to be added.
     */
    @Override
    public void add(Admin item) {
        _repository.get().add(item);
    }

    /**
     * Add a collection of items to the repository.
     *
     * @param items the collection of items to be added.
     */
    @Override
    public void add(ArrayList< Admin > items) {
        _repository.get().addAll(items);
    }

    /**
     * Removes an item from the repository.
     *
     * @param item the item to be removed.
     */
    @Override
    public void remove(Admin item) {
        _repository.get().remove(item);
    }

    /**
     * Removes a collection of items from the repository.
     *
     * @param items the collection of items to be removed.
     */
    @Override
    public void remove(ArrayList< Admin > items) {
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