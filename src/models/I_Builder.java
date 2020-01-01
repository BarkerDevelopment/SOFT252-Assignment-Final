package models;

/**
 * Implements the Builder design pattern on an object.
 *
 * @param <T> the type of object to create.
 */
public interface I_Builder< T > {
    /**
     * @return the object based on the builder.
     */
    public abstract T build();
}
