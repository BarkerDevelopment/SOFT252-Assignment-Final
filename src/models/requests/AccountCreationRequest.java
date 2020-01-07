package models.requests;

import controllers.repository.UserRepositoryController;
import exceptions.DuplicateObjectException;
import models.users.Patient;
import models.users.info.Address;
import models.users.info.Gender;

import java.time.LocalDate;

/**
 * A class that encapsulates a request to create a new Patient object.
 */
public class AccountCreationRequest extends Request {
    private final String _name;
    private final String _surname;
    private final Address _address;
    private final int _password;
    private final LocalDate _dob;
    private final Gender _gender;

    /**
     * Default constructor.
     *
     * @param name the Patient's name.
     * @param surname the Patient's surname.
     * @param address the Patient's address
     * @param password the Patient's password.
     * @param dob the Patient's date of birth.
     * @param gender the Patient's gender.
     */
    public AccountCreationRequest(String name, String surname, Address address, String password, LocalDate dob, Gender gender) {
        super(RequestType.ACCOUNT_CREATION);

        _name = name;
        _surname = surname;
        _address = address;
        _password = password.hashCode();
        _dob = dob;
        _gender = gender;
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
     * The action following request approval.
     */
    @Override
    public void approveAction() {
        try {
            UserRepositoryController.getInstance().add(new Patient(this));

        }catch (DuplicateObjectException e){
            // TODO handle this correctly.
            e.printStackTrace();
        }
    }

    /**
     * The action following request denial.
     */
    @Override
    public void denyAction() {
        // There is no functionality for a AccountCreationRequest denyAction.
    }
}
