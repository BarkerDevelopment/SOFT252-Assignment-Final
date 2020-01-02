package controllers.serialisation.strategies;

/**
 * Identifies an object as a serialisation strategy.
 */
public interface I_SerialisationStrategy {
    /**
     * @param fileName the destination file name.
     * @param obj the object to be serialised.
     */
    public abstract void serialise(String fileName, Object obj);
}
