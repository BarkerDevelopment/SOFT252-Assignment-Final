package controllers.serialisation.strategies;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Defines a class for the default serialisation strategy.
 */
public class DefaultSerialisationStrategy implements I_SerialisationStrategy {
    /**
     * @param fileName the destination file name.
     * @param obj      the object to be serialised.
     */
    @Override
    public void serialise(String fileName, Object obj) {
        try
        {
            FileOutputStream file = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(obj);

            out.close();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
