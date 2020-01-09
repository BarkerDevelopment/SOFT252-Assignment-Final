package project.models.users.info;

import project.controllers.repository.UserRepositoryController;
import project.exceptions.IdClashException;
import project.exceptions.OutOfRangeException;
import project.models.I_Printable;

import java.io.Serializable;
import java.util.stream.Collectors;

/**
 * A class that encapsulates the unique ID of a user.
 */
public class ID
        implements I_Printable, Serializable {
    public static final int ID_LENGTH = 4;

    private final UserRole _role;
    private final String _idNumber;

    /**
     * Creates a ID object.
     *
     * @param role the initial character of the ID that identifies the user to belong to a specific group.
     * @param idNumber a String of numbers.
     * @throws OutOfRangeException if length of idNumber is greater than the constant ID_LENGTH.
     */
    public ID(UserRole role, String idNumber) throws OutOfRangeException, IdClashException {
        if(idNumber.length() > ID_LENGTH)
            throw new OutOfRangeException(String.format("Length of idNumber is too great. Must be less than %d integers.", ID_LENGTH));
        if(UserRepositoryController.getInstance().getIDs(role).stream().map(ID::toString).collect(Collectors.toList()).contains(role.toString() + idNumber))
            throw new IdClashException();


        _role = role;
        _idNumber = idNumber;
    }

    /**
     * @return the _role variable.
     */
    public UserRole getRole() {
        return _role;
    }

    /**
     * @return the _idNumber variable.
     */
    public String getIdNumber() {
        return _idNumber;
    }

    /**
     * @return the object as a string variable.
     */
    @Override
    public String toString(){
        return _role + _idNumber;
    }
}
