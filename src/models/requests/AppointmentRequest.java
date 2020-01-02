package models.requests;

import exceptions.AppointmentClashException;
import models.users.Doctor;
import models.users.Patient;

import java.time.LocalDateTime;

/**
 * A class that encapsulates a request for an appointment.
 */
public class AppointmentRequest extends Request{

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
    }

    /**
     * The action following request approval.
     */
    @Override
    protected void approveAction() {

    }

    /**
     * The action following request denial.
     */
    @Override
    protected void denyAction() {

    }
}
