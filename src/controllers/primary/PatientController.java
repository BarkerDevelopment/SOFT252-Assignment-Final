package controllers.primary;

import controllers.primary.login.LoginController;
import controllers.repository.AppointmentRepositoryController;
import controllers.repository.UserRepositoryController;
import models.appointments.Appointment;
import models.users.Doctor;
import models.users.Patient;
import models.users.User;
import models.users.info.UserRole;
import views.I_Form;
import views.patient.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * A view controller for the Patient user.
 */
public class PatientController implements I_UserController {
    private ViewController _controller;
    private AppointmentRepositoryController _appointmentRepositoryController;
    private UserRepositoryController _userRepositoryController;
    private Patient _user;

    /**
     * Default constructor.
     * @param user the logged in user.
     */
    public PatientController(User user) {
        _controller = ViewController.getInstance();
        _appointmentRepositoryController = AppointmentRepositoryController.getInstance();
        _userRepositoryController = UserRepositoryController.getInstance();
        _user = (Patient) user;
    }

    /**
     * @return the user.
     */
    @Override
    public User getUser() {
        return _user;
    }

    /**
     * Shows the initial view of the controller.
     * @return the initial view for the user.
     */
    @Override
    public I_Form index() {
        return new PatientIndex(_controller, this);
    }

    /**
     * Show the user a form that shows their appointments.
     */
    public void viewAppointments(){
        _controller.show(new ViewAppointments(_controller, this, _appointmentRepositoryController));
    }

    /**
     * Shows the user the form to provide feedback about doctors.
     * @param doctor the target doctor.
     */
    public void reviewDoctor(Doctor doctor){
        _controller.show(new ReviewDoctor(this, doctor));
    }

    /**
     * Shows the user the form to request an appointment.
     */
    public void requestAppointment(){
        ArrayList< Doctor > doctors = new ArrayList<>(
                _userRepositoryController.getRepository(UserRole.DOCTOR).get()
                    .stream()
                    .map(doctor -> (Doctor) doctor)
                        .collect(Collectors.toList())
        );

        _controller.show(new RequestAppointment(this, _user, doctors));
    }

    /**
     * Shows the user the form to see their prescriptions.
     */
    public void viewPrescriptions(){
        _controller.show(new ViewPrescriptions(this));
    }

    /**
     * Take the user back a form.
     */
    @Override
    public void back(){
        _controller.undo();
    }

    /**
     * Log the user out and return to the login screen.
     */
    @Override
    public void logout(){
        LoginController loginController = LoginController.getInstance();
        loginController.logout();

        _controller.show(loginController.index());
        _controller.clear();
    }
}
