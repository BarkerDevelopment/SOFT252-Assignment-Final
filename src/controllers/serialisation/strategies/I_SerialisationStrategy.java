package controllers.serialisation.strategies;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Identifies an object as a serialisation strategy for serialising and deserialising objects..
 */
public interface I_SerialisationStrategy {
    /**
     * @param fileName the destination file name.
     * @param obj the object to be serialised.
     */
    public abstract void serialise(String fileName, Object obj) throws Exception;

    /**
     * @param fileName the destination file that contains the target object.
     * @return the deserialised object.
     */
    public abstract Object deserialise(String fileName) throws Exception;
}
