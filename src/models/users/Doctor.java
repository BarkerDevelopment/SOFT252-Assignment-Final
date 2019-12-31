package models.users;

import models.users.info.Address;
import models.users.info.Role;

/**
 * A User subclass for the system's doctor.
 */
public class Doctor extends User{

    public static Role ROLE = Role.DOCTOR;

    /**
     * Creates an Doctor object.
     *
     * @param name     the Doctor's name.
     * @param surname  the Doctor's surname.
     * @param address  the Doctor's address
     * @param password the Doctor's password.
     */
    public Doctor(String name, String surname, Address address, int password) {
        super(ROLE, name, surname, address, password);
    }

    /**
     * Creates a dummy Doctor object for testing purposes. NOTE: This constructor is used to specifically test the
     * random ID functionality.
     *
     * @param name     the Doctor's name.
     * @param surname  the Doctor's surname.
     * @param password the Doctor's password.
     * @param seed     the pseudo-random generator seed. This ensures repeatable random generation.
     */
    public Doctor(String name, String surname, int password, long seed) {
        super(ROLE, name, surname, password, seed);
    }

    /**
     * Creates a bare dummy Doctor object for testing purposes. The name and surname are used to provide another element
     * of individuality to increase ease of testing rather than just looking at IDs.
     *
     * @param idNumber the Doctor's ID number. This will be added to the Doctor role string to create the Doctor ID.
     * @param name     the Doctor's name.
     * @param surname  the Doctor's surname.
     */
    public Doctor(String idNumber, String name, String surname) {
        super(ROLE, idNumber, name, surname);
    }
}
