package controllers.repository;

import exceptions.DuplicateObjectException;
import exceptions.ObjectNotFoundException;
import exceptions.StockLevelException;
import models.drugs.DrugStock;
import models.drugs.I_Treatment;
import models.repositories.I_RepositoryItem;
import models.repositories.Repository;

import java.util.ArrayList;

/**
 * A class that controls the interactions with the Drug repository.
 */
public class DrugRepositoryController
        implements I_SingleRepositoryController< DrugStock >, I_UniqueQueryableRepository<I_Treatment, DrugStock> {
    private static DrugRepositoryController INSTANCE;

    private final String _fileName;
    private Repository _repository;

    /**
     * Default constructor. Creates an object without a repository.
     */
    private DrugRepositoryController() {
        _fileName = "drugs";
        _repository = new Repository();
    }

    /**
     * RequestRepositoryController implements the Singleton pattern.
     *
     * @return the Singleton RequestRepositoryController.
     */
    public static DrugRepositoryController getInstance(){
        if(INSTANCE == null) INSTANCE = new DrugRepositoryController();

        return INSTANCE;
    }

    /**
     * @return the _fileName variable. Represents the file that stores the repository contents.
     */
    @Override
    public String getFileName() {
        return _fileName;
    }

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
    public DrugStock get(I_Treatment identifier) throws ObjectNotFoundException {
        for (I_RepositoryItem item : _repository.get()){
            DrugStock drugStock = (DrugStock) item;
            if( drugStock.getDrug().equals(identifier)) return drugStock;
        }

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
    public boolean contains(I_Treatment identifier) {
        try {
            return this.get(identifier) != null;

        }catch (ObjectNotFoundException e) {
            return false;
        }
    }

    /**
     * Adds an item to the repository.
     *
     * @param item the item to be added.
     */
    @Override
    public void add(DrugStock item) throws DuplicateObjectException {
        if(this.contains(item.getDrug()))
                throw new DuplicateObjectException(String.format("%s already exists in the system.", item.getDrug().getName()));

        else{
            for (I_RepositoryItem repositoryItem : _repository.get()){
                DrugStock drugStock = (DrugStock) repositoryItem;
                if( drugStock.getDrug().getName().equals(item.getDrug().getName())){
                    if ( drugStock.getDrug().getDescription().equals(item.getDrug().getDescription())) {
                        throw new DuplicateObjectException(String.format("%s already exists in the system.", item.getDrug().getName()));
                    }
                }
            }

            _repository.get().add(item);
        }
    }

    /**
     * Add a collection of items to the repository.
     *
     * @param items the collection of items to be added.
     */
    @Override
    public void add(ArrayList< DrugStock > items) throws DuplicateObjectException {
        for (DrugStock item : items) {
            this.add(item);
        }
    }

    /**
     * Updates a DrugStock objects stock by the passed value.
     *
     * @param drug the target drug object.
     * @param stockChange the proposed change in stock.
     */
    public void updateStock(I_Treatment drug, int stockChange) throws StockLevelException, ObjectNotFoundException {
        DrugStock drugStock = this.get(drug);
        int newStock = drugStock.getStock() + stockChange;

        if(newStock >= 0){
            drugStock.setStock(newStock);

        }else{
            throw new StockLevelException(
                    String.format("%s stock change of %d results in a negative stock.", drugStock.getDrug().getName(), stockChange)
            );
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
