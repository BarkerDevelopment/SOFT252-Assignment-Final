package controllers.serialisation.strategies;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Defines a class for the default deserialisation strategy.
 */
public class DefaultDeserialisationStrategy implements I_DeserialisationStrategy {
    /**
     * @param fileName the destination file that contains the target object.
     * @return the deserialised object.
     */
    @Override
    public Object deserialise(String fileName) {
        try
        {
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(file);

            Object object = in.readObject();

            in.close();
            file.close();

            return object;
        }
        catch(IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
