package project.models.users;

import project.controllers.repository.UserRepositoryController;
import project.exceptions.IdClashException;
import project.exceptions.OutOfRangeException;
import project.models.I_Unique;
import project.models.repositories.I_RepositoryItem;
import project.models.users.info.Address;
import project.models.users.info.ID;
import project.models.users.info.IDFactory;
import project.models.users.info.UserRole;
import project.models.messaging.I_Message;
import project.models.messaging.I_MessageRecipient;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Super class for the system users.
 */
public class User
    implements I_MessageRecipient, I_RepositoryItem, I_Unique< String >, Serializable {

    private final ID _id;
    private String _name;
    private String _surname;
    private Address _address;
    private int _password;
    private ArrayList< I_Message > _messages;

    /**
     * Creates a User object.
     *
     * @param role the User's role. This should be predefined as the subclass _role variable.
     * @param name the User's name.
     * @param surname the User's surname.
     * @param address the User's address
     * @param password the User's password.
     */
    public User(UserRole role, String name, String surname, Address address, int password){
        _id = new IDFactory(role).create(UserRepositoryController.getInstance().getIDs(role));
        _name = name;
        _surname = surname;
        _address = address;
        _password = password;
        _messages = new ArrayList<>();
    }

    /**
     * Creates a dummy User object for testing purposes.
     * NOTE: This constructor is used to specifically test the random ID functionality.
     *
     * @param role the User's role. This should be predefined as the subclass _role variable.
     * @param name the User's name.
     * @param surname the User's surname.
     * @param seed the pseudo-random generator seed. This ensures repeatable random generation.
     */
    public User(UserRole role, String name, String surname, long seed){
        _id = new IDFactory(role, seed).create(UserRepositoryController.getInstance().getIDs(role));
        _name = name;
        _surname = surname;
        _address = new Address();
        _password = "password".hashCode();
        _messages = new ArrayList<>();
    }

    /**
     * Creates a bare dummy User object for testing purposes.
     * The name and surname are used to provide another element of individuality to increase ease of testing rather than
     * just looking at IDs.
     *
     * @param role the User's role. This should be predefined as the subclass _role variable.
     * @param idNumber the User's ID number. This will be added to the User role string to create the User ID.
     * @param name the User's name.
     * @param surname the User's surname.
     */
    public User(UserRole role, String idNumber, String name, String surname, Address address, String password) throws OutOfRangeException, IdClashException {
        _id = new ID(role, idNumber);
        _name = name;
        _surname = surname;
        _address = address;
        _password = password.hashCode();
        _messages = new ArrayList<>();
    }

    /**
     * @return the objects unique string.
     */
    @Override
    public String getUnique() {
        return _id.toString();
    }

    /**
     * @return the _id variable.
     */
    public ID getId() {
        return _id;
    }

    /**
     * @return the _name variable.
     */
    public String getName() {
        return _name;
    }

    /**
     * @return the _surname variable.
     */
    public String getSurname() {
        return _surname;
    }

    /**
     * @return the _address variable.
     */
    public Address getAddress() {
        return _address;
    }

    /**
     * @return the _password variable.
     */
    public int getPassword() {
        return _password;
    }

    /**
     * @return the _messages variable. Represents the stored messages to the user.
     */
    @Override
    public ArrayList< I_Message > getMessages() {
        return _messages;
    }

    /**
     * @param name the new contents to set _name to.
     */
    public void setName(String name) {
        _name = name;
        save();
    }

    /**
     * @param surname the new contents to set _surname to.
     */
    public void setSurname(String surname) {
        _surname = surname;
        save();
    }

    /**
     * @param address the new contents to set _address to.
     */
    public void setAddress(Address address) {
        _address = address;
        save();
    }

    /**
     * @param password the new contents to set _password to.
     */
    public void setPassword(String password) {
        _password = password.hashCode();
        save();
    }

    /**
     * @param messages the new contents to set _prescriptions to.
     */
    @Override
    public void setMessage(ArrayList< I_Message > messages) {
        _messages = messages;
        save();
    }

    /**
     * Save the object.
     */
    protected void save(){
        UserRepositoryController.getInstance().save();
    }
}
