package models.users.info;

import controllers.repository.*;
import models.I_Printable;

import java.lang.reflect.InvocationTargetException;

/**
 * An enumeration representing the possible user roles.
 */
public enum Role
        implements I_Printable {
    ADMIN('A', AdminRepositoryController.class),
    DOCTOR('D', DoctorRepositoryController.class),
    SECRETARY('S', SecretaryRepositoryController.class),
    PATIENT('P', PatientRepositoryController.class);

    private final char _roleString;
    private final Class< ? > _repositoryControllerClass;

    /**
     * Enum constructor assigning input variables.
     *
     * @param roleString the character required for the ID.
     */
    private Role(char roleString, Class< ? > repositoryControllerClass) {
        _roleString = roleString;
        _repositoryControllerClass = repositoryControllerClass;
    }

    /**
     * @return the object as a string.
     */
    @Override
    public String toString(){
        return Character.toString(_roleString);
    }

    /**
     * @return the repository controller specific to the role.
     */
    public I_RepositoryController< ? > getRepositoryController() {
        try {
            return (I_RepositoryController< ? >) _repositoryControllerClass.getConstructor().newInstance();

        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns an instance of the enum based of the character inputted.
     *
     * @param input the character that corresponds to an enum instance.
     * @return an enum instance.
     * @throws EnumConstantNotPresentException if character does not correspond to an enum instance.
     */
    public static Role fromChar(char input) throws EnumConstantNotPresentException{
        for(Role instance : Role.values()){
            if (instance._roleString == input) return instance;
        }

        throw new EnumConstantNotPresentException(Role.class, Character.toString(input));
    }
}
