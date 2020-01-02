package controllers.repository.user;

import models.users.Doctor;
import models.users.info.UserRole;

/**
 * A class that controls the interactions with the Doctor repository.
 */
public class DoctorRepositoryController extends GenericUserRepositoryController< Doctor > {
    /**
     * Default constructor. Creates an object without a repository.
     */
    public DoctorRepositoryController() {
        super(UserRole.DOCTOR.getFileName());
    }
}
