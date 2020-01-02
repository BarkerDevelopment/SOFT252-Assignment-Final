package controllers.repository.user;

import models.users.Doctor;
import models.users.info.UserRole;

/**
 * A class that controls the interactions with the Doctor repository.
 */
public class DoctorRepositoryController extends GenericUserRepositoryController< Doctor > {
    private static DoctorRepositoryController INSTANCE;

    /**
     * Singleton constructor.
     */
    private DoctorRepositoryController() {
        super(UserRole.DOCTOR.getFileName());
    }

    /**
     * DoctorRepositoryController implements the Singleton pattern.
     *
     * @return the Singleton RequestRepositoryController.
     */
    public static DoctorRepositoryController getInstance(){
        if(INSTANCE == null) INSTANCE = new DoctorRepositoryController();

        return INSTANCE;
    }
}
