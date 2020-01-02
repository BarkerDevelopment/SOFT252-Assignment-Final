package controllers.repository.user;

import controllers.repository.I_SingleRepositoryController;
import controllers.repository.I_UniqueQueryableRepository;
import exceptions.ObjectNotFoundException;
import models.repositories.Repository;
import models.users.User;

import java.util.ArrayList;

/**
 * An abstract superclass that controls the interactions with a User repository.
 * @param <T> the type of User.
 */
public abstract class GenericUserRepositoryController< T extends User >
    implements I_SingleRepositoryController< T >, I_UniqueQueryableRepository< String, T > {
    protected Repository _repository;

    /**
     * @return the _repository variable. Represents the repository the object controls.
     */
    @Override
    public Repository getRepository() {
        return _repository;
    }

    /**
     * @param repository the new contents of the _repository variable.
     */
    @Override
    public void setRepository(Repository repository) {
        _repository = repository;
    }

    /**
     * @return the contents of the repository cast to the correct type.
     */
    @Override
    public ArrayList< T > get() {
        ArrayList< T > content = new ArrayList<>();
        _repository.get().forEach(i -> content.add( (T) i) );

        return content;
    }

    /**
     * @param identifier the unique sting which identifies an object.
     * @return the found object.
     * @throws ObjectNotFoundException if an object cannot be found.
     */
    @Override
    public T get(String identifier) throws ObjectNotFoundException {
        for (T user : this.get()) if(user.getUnique().equals(identifier)) return user;

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
    public void add(T item) {
        _repository.get().add(item);
    }

    /**
     * Add a collection of items to the repository.
     *
     * @param items the collection of items to be added.
     */
    @Override
    public void add(ArrayList< T > items) {
        _repository.get().addAll(items);
    }

    /**
     * Removes an item from the repository.
     *
     * @param item the item to be removed.
     */
    @Override
    public void remove(T item) {
        _repository.get().remove(item);
    }

    /**
     * Removes a collection of items from the repository.
     *
     * @param items the collection of items to be removed.
     */
    @Override
    public void remove(ArrayList< T > items) {
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
