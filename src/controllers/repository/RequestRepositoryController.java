package controllers.repository;

import exceptions.DuplicateObjectException;
import exceptions.ObjectNotFoundException;
import models.repositories.I_RepositoryItem;
import models.repositories.Repository;
import models.requests.Request;
import models.requests.RequestType;
import models.users.User;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map.Entry;
import java.util.Set;

/**
 * A class that controls the interactions with the Request repositories.
 */
public class RequestRepositoryController
        implements I_EnumRepositoryController< RequestType, Request > {

    private static RequestRepositoryController INSTANCE;
    private EnumMap< RequestType, Repository > _repositories;

    /**
     * Singleton constructor.
     */
    private RequestRepositoryController() {
        _repositories = new EnumMap< >(RequestType.class);

        for(RequestType type : RequestType.values()) _repositories.put(type, new Repository());
    }

    /**
     * RequestRepositoryController implements the Singleton pattern.
     *
     * @return the Singleton RequestRepositoryController.
     */
    public static RequestRepositoryController getInstance(){
        if(INSTANCE == null) INSTANCE = new RequestRepositoryController();

        return INSTANCE;
    }

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

    /**
     * @param type the type of request.
     * @return an ArrayList of all the requests of the specified type.
     */
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
    public void add(Request item) throws DuplicateObjectException{
        ArrayList< I_RepositoryItem > requests = _repositories.get( item.getType() ).get();

        if(! requests.contains(item)){
            requests.add(item);

        }else{
            throw new DuplicateObjectException();
        }
    }

    /**
     * Add a collection of items to the repository.
     *
     * @param items the collection of items to be added.
     */
    @Override
    public void add(ArrayList< Request > items) {
        for (Request request : items){
            this.add(request);
        }
    }

    /**
     * Removes an item from the repository.
     *
     * @param item the item to be removed.
     */
    @Override
    public void remove(Request item) throws ObjectNotFoundException {
        ArrayList< I_RepositoryItem > requests = _repositories.get( item.getType() ).get();

        if(requests.contains(item)){
            requests.remove(item);

        }else{
            throw new ObjectNotFoundException();
        }
    }

    /**
     * Removes a collection of items from the repository.
     *
     * @param items the collection of items to be removed.
     */
    @Override
    public void remove(ArrayList< Request > items) throws ObjectNotFoundException {
        for (Request request : items){
            this.remove(request);
        }
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

    /**
     * Approve a request, then deletes it.
     *
     * @param request the target request to approve.
     * @throws ObjectNotFoundException the request cannot be found in the repository.
     * @throws Exception if the request's approveAction throws an Exception.
     */
    public void approve(Request request) throws Exception {
        if(this.get(request.getType()).contains(request)){
            request.approveAction();
            this.remove(request);

        }else{
            throw new ObjectNotFoundException();
        }
    }

    /**
     * Deny a request, then deletes it.
     *
     * @param request the target request to deny.
     * @throws ObjectNotFoundException if the request cannot be found in the repository.
     * @throws Exception if the request's approveAction throws an Exception.
     */
    public void deny(Request request) throws Exception {
        if(this.get(request.getType()).contains(request)){
            request.denyAction();
            this.remove(request);

        }else{
            throw new ObjectNotFoundException();
        }
    }
}
