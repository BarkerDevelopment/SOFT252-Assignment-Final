package controllers.repository;

import controllers.serialisation.RepositorySerialisationController;
import exceptions.AppointmentClashException;
import exceptions.ObjectNotFoundException;
import models.appointments.Appointment;
import models.appointments.I_Appointment;
import models.appointments.I_AppointmentParticipant;
import models.drugs.I_Prescription;
import models.repositories.Repository;
import models.requests.PrescriptionRequest;
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
    private RepositorySerialisationController _serialisationController;

    /**
     * Singleton constructor.
     */
    private AppointmentRepositoryController() {
        _fileName = "appointments";
        _repository = new Repository();
        _serialisationController = new RepositorySerialisationController();
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
    public ArrayList< Appointment > get(I_AppointmentParticipant participant){
        return new ArrayList<>( this.get().stream().filter(
                a -> a.getParticipants().contains(participant)
        ).collect(Collectors.toList()));
    }

    /**
     * @return all appointments of a specified day.
     */
    public ArrayList< Appointment > get(LocalDate date) {
        return new ArrayList<>( this.get().stream().filter(
                a -> a.getDateTime().toLocalDate().equals(date)
        ).collect(Collectors.toList()));
    }

    /**
     * Gets all the future, non completed appointments relating to a user.
     * @param participant the target participant.
     * @return the completed appointments for the participant.
     */
    public ArrayList< Appointment > getPast(I_AppointmentParticipant participant){
        return new ArrayList<>(this.get(participant).stream().filter(
                Appointment::isCompleted
        ).collect(Collectors.toList()));
    }

    /**
     * Gets all the future, non completed appointments relating to a user.
     * @param participant the target participant.
     * @return the completed appointments for the participant.
     */
    public ArrayList< Appointment > getFuture(I_AppointmentParticipant participant){
        return new ArrayList<>(this.get(participant).stream().filter(
                appointment -> ! appointment.isCompleted()
        ).collect(Collectors.toList()));
    }

    /**
     * Adds an item to the repository.
     *
     * @param item the item to be added.
     */
    @Override
    public void add(Appointment item) throws AppointmentClashException {
        if(! _repository.get().contains(item)){
            LocalDateTime dateTime = item.getDateTime();
            Patient patient = item.getPatient();
            Doctor doctor = item.getDoctor();

            // Retrieves all appointments at the request's time that involve either one of the participants.
            ArrayList< I_Appointment > clashAppointments = new ArrayList<>(this.get(dateTime.toLocalDate())
                    .stream()
                    .filter(a -> (a.getParticipants().contains(patient) || a.getParticipants().contains(doctor)) &&
                            a.getDateTime().toLocalTime().equals(dateTime.toLocalTime()))
                    .collect(Collectors.toList())
            );

            if(clashAppointments.isEmpty()) {
                _repository.get().add(item);
                save();

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

        }else{
            throw new AppointmentClashException("The appointment already exists in the system.");
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
    public void remove(Appointment item) throws ObjectNotFoundException
    {
        if(_repository.get().contains(item)){
            _repository.get().remove(item);
            save();

        }else{
            throw new ObjectNotFoundException();
        }
    }

    /**
     * Removes a collection of items from the repository.
     *
     * @param items the collection of items to be removed.
     */
    @Override
    public void remove(ArrayList< Appointment > items) throws ObjectNotFoundException {
        for (Appointment item : items) {
            this.remove(item);
        }
    }

    /**
     * Clears the repository.
     */
    @Override
    public void clear() {
        _repository.get().clear();
        save();
    }

    /**
     * Loads the content repository.
     *
     * @return the repository controller.
     */
    @Override
    public I_RepositoryController< Appointment > load() {
        try {
            _serialisationController.load(this);

        }catch (Exception e){
            e.printStackTrace();
        }

        return this;
    }

    /**
     * Saves the contents of the repository.
     */
    @Override
    public void save() {
        try {
            _serialisationController.save(this);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Completes an appointment.
     *
     * @param appointment the target appointment.
     */
    public void complete(Appointment appointment) throws ObjectNotFoundException {
        if(_repository.get().contains(appointment)) {
            RequestRepositoryController controller = RequestRepositoryController.getInstance();
            Patient patient = appointment.getPatient();

            for(I_Prescription prescription : appointment.getPrescriptions()) controller.add(new PrescriptionRequest(patient, prescription));

            appointment.setCompleted(true);

        }else{
            throw new ObjectNotFoundException();
        }
    }
}
