package controllers.serialisation;

/**
 * Identifies an object as a deserialisation strategy.
 */
public interface I_DeserialisationStrategy {
    /**
     * @param fileName the destination file that contains the target object.
     * @return the deserialised object.
     */
    public abstract Object deserialise(String fileName);
}
