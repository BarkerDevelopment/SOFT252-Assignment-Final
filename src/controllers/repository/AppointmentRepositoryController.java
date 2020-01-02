package controllers.repository;

import exceptions.AppointmentClashException;
import models.appointments.Appointment;
import models.appointments.I_Appointment;
import models.appointments.I_AppointmentParticipant;
import models.repositories.Repository;
import models.users.Doctor;
import models.users.Patient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * A class that controls the interactions with the Appointment repository.
 */
public class AppointmentRepositoryController
        implements I_SingleRepositoryController< Appointment > {
    private static AppointmentRepositoryController INSTANCE;

    private final String _fileName;
    private Repository _repository;

    /**
     * Singleton constructor.
     */
    private AppointmentRepositoryController() {
        _fileName = "appointments";
        _repository = null;
    }

    /**
     * AppointmentRepositoryController implements the Singleton pattern.
     *
     * @return the Singleton RequestRepositoryController.
     */
    public static AppointmentRepositoryController getInstance(){
        if(INSTANCE == null) INSTANCE = new AppointmentRepositoryController();

        return INSTANCE;
    }

    /**
     * @return the _fileName variable. Represents the file that stores the repository contents.
     */
    @Override
    public String getFileName() {
        return _fileName;
    }

    /**
     * @return the _repository variable. Represents the repository the object controls.
     */
    @Override
    public Repository getRepository() {
        return _repository;
    }

    /**
     * @param repository the new contents of the _repository variable.
     */
    @Override
    public void setRepository(Repository repository) {
        _repository = repository;
    }

    /**
     * @return the contents of the repository cast to the correct type.
     */
    @Override
    public ArrayList< Appointment > get() {
        ArrayList< Appointment > content = new ArrayList<>();
        _repository.get().forEach(i -> content.add( (Appointment) i) );

        return content;
    }

    /**
     * @return all appointments of a specific participant.
     */
    public ArrayList< I_Appointment > get(I_AppointmentParticipant participant){
        return new ArrayList<>( this.get().stream().filter(
                a -> a.getParticipants().contains(participant)
        ).collect(Collectors.toList()));
    }

    /**
     * @return all appointments of a specified day.
     */
    public ArrayList< I_Appointment > get(LocalDate date) {
        return new ArrayList<>( this.get().stream().filter(
                a -> a.getDateTime().toLocalDate().equals(date)
        ).collect(Collectors.toList()));
    }

    /**
     * Adds an item to the repository.
     *
     * @param item the item to be added.
     */
    @Override
    public void add(Appointment item) throws AppointmentClashException {
        LocalDateTime dateTime = item.getDateTime();
        Patient patient = item.getPatient();
        Doctor doctor = item.getDoctor();

        // Retrieves all appointments at the request's time that involve either one of the participants.
        ArrayList< I_Appointment > clashAppointments = new ArrayList<>(this.get(dateTime.toLocalDate())
                .stream()
                .filter(a -> a.getParticipants().contains(patient) || a.getParticipants().contains(doctor))
                .collect(Collectors.toList())
        );

        if(clashAppointments.isEmpty()) {
            _repository.get().add(item);

        }else{
            if(clashAppointments.size() == 2){
                throw new AppointmentClashException("Both participants are involved in another appointment at this time.");

            }else{
                I_Appointment clash = clashAppointments.get(0);

                throw new AppointmentClashException(
                        String.format("The %s is already involved involved in another appointment at this time.",
                        (clash.getDoctor().equals(doctor) ? "doctor" : "patient"))
                );
            }
        }
    }

    /**
     * Add a collection of items to the repository.
     *
     * @param items the collection of items to be added.
     */
    @Override
    public void add(ArrayList< Appointment > items) throws AppointmentClashException {
        for (Appointment item : items) {
            this.add(item);
        }
    }

    /**
     * Removes an item from the repository.
     *
     * @param item the item to be removed.
     */
    @Override
    public void remove(Appointment item) {
        _repository.get().remove(item);
    }

    /**
     * Removes a collection of items from the repository.
     *
     * @param items the collection of items to be removed.
     */
    @Override
    public void remove(ArrayList< Appointment > items) {
        _repository.get().removeAll(items);
    }

    /**
     * Clears the repository.
     */
    @Override
    public void clear() {
        _repository.get().clear();
    }
}
