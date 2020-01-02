package controllers.repository.user;

import models.users.Patient;
import models.users.info.UserRole;

/**
 * A class that controls the interactions with the Patient repository.
 */
public class PatientRepositoryController extends GenericUserRepositoryController< Patient > {
    /**
     * Default constructor. Creates an object without a repository.
     */
    public PatientRepositoryController() {
        super(UserRole.PATIENT.getFileName());
    }
}
