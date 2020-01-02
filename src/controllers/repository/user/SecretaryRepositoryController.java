package controllers.repository.user;

import models.users.Secretary;
import models.users.info.UserRole;

/**
 * A class that controls the interactions with the Secretary repository.
 */
public class SecretaryRepositoryController extends GenericUserRepositoryController< Secretary > {
    private static SecretaryRepositoryController INSTANCE;

    /**
     * Singleton constructor.
     */
    private SecretaryRepositoryController() {
        super(UserRole.SECRETARY.getFileName());
    }

    /**
     * SecretaryRepositoryController implements the Singleton pattern.
     *
     * @return the Singleton RequestRepositoryController.
     */
    public static SecretaryRepositoryController getInstance(){
        if(INSTANCE == null) INSTANCE = new SecretaryRepositoryController();

        return INSTANCE;
    }
}
