package models.users.info;

import models.I_Printable;

/**
 * An enumeration representing the possible user roles.
 */
public enum Role
        implements I_Printable {
    ADMIN('A'),
    DOCTOR('D'),
    SECRETARY('S'),
    PATIENT('P');

    private final char _roleString;
    /**
     * Enum constructor assigning input variables.
     *
     * @param roleString the character required for the ID.
     */
    private Role(char roleString) {
        _roleString = roleString;
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
    public static Role fromChar(char input) throws EnumConstantNotPresentException{
        for(Role instance : Role.values()){
            if (instance._roleString == input) return instance;
        }

        throw new EnumConstantNotPresentException(Role.class, Character.toString(input));
    }
}
