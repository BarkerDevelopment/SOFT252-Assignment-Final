package project.models.users;

import project.exceptions.IdClashException;
import project.exceptions.OutOfRangeException;
import project.models.users.info.Address;
import project.models.users.info.UserRole;

/**
 * A User subclass for the system administrators.
 */
public class Admin extends User {

    public static UserRole ROLE = UserRole.ADMIN;

    /**
     * Creates an Admin object.
     *
     * @param name the Admin's name.
     * @param surname the Admin's surname.
     * @param address the Admin's address
     * @param password the Admin's password.
     */
    public Admin(String idNumber, String name, String surname, Address address, String password) throws OutOfRangeException, IdClashException {
        super(ROLE, idNumber, name, surname, address, password);
    }

    /**
     * Creates a dummy Admin object for testing purposes.
     * NOTE: This constructor is used to specifically test the random ID functionality.
     *
     * @param name the Admin's name.
     * @param surname the Admin's surname.
     * @param seed the pseudo-random generator seed. This ensures repeatable random generation.
     */
    public Admin(String name, String surname, long seed) {
        super(ROLE, name, surname, seed);
    }

    /**
     * Creates a bare dummy Admin object for testing purposes. The name and surname are used to provide another element of
     * individuality to increase ease of testing rather than just looking at IDs.
     *
     * @param idNumber the Admin's ID number. This will be added to the Admin role string to create the Admin ID.
     * @param name the Admin's name.
     * @param surname the Admin's surname.
     */
    public Admin(String idNumber, String name, String surname) throws OutOfRangeException, IdClashException {
        super(ROLE, idNumber, name, surname, new Address(), "password");
    }

    /**
     * Creates a bare dummy Admin object for testing purposes, particularly the login system. The name and surname 
     * are used to provide another element of individuality to increase ease of testing rather than just looking at IDs.
     *
     * @param idNumber the Admin's ID number. This will be added to the Admin role string to create the Admin ID.
     * @param name the Admin's name.
     * @param surname the Admin's surname.
     * @param password the Admin's password.
     */
    public Admin(String idNumber, String name, String surname, String password) throws OutOfRangeException, IdClashException {
        super(ROLE, idNumber, name, surname, new Address(), password);
    }
}
