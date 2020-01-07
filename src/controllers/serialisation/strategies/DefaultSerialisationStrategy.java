package controllers.serialisation.strategies;

import java.io.*;

/**
 * Defines a class for the default serialisation strategy.
 */
public class DefaultSerialisationStrategy
        implements I_SerialisationStrategy {
    /**
     * @param fileName the destination file name.
     * @param obj      the object to be serialised.
     */
    @Override
    public void serialise(String fileName, Object obj) throws IOException {
        FileOutputStream file = new FileOutputStream(fileName);
        ObjectOutputStream out = new ObjectOutputStream(file);

        out.writeObject(obj);

        out.close();
        file.close();
    }

    /**
     * @param fileName the destination file that contains the target object.
     * @return the deserialised object.
     */
    @Override
    public Object deserialise(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(fileName);
        ObjectInputStream in = new ObjectInputStream(file);

        Object object = in.readObject();

        in.close();
        file.close();

        return object;
    }
}
