package project.models.users;

import project.exceptions.IdClashException;
import project.exceptions.OutOfRangeException;
import project.models.I_Printable;
import project.models.appointments.I_AppointmentParticipant;
import project.models.feedback.I_Feedback;
import project.models.feedback.I_FeedbackRecipient;
import project.models.users.info.Address;
import project.models.users.info.UserRole;

import java.util.ArrayList;

/**
 * A User subclass for the system's doctor.
 */
public class Doctor extends User
    implements I_AppointmentParticipant, I_FeedbackRecipient, I_Printable {

    public static UserRole ROLE = UserRole.DOCTOR;

    private ArrayList< I_Feedback > _feedback;

    /**
     * Creates an Doctor object.
     *
     * @param name     the Doctor's name.
     * @param surname  the Doctor's surname.
     * @param address  the Doctor's address
     * @param password the Doctor's password.
     */
    public Doctor(String idNumber, String name, String surname, Address address, String password) throws OutOfRangeException, IdClashException {
        super(ROLE, idNumber, name, surname, address, password);
        _feedback = new ArrayList<>();
    }

    /**
     * Creates a dummy Doctor object for testing purposes. NOTE: This constructor is used to specifically test the
     * random ID functionality.
     *
     * @param name     the Doctor's name.
     * @param surname  the Doctor's surname.
     * @param seed     the pseudo-random generator seed. This ensures repeatable random generation.
     */
    public Doctor(String name, String surname, long seed) {
        super(ROLE, name, surname, seed);
        _feedback = new ArrayList<>();
    }

    /**
     * Creates a bare dummy Doctor object for testing purposes. The name and surname are used to provide another element
     * of individuality to increase ease of testing rather than just looking at IDs.
     *
     * @param idNumber the Doctor's ID number. This will be added to the Doctor role string to create the Doctor ID.
     * @param name     the Doctor's name.
     * @param surname  the Doctor's surname.
     */
    public Doctor(String idNumber, String name, String surname) throws OutOfRangeException, IdClashException {
        super(ROLE, idNumber, name, surname, new Address(), "password");
        _feedback = new ArrayList<>();
    }

    /**
     * Creates a bare dummy Doctor object for testing purposes, particularly the login system. The name and surname 
     * are used to provide another element of individuality to increase ease of testing rather than just looking at IDs.
     *
     * @param idNumber the Doctor's ID number. This will be added to the Doctor role string to create the Doctor ID.
     * @param name the Doctor's name.
     * @param surname the Doctor's surname.
     * @param password the Doctor's password.
     */
    public Doctor(String idNumber, String name, String surname, String password) throws OutOfRangeException, IdClashException {
        super(ROLE, idNumber, name, surname, new Address(), password);
        _feedback = new ArrayList<>();
    }

    /**
     * @return an ArrayList of all stored feedback.
     */
    @Override
    public ArrayList< I_Feedback > getFeedback() {
        return _feedback;
    }

    /**
     * @param feedback an ArrayList of feedback to be stored. This replaces the current ArrayList.
     */
    @Override
    public void setFeedback(ArrayList< I_Feedback > feedback) {
        _feedback = feedback;
        save();
    }

    @Override
    public String toString() {
        return String.format("Dr. %s %s", super.getName(), super.getSurname());
    }
}
