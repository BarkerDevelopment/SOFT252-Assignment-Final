package models.users;

import models.users.info.Address;
import models.users.info.Role;

/**
 * A User subclass for the system's secretaries.
 */
public class Secretary extends User {

    public static Role ROLE = Role.SECRETARY;

    /**
     * Creates an Secretary object.
     *
     * @param name the Secretary's name.
     * @param surname the Secretary's surname.
     * @param address the Secretary's address
     * @param password the Secretary's password.
     */
    public Secretary(String name, String surname, Address address, int password) {
        super(ROLE, name, surname, address, password);
    }

    /**
     * Creates a dummy Secretary object for testing purposes.
     * NOTE: This constructor is used to specifically test the random ID functionality.
     *
     * @param name the Secretary's name.
     * @param surname the Secretary's surname.
     * @param password the Secretary's password.
     * @param seed the pseudo-random generator seed. This ensures repeatable random generation.
     */
    public Secretary(String name, String surname, int password, long seed) {
        super(ROLE, name, surname, password, seed);
    }

    /**
     * Creates a bare dummy Secretary object for testing purposes. The name and surname are used to provide another element of
     * individuality to increase ease of testing rather than just looking at IDs.
     *
     * @param idNumber the Secretary's ID number. This will be added to the Secretary role string to create the Secretary ID.
     * @param name the Secretary's name.
     * @param surname the Secretary's surname.
     */
    public Secretary(String idNumber, String name, String surname) {
        super(ROLE, idNumber, name, surname);
    }
}
