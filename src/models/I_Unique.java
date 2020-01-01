package models;

/**
 * Implies that an object has a unique string that objects of the same class do not share.
 *
 * @param <T> the type of the unique variable.
 */
public interface I_Unique< T > {
    /**
     * @return T the objects unique string.
     */
    public abstract T getUnique();
}
