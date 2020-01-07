package controllers.repository;

import controllers.serialisation.RepositorySerialisationController;
import exceptions.DuplicateObjectException;
import exceptions.ObjectNotFoundException;
import models.repositories.I_RepositoryItem;
import models.repositories.Repository;
import models.users.User;
import models.users.info.ID;
import models.users.info.UserRole;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A class that controls the interactions with the User repositories.
 */
public class UserRepositoryController
    implements I_EnumRepositoryController< UserRole, User >, I_UniqueQueryableRepository< String, User > {

    private static UserRepositoryController INSTANCE;

    private EnumMap< UserRole, Repository> _repositories;
    private RepositorySerialisationController _serialisationController;

    /**
     * Singleton constructor.
     */
    private UserRepositoryController() {
        _repositories = new EnumMap< >(UserRole.class);

        for(UserRole role : UserRole.values()) _repositories.put(role, new Repository());
        _serialisationController = new RepositorySerialisationController();
    }

    /**
     * UserRepositoryController implements the Singleton pattern.
     *
     * @return the Singleton RequestRepositoryController.
     */
    public static UserRepositoryController getInstance(){
        if(INSTANCE == null) INSTANCE = new UserRepositoryController();

        return INSTANCE;
    }

    /**
     * @return all repositories stored by the EnumRepositoryController.
     */
    @Override
    public Set< Entry< UserRole, Repository > > getRepositories() {
        return _repositories.entrySet();
    }

    /**
     * @param type the type of repository to get.
     * @return the content of the _repository variable. Represents the repository that holds the data.
     */
    @Override
    public Repository getRepository(UserRole type) {
        return _repositories.get(type);
    }

    /**
     * @param type the type of repository to return.
     * @param repository the new repository to replace the repository in the _repository variable.
     */
    @Override
    public void setRepository(UserRole type, Repository repository) {
        _repositories.put(type, repository);
    }

    /**
     * @return the contents of the repository cast to the correct type.
     */
    @Override
    public ArrayList< User > get() {
        ArrayList< User > content = new ArrayList<>();

        /*
         * I would have like to avoided using a quadratic loop, however due to the casting issues I've been facing
         * it was near impossible. To avoid it, I would have used the ArrayList.addAll() function but alas, you can't
         * do it if there are conflicting types.
         *
         * While I'm using a quadratic loop, it only has a time complexity of O(n) as it only cycles through the list once.
         */
        _repositories.forEach(
                (role, repository) -> repository.get().forEach(
                        user -> content.add( (User) user)
                )
        );

        return content;
    }

    /**
     * @param identifier the unique sting which identifies an object.
     * @return the found object.
     * @throws ObjectNotFoundException if an object cannot be found.
     */
    @Override
    public User get(String identifier) throws ObjectNotFoundException {
        for (User user : this.get()) if(user.getUnique().equals(identifier)) return user;

        throw new ObjectNotFoundException();
    }

    /**
     * @return all the IDs of the users in the repository controller.
     */
    public ArrayList< ID > getIDs(){
        return new ArrayList<>(
                this.get().stream().map(User::getId).collect(Collectors.toList())
        );
    }

    /**
     * @param role the type of role of the IDs.
     * @return all the IDs of the users of the passed ID in the repository controller.
     */
    public ArrayList< ID > getIDs(UserRole role){
        return new ArrayList<>(
                this.getRepository(role).get().stream().map(item -> ((User) item).getId()).collect(Collectors.toList())
        );
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
     * @throws DuplicateObjectException
     */
    @Override
    public void add(User item) throws DuplicateObjectException {
        ArrayList< I_RepositoryItem > users = _repositories.get( item.getId().getRole() ).get();

        if(! users.contains(item)){
            users.add(item);
            save();

        }else{
            throw new DuplicateObjectException();
        }
    }

    /**
     * Add a collection of items to the repository.
     *
     * @param items the collection of items to be added.
     * @throws DuplicateObjectException
     */
    @Override
    public void add(ArrayList< User > items) throws DuplicateObjectException {
        for (User user : items){
            this.add(user);
        }
    }

    /**
     * Removes an item from the repository.
     *
     * @param item the item to be removed.
     * @throws ObjectNotFoundException
     */
    @Override
    public void remove(User item) throws ObjectNotFoundException {
        ArrayList< I_RepositoryItem > users = _repositories.get( item.getId().getRole() ).get();

        if(users.contains(item)){
            users.remove(item);
            save();

        }else{
            throw new ObjectNotFoundException();
        }
    }

    /**
     * Removes a collection of items from the repository.
     *
     * @param items the collection of items to be removed.
     * @throws ObjectNotFoundException
     */
    @Override
    public void remove(ArrayList< User > items) throws ObjectNotFoundException {
        for (User user : items){
            this.remove(user);
        }
    }

    /**
     * Clears the repository.
     */
    @Override
    public void clear() {
        _repositories.forEach(
                (role, repository) -> repository.get().clear()
        );
        save();
    }

    /**
     * Loads the content repository.
     *
     * @return the repository controller.
     */
    @Override
    public I_RepositoryController< User > load() {
        try {
            _serialisationController.load(this);

        }catch (Exception e){
            e.printStackTrace();
        }

        return this;
    }

    /**
     * Saves the contents of the repository.
     */
    @Override
    public void save() {
        try {
            _serialisationController.save(this);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
