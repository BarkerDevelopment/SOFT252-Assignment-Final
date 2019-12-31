package models.users;

import models.users.info.Address;
import models.users.info.ID;
import models.users.info.IDFactory;
import models.users.info.Role;

/**
 * Super class for the system users.
 */
public class User {

    private final ID _id;
    private String _name;
    private String _surname;
    private Address _address;
    private int _password;

    /**
     * Creates a User object.
     *
     * @param role the User's role. This should be predefined as the subclass _role variable.
     * @param name the User's name.
     * @param surname the User's surname.
     * @param address the User's address
     * @param password the User's password.
     */
    public User(Role role, String name, String surname, Address address, int password){
        _id = new IDFactory(role).create();
        _name = name;
        _surname = surname;
        _address = address;
        _password = password;
    }

    /**
     * Creates a dummy User object for testing purposes.
     * NOTE: This constructor is used to specifically test the random ID functionality.
     *
     * @param role the User's role. This should be predefined as the subclass _role variable.
     * @param name the User's name.
     * @param surname the User's surname.
     * @param password the User's password.
     * @param seed the pseudo-random generator seed. This ensures repeatable random generation.
     */
    public User(Role role, String name, String surname, int password, long seed){
        _id = new IDFactory(role, seed).create();
        _name = name;
        _surname = surname;
        _address = new Address();
        _password = password;
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
    public User(Role role, String idNumber, String name, String surname){
        _id = new ID(role, idNumber);
        _name = name;
        _surname = surname;
        _address = new Address();
        _password = "password".hashCode();
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
     * @param name the new contents to set _name to.
     */
    public void setName(String name) {
        _name = name;
    }

    /**
     * @param surname the new contents to set _surname to.
     */
    public void setSurname(String surname) {
        _surname = surname;
    }

    /**
     * @param address the new contents to set _address to.
     */
    public void setAddress(Address address) {
        _address = address;
    }

    /**
     * @param password the new contents to set _password to.
     */
    public void setPassword(String password) {
        _password = password.hashCode();
    }
}