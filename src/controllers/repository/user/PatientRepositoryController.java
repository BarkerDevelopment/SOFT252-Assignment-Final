package controllers.repository.user;

import models.users.Patient;
import models.users.info.UserRole;

/**
 * A class that controls the interactions with the Patient repository.
 */
public class PatientRepositoryController extends GenericUserRepositoryController< Patient > {
    private static PatientRepositoryController INSTANCE;

    /**
     * Singleton constructor.
     */
    private PatientRepositoryController() {
        super(UserRole.PATIENT.getFileName());
    }

    /**
     * PatientRepositoryController implements the Singleton pattern.
     *
     * @return the Singleton RequestRepositoryController.
     */
    public static PatientRepositoryController getInstance(){
        if(INSTANCE == null) INSTANCE = new PatientRepositoryController();

        return INSTANCE;
    }
}
