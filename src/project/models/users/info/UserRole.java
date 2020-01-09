package project.models.users.info;

import project.controllers.primary.*;
import project.controllers.repository.*;
import project.models.I_Printable;
import project.models.users.User;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

/**
 * An enumeration representing the possible user roles.
 */
public enum UserRole
        implements I_EnumRepositoryControllerKey, I_Printable, Serializable {
    ADMIN('A', "admins", AdminController.class),
    DOCTOR('D', "doctors", DoctorController.class),
    SECRETARY('S', "secretaries", SecretaryController.class),
    PATIENT('P', "patients", PatientController.class);

    private final char _roleString;
    private final String _filename;
    private final Class< ? > _viewController;

    /**
     * Enum constructor assigning input variables.
     *
     * @param roleString the character required for the ID.
     * @param fileName the file destination of the repository content.
     */
    private UserRole(char roleString, String fileName, Class< ? > viewController) {
        _roleString = roleString;
        _filename = fileName;
        _viewController = viewController;
    }

    /**
     * @return the _fileName variable. Represents the file at which the serialised repository is stored.
     */
    @Override
    public String getFileName() {
        return _filename;
    }

    /**
     * @return the view controller corresponding to the UserRole.
     */
    public I_UserController getViewController(User user) {
        try{
            return (I_UserController) _viewController.getConstructor(User.class).newInstance(user);

        }catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
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
            if (instance._roleString == Character.toUpperCase(input)) return instance;
        }

        throw new EnumConstantNotPresentException(UserRole.class, Character.toString(input));
    }
}
