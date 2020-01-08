package controllers.primary;

import controllers.primary.login.LoginController;
import controllers.repository.AppointmentRepositoryController;
import controllers.repository.DrugRepositoryController;
import controllers.repository.RequestRepositoryController;
import controllers.repository.UserRepositoryController;
import models.appointments.Appointment;
import models.users.Doctor;
import models.users.Patient;
import models.users.User;
import views.I_Form;
import views.doctor.*;

public class DoctorController implements I_UserController {
    private ViewController _controller;
    private UserRepositoryController _userRepositoryController;
    private DrugRepositoryController _drugRepositoryController;
    private AppointmentRepositoryController _appointmentRepositoryController;
    private RequestRepositoryController _requestRepositoryController;
    private Doctor _user;

    public DoctorController(User user) {
        _controller = ViewController.getInstance();
        _userRepositoryController = UserRepositoryController.getInstance();
        _drugRepositoryController = DrugRepositoryController.getInstance();
        _appointmentRepositoryController = AppointmentRepositoryController.getInstance();
        _requestRepositoryController = RequestRepositoryController.getInstance();
        _user = (Doctor) user;
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
     * @return the initial view.
     */
    @Override
    public I_Form index() {
        return new DoctorIndex(_controller, this);
    }

    public void viewAppointments(){
        _controller.show(new ViewAppointments(_controller, this, _appointmentRepositoryController));
    }

    public void viewAppointment(Appointment appointment){
        _controller.show(new AppointmentForm(_controller, this, _drugRepositoryController, _appointmentRepositoryController, appointment));
    }

    public void viewPatientHistory(Patient patient){
        _controller.show(new ViewPatientHistory(_controller, this, _appointmentRepositoryController, patient));
    }

    public void requestDrugs(){
        _controller.show(new RequestDrug(_controller, this, _requestRepositoryController));
    }

    public void proposeFutureAppointments(){
        _controller.show(new ProposeFutureAppointment(_controller, this, _userRepositoryController, _requestRepositoryController));
    }

    /**
     * Take the user back a form.
     */
    @Override
    public void back() {
        _controller.undo();
    }

    /**
     * Log the user out and return to the login screen.
     */
    @Override
    public void logout() {
        LoginController loginController = LoginController.getInstance();
        loginController.logout();

        _controller.show(loginController.index());
        _controller.clear();
    }
}
