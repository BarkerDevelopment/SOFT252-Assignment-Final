package models.users;

import exceptions.OutOfRangeException;
import models.users.info.Address;
import models.users.info.UserRole;
import models.messaging.I_MessageSender;

/**
 * A User subclass for the system's secretaries.
 */
public class Secretary extends User
    implements I_MessageSender {

    public static UserRole ROLE = UserRole.SECRETARY;

    /**
     * Creates an Secretary object.
     *
     * @param name the Secretary's name.
     * @param surname the Secretary's surname.
     * @param address the Secretary's address
     * @param password the Secretary's password.
     */
    public Secretary(String idNumber, String name, String surname, Address address, String password) throws OutOfRangeException {
        super(ROLE, idNumber, name, surname, address, password);
    }

    /**
     * Creates a bare dummy Secretary object for testing purposes. The name and surname are used to provide another element of
     * individuality to increase ease of testing rather than just looking at IDs.
     *
     * @param idNumber the Secretary's ID number. This will be added to the Secretary role string to create the Secretary ID.
     * @param name the Secretary's name.
     * @param surname the Secretary's surname.
     */
    public Secretary(String idNumber, String name, String surname) throws OutOfRangeException {
        super(ROLE, idNumber, name, surname, new Address(), "password");
    }

    /**
     * Creates a bare dummy Secretary object for testing purposes, particularly the login system. The name and surname
     * are used to provide another element of individuality to increase ease of testing rather than just looking at IDs.
     *
     * @param idNumber the Secretary's ID number. This will be added to the Secretary role string to create the Secretary ID.
     * @param name the Secretary's name.
     * @param surname the Secretary's surname.
     * @param password the Secretary's password.
     */
    public Secretary(String idNumber, String name, String surname, String password) throws OutOfRangeException {
        super(ROLE, idNumber, name, surname, new Address(), password);
    }
}
