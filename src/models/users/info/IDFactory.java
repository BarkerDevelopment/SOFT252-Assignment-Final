package models.users.info;

import controllers.repository.I_SingleRepositoryController;
import controllers.repository.I_UniqueQueryableRepository;
import exceptions.OutOfRangeException;

import java.util.Random;

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
        _seed = System.currentTimeMillis();
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
    public ID create(){
        I_SingleRepositoryController< ? > repositoryController = _role.getRepositoryController();

        try{
            I_UniqueQueryableRepository< String, ? > queryableRepositoryController = (I_UniqueQueryableRepository< String, ? >) repositoryController;

            Random rand = new Random(_seed);

            String idNumber;

            do {
                StringBuilder idNumberBuilder = new StringBuilder();

                for (int i = 0; i < ID.ID_LENGTH; i++) {
                    idNumberBuilder.append(rand.nextInt(10));
                }
                idNumber = idNumberBuilder.toString();

                // Loop ensures that generated ID doesn't already exist.
            } while(queryableRepositoryController.contains(_role.toString() + idNumber));

            return new ID(_role, idNumber);

        }catch (ClassCastException | NullPointerException | OutOfRangeException e){
            e.printStackTrace();
            return null;
        }
    }
}
