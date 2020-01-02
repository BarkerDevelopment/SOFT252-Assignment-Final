package controllers.repository.user;

import models.users.Admin;
import models.users.info.UserRole;

/**
 * A class that controls the interactions with the Admin repository.
 */
public class AdminRepositoryController extends GenericUserRepositoryController< Admin > {
    /**
     * Default constructor. Creates an object without a repository.
     */
    public AdminRepositoryController() {
        super(UserRole.ADMIN.getFileName());
    }
}
