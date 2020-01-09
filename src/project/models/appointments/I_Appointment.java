package project.models.appointments;

import project.models.repositories.I_RepositoryItem;
import project.models.users.Doctor;
import project.models.users.Patient;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Defines the functions for an appointment object.
 */
public interface I_Appointment extends I_RepositoryItem {
    /**
     * @return the _participants variable. Represents all the participants in the appointment.
     */
    public abstract ArrayList< I_AppointmentParticipant > getParticipants();

    /**
     * @return the patient involved in the appointment. This is contained within the _participants variable.
     */
    public abstract Patient getPatient();

    /**
     * @return the doctor involved in the appointment. This is contained within the _participants variable.
     */
    public abstract Doctor getDoctor();

    /**
     * @return the _dateTime variable. Represents the date and time of the appointment.
     */
    public abstract LocalDateTime getDateTime();
}
