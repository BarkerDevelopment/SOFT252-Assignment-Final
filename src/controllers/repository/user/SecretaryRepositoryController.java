package controllers.repository.user;

import models.users.Secretary;
import models.users.info.UserRole;

/**
 * A class that controls the interactions with the Secretary repository.
 */
public class SecretaryRepositoryController extends GenericUserRepositoryController< Secretary > {
    /**
     * Default constructor. Creates an object without a repository.
     */
    public SecretaryRepositoryController() {
        super(UserRole.SECRETARY.getFileName());
    }
}
