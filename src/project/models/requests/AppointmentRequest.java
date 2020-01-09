package project.models.requests;

import project.controllers.auxiliary.MessageController;
import project.controllers.repository.AppointmentRepositoryController;
import project.exceptions.AppointmentClashException;
import project.models.appointments.Appointment;
import project.models.messaging.Message;
import project.models.users.Doctor;
import project.models.users.Patient;

import java.time.LocalDateTime;

/**
 * A class that encapsulates a request for an appointment.
 */
public class AppointmentRequest extends Request {

    private final Patient _patient;
    private Doctor _doctor;
    private LocalDateTime _dateTime;

    /**
     * Default constructor.
     * Additionally, this constructor adds the resultant object to its corresponding repository: RequestRepository.
     *
     * @param requester the patient requesting the appointment.
     * @param doctor the requested doctor.
     * @param dateTime the requested dateTime for the appointment.
     */
    public AppointmentRequest(Patient requester, Doctor doctor, LocalDateTime dateTime) {
        super(RequestType.APPOINTMENT);

        _patient = requester;
        _doctor = doctor;
        _dateTime = dateTime;
    }

    /**
     * @return the _patient variable.
     */
    public Patient getPatient() {
        return _patient;
    }

    /**
     * @return the _doctor variable.
     */
    public Doctor getDoctor() {
        return _doctor;
    }

    /**
     * @return the _dateTime variable.
     */
    public LocalDateTime getDateTime() {
        return _dateTime;
    }

    /**
     * @param doctor the new doctor of the appointment.
     */
    public void setDoctor(Doctor doctor) {
        _doctor = doctor;
    }

    /**
     * @param dateTime the new dateTime of the appointment.
     */
    public void setDateTime(LocalDateTime dateTime) {
        _dateTime = dateTime;
        save();
    }

    /**
     * The action following request approval.
     */
    @Override
    public void approveAction() {
        try {
            AppointmentRepositoryController.getInstance().add(new Appointment(this));

            MessageController.send(_patient, new Message(this,
                    String.format("You have an appointment with Dr.%s at %s.",
                            _doctor.getSurname(), _dateTime.toString())
                ));

            MessageController.send(_doctor, new Message(this,
                    String.format("You have an appointment with patient %s at %s.",
                            _patient.getId().toString(), _dateTime.toString())
                ));

        } catch (AppointmentClashException e) {
            //TODO handle this error properly.
            denyAction();
        }
    }

    /**
     * The action following request denial.
     */
    @Override
    public void denyAction() {
        MessageController.send(_patient, new Message(this,
                String.format("Your request for an appointment with Dr.%s at %s has been denied.",
                        _doctor.getSurname(), _dateTime.toString() ))
        );
    }
}
