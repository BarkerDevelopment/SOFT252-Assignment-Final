package models.users.info;

import controllers.repository.*;
import controllers.repository.user.AdminRepositoryController;
import controllers.repository.user.DoctorRepositoryController;
import controllers.repository.user.PatientRepositoryController;
import controllers.repository.user.SecretaryRepositoryController;
import models.I_Printable;

import java.lang.reflect.InvocationTargetException;

/**
 * An enumeration representing the possible user roles.
 */
public enum UserRole
        implements I_EnumRepositoryControllerKey, I_Printable {
    ADMIN('A', "admins", AdminRepositoryController.class),
    DOCTOR('D', "doctors", DoctorRepositoryController.class),
    SECRETARY('S', "secretaries", SecretaryRepositoryController.class),
    PATIENT('P', "patients", PatientRepositoryController.class);

    private final char _roleString;
    private final String _filename;
    private final Class< ? > _repositoryControllerClass;

    /**
     * Enum constructor assigning input variables.
     *
     * @param roleString the character required for the ID.
     * @param fileName the file destination of the repository content.
     * @param repositoryControllerClass the repository controller class.
     */
    private UserRole(char roleString, String fileName, Class< ? > repositoryControllerClass) {
        _roleString = roleString;
        _filename = fileName;
        _repositoryControllerClass = repositoryControllerClass;
    }

    /**
     * @return the _fileName variable. Represents the file at which the serialised repository is stored.
     */
    @Override
    public String getFileName() {
        return _filename;
    }

    /**
     * @return the repository controller specific to the role.
     */
    public I_SingleRepositoryController< ? > getRepositoryController() {
        try {
            return (I_SingleRepositoryController< ? >) _repositoryControllerClass.getConstructor().newInstance();

        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
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
