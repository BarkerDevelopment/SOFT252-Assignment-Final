package models.users.info;

import controllers.repository.I_UniqueQueryableRepository;
import controllers.repository.UserRepositoryController;
import exceptions.IdClashException;
import exceptions.OutOfRangeException;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * A class that creates random IDs objects.
 */
public class IDFactory {
    private final UserRole _role;
    private final long _seed;

    /**
     * Creates a factory object that will create random IDs based on the passed role.
     *
     * @param role the role of the resultant IDs.
     */
    public IDFactory(UserRole role) {
        _role = role;
        _seed = 0;
    }

    /**
     * Creates a factory object that will create IDs based on the passed seed.
     * NOTE: Used for testing purposes.
     *
     * @param role the role of the resultant IDs.
     * @param seed the pseudo random number generator seed.
     */
    public IDFactory(UserRole role, long seed) {
        _role = role;
        _seed = seed;
    }

    /**
     * @return the _role variable. Represents the type of ID the factory will create.
     */
    public UserRole getRole() {
        return _role;
    }

    /**
     * Creates a randomly generated ID that doesn't belong to a User already.
     *
     * @return the randomly generated ID.
     */
    public ID create(ArrayList<ID> ids) {
        String idNumber;
        ArrayList< String > idStrings = new ArrayList<>(ids.stream().map(ID::toString).collect(Collectors.toList()));

        do {
            idNumber = _seed == 0 ? generateID(System.currentTimeMillis()) : generateID(_seed);
        } while(idStrings.contains(_role.toString() + idNumber)); // Loop ensures that generated ID doesn't already exist.

        try{
            return new ID(_role, idNumber);

        }catch (OutOfRangeException | IdClashException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Generates a random String of integers.
     *
     * @param seed the pseudo random seed.
     * @return the String of integers.
     */
    private String generateID(long seed){
        Random rand = new Random(seed);
        StringBuilder idNumberBuilder = new StringBuilder();

        for (int i = 0; i < ID.ID_LENGTH; i++) {
            idNumberBuilder.append(rand.nextInt(10));
        }

        return idNumberBuilder.toString();
    }
}
