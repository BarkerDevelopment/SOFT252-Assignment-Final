package controllers.repository.user;

import models.users.Admin;
import models.users.info.UserRole;

/**
 * A class that controls the interactions with the Admin repository.
 */
public class AdminRepositoryController extends GenericUserRepositoryController< Admin > {
    private static AdminRepositoryController INSTANCE;

    /**
     * Singleton constructor.
     */
    private AdminRepositoryController() {
        super(UserRole.ADMIN.getFileName());
    }

    /**
     * AdminRepositoryController implements the Singleton pattern.
     *
     * @return the Singleton RequestRepositoryController.
     */
    public static AdminRepositoryController getInstance(){
        if(INSTANCE == null) INSTANCE = new AdminRepositoryController();

        return INSTANCE;
    }
}
