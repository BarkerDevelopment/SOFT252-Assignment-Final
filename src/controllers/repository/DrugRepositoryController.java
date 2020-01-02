package controllers.repository;

import exceptions.DuplicateDrugException;
import exceptions.ObjectNotFoundException;
import exceptions.StockLevelException;
import models.drugs.DrugStock;
import models.repositories.Repository;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * A class that controls the interactions with the Drug repository.
 */
public class DrugRepositoryController
        implements I_SingleRepositoryController< DrugStock > {
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
    public ArrayList< DrugStock > get(String identifier) throws ObjectNotFoundException {
        ArrayList< DrugStock > drugStocks = new ArrayList<>();
        for (DrugStock item : this.get()) if(item.getDrug().getName().equals(identifier)) drugStocks.add(item);

        if(drugStocks.size() > 0){
            return drugStocks;

        }else{
            throw new ObjectNotFoundException();
        }
    }

    /**
     * Queries the contnents of the repository to see if it contains an object that can be identified by the passed
     * string.
     *
     * @param identifier the unique sting which identifies an object.
     * @return TRUE if the repository contains an object with the identifier, FALSE otherwise.
     */
    public boolean contains(String identifier) {
        try {
            return this.get(identifier).size() > 0;

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
        if(this.contains(item.getDrug().getName())){
            ArrayList< DrugStock > drugStocks = null;

            try{
                drugStocks = this.get(item.getDrug().getName());

            }catch (ObjectNotFoundException e) {
                // This will never be called because the if statement ensures that the object is not in the repository.
                e.printStackTrace();
            }

            if(drugStocks != null && drugStocks.size() > 0){ // Which it will be, this is just so the compiler shuts up.

                if(! drugStockMatchesDescription(drugStocks, item.getDrug().getDescription())){
                    _repository.get().add(item);
                }

            }else {
                /*
                 * If the repository contains a Drug that shares both a name and description as a Drug already in the
                 * repository it will throw an exception.
                 */
                throw new DuplicateDrugException(String.format("%s already exists in the system.", item.getDrug().getName()));
            }

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
     * Updates a DrugStock objects stock by the passed value.
     *
     * @param drugStock the target DrugStock object.
     * @param stockChange the proposed change in stock.
     */
    public void updateStock(DrugStock drugStock, int stockChange) throws StockLevelException {
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

    /**
     * Compares the descriptions of all the drugs of the passed ArrayList against the passed string.
     *
     * @param drugStocks the ArrayList of drugStocks.
     * @param description the target description.
     * @return TRUE if a drug out of the passed array matches the description, FALSE if no drugs match the description.
     */
    private boolean drugStockMatchesDescription(ArrayList< DrugStock > drugStocks, String description){
        ArrayList< DrugStock > drugStocksWithDescription = new ArrayList<> (
            drugStocks.stream().filter(
                drugStock -> drugStock.getDrug().getDescription().equals(description)
            ).collect(Collectors.toList()));

        return drugStocksWithDescription.size() == 0;
    }
}
