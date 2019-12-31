package models.users;

import models.appointments.I_AppointmentParticipant;
import models.drugs.I_Prescription;

import models.feedback.I_FeedbackSender;
import models.users.info.Address;
import models.users.info.Gender;
import models.users.info.Role;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * A User subclass for the system's patients.
 */
public class Patient extends User
    implements I_AppointmentParticipant, I_FeedbackSender {

    public static Role ROLE = Role.PATIENT;

    private final LocalDate _dob;
    private final Gender _gender;

    /**
     * Creates an Patient object.
     *
     * @param name the Patient's name.
     * @param surname the Patient's surname.
     * @param address the Patient's address
     * @param password the Patient's password.
     * @param dob the Patient's date of birth.
     * @param gender the Patient's gender.
     */
    public Patient(String name, String surname, Address address, int password, LocalDate dob, Gender gender) {
        super(ROLE, name, surname, address, password);
        _dob = dob;
        _gender = gender;
    }

    /**
     * Creates a dummy Patient object for testing purposes.
     * NOTE: This constructor is used to specifically test the random ID functionality.
     *
     * @param name the Patient's name.
     * @param surname the Patient's surname.
     * @param password the Patient's password.
     * @param dob the User's date of birth.
     * @param gender the Patient's gender.
     * @param seed the pseudo-random generator seed. This ensures repeatable random generation.
     */
    public Patient(String name, String surname, int password, LocalDate dob, Gender gender, long seed) {
        super(ROLE, name, surname, password, seed);
        _dob = dob;
        _gender = gender;
    }

    /**
     /**
     * Creates a bare dummy Patient object for testing purposes. The name and surname are used to provide another element of
     * individuality to increase ease of testing rather than just looking at IDs.
     *
     * @param idNumber the Patient's ID number. This will be added to the Patient role string to create the Patient ID.
     * @param name the Patient's name.
     * @param surname the Patient's surname.
     * @param gender the Patient's gender.
     */
    public Patient(String idNumber, String name, String surname, Gender gender) {
        super(ROLE, idNumber, name, surname);
        _dob = LocalDate.now();
        _gender = gender;
    }

    /**
     * @return the _dob variable.
     */
    public LocalDate getDob() {
        return _dob;
    }

    /**
     * @return the _gender variable.
     */
    public Gender getGender() {
        return _gender;
    }
}
