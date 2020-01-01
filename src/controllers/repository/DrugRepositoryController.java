package controllers.repository;

import exceptions.DuplicateDrugException;
import exceptions.ObjectNotFoundException;
import models.appointments.Appointment;
import models.drugs.DrugStock;
import models.repositories.Repository;

import java.util.ArrayList;

public class DrugRepositoryController
        implements I_RepositoryController< DrugStock >, I_UniqueQueryableRepository< String, DrugStock > {
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
     * @param identifier the unique sting which identifies an object.
     * @return the found object.
     * @throws ObjectNotFoundException if an object cannot be found.
     */
    @Override
    public DrugStock get(String identifier) throws ObjectNotFoundException {
        for (DrugStock item : this.get()) if(item.getUnique().equals(identifier)) return item;

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
    public void add(DrugStock item) throws DuplicateDrugException {
        if(this.contains(item.getUnique())){
            DrugStock drugStock = null;

            try{
                drugStock = this.get(item.getUnique());

            }catch (ObjectNotFoundException e) {
                // This will never be called because the if statement ensures that the object is not in the repository.
                e.printStackTrace();
            }

            if(drugStock != null){ // Which it will be, this is just so the compiler shuts up.
                if(!drugStock.getDrug().getDescription().equals(item.getDrug().getDescription())){
                    _repository.get().add(item);
                }
            }

            /*
             * If the repository contains a Drug that shares both a name and description as a Drug already in the
             * repository it will throw an exception.
             */
            throw new DuplicateDrugException(String.format("%s already exists in the system.", item.getDrug().getName()));

        }else{
            _repository.get().add(item);
        }

    }

    /**
     * Add a collection of items to the repository.
     *
     * @param items the collection of items to be added.
     */
    @Override
    public void add(ArrayList< DrugStock > items) throws DuplicateDrugException {
        for (DrugStock item : items) {
            this.add(item);
        }
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
