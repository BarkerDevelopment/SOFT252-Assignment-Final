package models.users.info;

import controllers.repository.*;
import models.I_Printable;

import java.lang.reflect.InvocationTargetException;

/**
 * An enumeration representing the possible user roles.
 */
public enum UserRole
        implements I_EnumRepositoryControllerKey, I_Printable {
    ADMIN('A', "admins"),
    DOCTOR('D', "doctors"),
    SECRETARY('S', "secretaries"),
    PATIENT('P', "patients");

    private final char _roleString;
    private final String _filename;

    /**
     * Enum constructor assigning input variables.
     *
     * @param roleString the character required for the ID.
     * @param fileName the file destination of the repository content.
     */
    private UserRole(char roleString, String fileName) {
        _roleString = roleString;
        _filename = fileName;
    }

    /**
     * @return the _fileName variable. Represents the file at which the serialised repository is stored.
     */
    @Override
    public String getFileName() {
        return _filename;
    }

    /**
     * @return the object as a string.
     */
    @Override
    public String toString(){
        return Character.toString(_roleString);
    }

    /**
     * Returns an instance of the enum based of the character inputted.
     *
     * @param input the character that corresponds to an enum instance.
     * @return an enum instance.
     * @throws EnumConstantNotPresentException if character does not correspond to an enum instance.
     */
    public static UserRole fromChar(char input) throws EnumConstantNotPresentException{
        for(UserRole instance : UserRole.values()){
            if (instance._roleString == input) return instance;
        }

        throw new EnumConstantNotPresentException(UserRole.class, Character.toString(input));
    }
}
