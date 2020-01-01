package models.appointments;

import models.drugs.I_Prescription;
import models.users.Doctor;
import models.users.Patient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class that encapsulates patient's appointments.
 */
public class Appointment
        implements I_Appointment {

    private final Patient _patient;
    private Doctor _doctor;
    private LocalDateTime _dateTime;
    private String _notes;
    private ArrayList< I_Prescription > _prescriptions;
    private boolean _isCompleted;

    /**
     * Default appointment constructor.
     *
     * @param patient the patient.
     * @param doctor the doctor.
     * @param dateTime the date and time of the appointment.
     */
    public Appointment(Patient patient, Doctor doctor, LocalDateTime dateTime) {
        _patient = patient;
        _doctor = doctor;
        _dateTime = dateTime;
        _prescriptions = new ArrayList<>();
        _isCompleted = false;
    }

    /**
     * @return the participants of the appointment.
     */
    @Override
    public ArrayList< I_AppointmentParticipant > getParticipants() {
        return new ArrayList<>(Arrays.asList(_doctor, _patient));
    }

    /**
     * @return the _patient variable.
     */
    @Override
    public Patient getPatient() {
        return _patient;
    }

    /**
     * @return the _doctor variable.
     */
    @Override
    public Doctor getDoctor() {
        return _doctor;
    }

    /**
     * @return the _dateTime variable.
     */
    @Override
    public LocalDateTime getDateTime() {
        return _dateTime;
    }

    /**
     *
     * @return the _notes variable.
     */
    public String getNotes() {
        return _notes;
    }

    /**
     * @return the _prescriptions variable.
     */
    public ArrayList<I_Prescription> getPrescriptions() {
        return _prescriptions;
    }

    /**
     * @param doctor the value to set _doctor to.
     */
    public void setDoctor(Doctor doctor) {
        _doctor = doctor;
    }

    /**
     * @param dateTime the value to set _dateTime to.
     */
    public void setDateTime(LocalDateTime dateTime) {
        _dateTime = dateTime;
    }

    /**
     * @param notes the value to set _notes to.
     */
    public void setNotes(String notes) {
        _notes = notes;
    }

    /**
     * @param prescriptions the value to set _prescriptions to.
     */
    public void setPrescriptions(ArrayList<I_Prescription> prescriptions) {
        _prescriptions = prescriptions;
    }
}