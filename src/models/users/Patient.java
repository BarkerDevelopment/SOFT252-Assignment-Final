package models.users;

import exceptions.IdClashException;
import exceptions.OutOfRangeException;
import models.appointments.I_AppointmentParticipant;
import models.drugs.I_Prescription;

import models.drugs.I_PrescriptionHolder;
import models.feedback.I_FeedbackSender;
import models.requests.AccountCreationRequest;
import models.users.info.Address;
import models.users.info.Gender;
import models.users.info.UserRole;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * A User subclass for the system's patients.
 */
public class Patient extends User
    implements I_AppointmentParticipant, I_PrescriptionHolder, I_FeedbackSender {

    public static UserRole ROLE = UserRole.PATIENT;

    private final LocalDate _dob;
    private final Gender _gender;

    private ArrayList< I_Prescription > _prescriptions;

    /**
     * Creates a Patient object from a request.
     *
     * @param request the account creation request.
     */
    public Patient(AccountCreationRequest request) {
        super(ROLE, request.getName(), request.getSurname(), request.getAddress(), request.getPassword());
        _dob = request.getDob();
        _gender = request.getGender();

        _prescriptions = new ArrayList<>();
    }

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
    public Patient(String idNumber, String name, String surname, Address address, String password, LocalDate dob, Gender gender) throws OutOfRangeException, IdClashException {
        super(ROLE, idNumber, name, surname, address, password);
        _dob = dob;
        _gender = gender;

        _prescriptions = new ArrayList<>();
    }

    /**
     * Creates a dummy Patient object for testing purposes.
     * NOTE: This constructor is used to specifically test the random ID functionality.
     *
     * @param name the Patient's name.
     * @param surname the Patient's surname.
     * @param dob the User's date of birth.
     * @param gender the Patient's gender.
     * @param seed the pseudo-random generator seed. This ensures repeatable random generation.
     */
    public Patient(String name, String surname, LocalDate dob, Gender gender, long seed) {
        super(ROLE, name, surname, seed);
        _dob = dob;
        _gender = gender;

        _prescriptions = new ArrayList<>();
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
    public Patient(String idNumber, String name, String surname, Gender gender) throws OutOfRangeException, IdClashException {
        super(ROLE, idNumber, name, surname, new Address(), "password");
        _dob = LocalDate.now();
        _gender = gender;

        _prescriptions = new ArrayList<>();
    }

    /**
     * Creates a bare dummy Patient object for testing purposes, particularly the login system. The name and surname 
     * are used to provide another element of individuality to increase ease of testing rather than just looking at IDs.
     *
     * @param idNumber the Patient's ID number. This will be added to the Patient role string to create the Patient ID.
     * @param name the Patient's name.
     * @param surname the Patient's surname.
     * @param password the Patient's password.
     */
    public Patient(String idNumber, String name, String surname, String password, Gender gender) throws OutOfRangeException, IdClashException {
        super(ROLE, idNumber, name, surname, new Address(), password);
        _dob = LocalDate.now();
        _gender = gender;

        _prescriptions = new ArrayList<>();
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

    /**
     * @return the _prescriptions variable. Represents all the prescriptions the I_PrescriptionHolder has.
     */
    @Override
    public ArrayList< I_Prescription > getPrescriptions() {
        return _prescriptions;
    }

    /**
     * @param prescriptions the new contents to set _prescriptions to.
     */
    @Override
    public void setPrescriptions(ArrayList< I_Prescription > prescriptions) {
        _prescriptions = prescriptions;
        save();
    }
}
