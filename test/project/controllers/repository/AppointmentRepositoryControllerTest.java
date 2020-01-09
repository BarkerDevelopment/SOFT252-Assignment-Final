package project.controllers.repository;

import project.exceptions.AppointmentClashException;
import project.exceptions.IdClashException;
import project.exceptions.ObjectNotFoundException;
import project.exceptions.OutOfRangeException;
import project.models.appointments.Appointment;
import project.models.drugs.DrugStock;
import project.models.drugs.I_Prescription;
import project.models.drugs.Prescription;
import project.models.requests.PrescriptionRequest;
import project.models.requests.Request;
import project.models.requests.RequestType;
import project.models.users.Doctor;
import project.models.users.Patient;
import project.models.users.info.Gender;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentRepositoryControllerTest {
    private AppointmentRepositoryController _controller;
    private ArrayList< Patient > _patients;
    private ArrayList< Doctor > _doctors;
    private ArrayList< Appointment > _appointments;
    private ArrayList< Appointment > _newAppointments;
    private ArrayList< Appointment > _classAppointments;


    /**
     * All names generated for the users were generated from the sites:
     * - https://www.fantasynamegenerators.com/warhammer-40k-space-marine-names.php
     * - https://www.fantasynamegenerators.com/warhammer-40k-sisters-of-battle-names.php
     */
    @BeforeEach
    void setUp() {
        _controller = AppointmentRepositoryController.getInstance();

        try {
            _doctors = new ArrayList<>(
                    Arrays.asList(
                            new Doctor("4891", "Raldun", "Deathseeker"),
                            new Doctor("5102", "Kvyrll", "Ironhanded"),
                            new Doctor("5024", "Nectohr", "Elgon")
                    )
            );

            _patients = new ArrayList<>(
                    Arrays.asList(
                            new Patient("9012", "Castiel", "Fatus", Gender.MALE),
                            new Patient("1164", "Gremenes", "Mordatus", Gender.MALE),
                            new Patient("3462", "Aegot", "Dragonmane", Gender.MALE),
                            new Patient("5352", "Sabrella", "Bles", Gender.FEMALE),
                            new Patient("1902", "Dissonya", "Inviel", Gender.FEMALE)
                    )
            );

        }catch (OutOfRangeException e){
            fail("Added a user with ID greater than the ID length.");

        } catch (IdClashException e){
            fail("Added a user with an ID that already exists.");
        }

        try{
            _appointments = new ArrayList<>(
                    Arrays.asList(
                            new Appointment(_patients.get(2), _doctors.get(2), LocalDateTime.of(2019, 10, 14, 12, 0)),
                            new Appointment(_patients.get(3), _doctors.get(1), LocalDateTime.of(2019, 1, 17, 10, 30)),
                            new Appointment(_patients.get(1), _doctors.get(1), LocalDateTime.of(2019, 2, 7, 9, 30)),
                            new Appointment(_patients.get(1), _doctors.get(0), LocalDateTime.of(2019, 7, 10, 13, 30)),
                            new Appointment(_patients.get(4), _doctors.get(1), LocalDateTime.of(2019, 7, 10, 13, 30))
                    )
            );

            _newAppointments = new ArrayList<>(
                    Arrays.asList(
                            // Same participants, different time
                            new Appointment(_patients.get(2), _doctors.get(2), LocalDateTime.of(2019, 7, 23, 10, 15)),
                            // Same doctor, different patient, different time.
                            new Appointment(_patients.get(3), _doctors.get(1), LocalDateTime.of(2019, 1, 17, 10, 0)),
                            // Different doctor, same patient, different time.
                            new Appointment(_patients.get(1), _doctors.get(0), LocalDateTime.of(2019, 5, 10, 14, 45))
                    )
            );

            /*
             * Clash appointments - these appointments will result in a clash with appointments 3 & 4
             * and should raise an error.
             */
            _classAppointments = new ArrayList<>(
                    Arrays.asList(
                            // Doctor clash.
                            new Appointment(_patients.get(2), _doctors.get(0), LocalDateTime.of(2019, 7, 10, 13, 30)),
                            // Patient clash.
                            new Appointment(_patients.get(4), _doctors.get(2), LocalDateTime.of(2019, 7, 10, 13, 30)),
                            // Both clash.
                            new Appointment(_patients.get(4), _doctors.get(0), LocalDateTime.of(2019, 7, 10, 13, 30))
                    )
            );

            _controller.add(_appointments);

        }catch (AppointmentClashException e){
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        _controller.clear();
    }

    @Test
    @DisplayName("get()")
    void get() {
        ArrayList< Appointment > expected = new ArrayList<>(_appointments);
        ArrayList< Appointment > actual = _controller.get();

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    @DisplayName("get() - Empty repository.")
    void get_boundary() {
        _controller.clear();

        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        ArrayList< Appointment > expected = new ArrayList<>();
        ArrayList< Appointment > actual = _controller.get();

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    @DisplayName("get( I_AppointmentParticipant participant) - Patient passed.")
    void get_participant0() {
        for (Patient patient : _patients){
            ArrayList< Appointment > expected = new ArrayList<>();
            _appointments.forEach(appointment -> { if(appointment.getPatient().equals(patient)) expected.add(appointment); });
            ArrayList< Appointment > actual = _controller.get(patient);

            assertArrayEquals(expected.toArray(), actual.toArray());
        }
    }

    @Test
    @DisplayName("get( I_AppointmentParticipant participant) - Doctor passed.")
    void get_participant1() {
        for (Doctor doctor : _doctors){
            ArrayList< Appointment > expected = new ArrayList<>();
            _appointments.forEach(appointment -> { if(appointment.getDoctor().equals(doctor)) expected.add(appointment); });
            ArrayList< Appointment > actual = _controller.get(doctor);

            assertArrayEquals(expected.toArray(), actual.toArray());
        }
    }

    @Test
    @DisplayName("get( I_AppointmentParticipant participant) - Empty repository, Patient passed.")
    void get_participant_boundary0() {
        _controller.clear();

        for (Patient patient : _patients){
            ArrayList< Appointment > expected = new ArrayList<>();
            ArrayList< Appointment > actual = _controller.get(patient);

            assertArrayEquals(expected.toArray(), actual.toArray());
        }
    }

    @Test
    @DisplayName("get( I_AppointmentParticipant participant) - Empty repository, Doctor passed.")
    void get_participant_boundary1() {
        _controller.clear();

        for (Doctor doctor : _doctors){
            ArrayList< Appointment > expected = new ArrayList<>();
            ArrayList< Appointment > actual = _controller.get(doctor);

            assertArrayEquals(expected.toArray(), actual.toArray());
        }
    }

    @Test
    @DisplayName("get( I_AppointmentParticipant participant) - Erroneous patient passed.")
    void get_participant_erroneous0() {
        try{
            Patient patient = new Patient("4240","Chriael","", Gender.FEMALE);

            ArrayList< Appointment > expected = new ArrayList<>();
            ArrayList< Appointment > actual = _controller.get(patient);

            assertArrayEquals(expected.toArray(), actual.toArray());

        }catch (OutOfRangeException e){
            fail("Added a user with ID greater than the ID length.");
        } catch (IdClashException e){
            fail("Added a user with an ID that already exists.");
        }
    }

    @Test
    @DisplayName("get( I_AppointmentParticipant participant) - Erroneous doctor passed.")
    void get_participant_erroneous1() {
        try {
            Doctor doctor = new Doctor("8392", "Ashoc", "Bloodhowl");
            ArrayList< Appointment > expected = new ArrayList<>();
            ArrayList< Appointment > actual = _controller.get(doctor);

            assertArrayEquals(expected.toArray(), actual.toArray());

        }catch (OutOfRangeException e){
            fail("Added a user with ID greater than the ID length.");
        } catch (IdClashException e){
            fail("Added a user with an ID that already exists.");
        }
    }

    @Test
    @DisplayName("get(LocalDate date)")
    void get_date() {
        ArrayList<Appointment> expected = new ArrayList<>(Arrays.asList(_appointments.get(3), _appointments.get(4)));
        ArrayList<Appointment> actual = _controller.get(LocalDate.of(2019, 7, 10));

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    @DisplayName("get(LocalDate date) - Empty repository.")
    void get_date_boundary() {
        _controller.clear();

        ArrayList<Appointment> expected = new ArrayList<>();
        ArrayList<Appointment> actual = _controller.get(LocalDate.now());

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    @DisplayName("get(LocalDate date) - Erroneous dates.")
    void get_date_erroneous() {
        ArrayList<Appointment> expected = new ArrayList<>();
        ArrayList<Appointment> actual = _controller.get(LocalDate.now());

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    @DisplayName("add(Appointment item)")
    void add() {
        _newAppointments.forEach(appointment -> {
                assertDoesNotThrow((() ->_controller.add(appointment)));
                assertTrue(_controller.get().contains(appointment));
            }
        );
    }

    @Test
    @DisplayName("add(Appointment item) - Empty repository.")
    void add_boundary() {
        _controller.clear();

        _newAppointments.forEach(appointment -> {
                    assertDoesNotThrow((() ->_controller.add(appointment)));
                    assertTrue(_controller.get().contains(appointment));
                }
        );
    }

    @Test
    @DisplayName("add(Appointment item) - Appointment passed already exists.")
    void add_erroneous0() {
        _appointments.forEach(appointment -> assertThrows(AppointmentClashException.class, () ->_controller.add(appointment)));
    }

    @Test
    @DisplayName("add(Appointment item) - Appointment clashes with an existing one.")
    void add_erroneous1() {
        _classAppointments.forEach(appointment -> assertThrows(AppointmentClashException.class, () ->_controller.add(appointment)));
    }

    @Test
    @DisplayName("add(ArrayList< Appointment > items)")
    void add_all() {
        assertDoesNotThrow(()-> _controller.add(_newAppointments));
        _newAppointments.forEach(appointment -> assertTrue( _controller.get().contains(appointment) ));
    }

    @Test
    @DisplayName("add(ArrayList< Appointment > items) - Empty repository.")
    void add_all_boundary() {
        _controller.clear();

        assertDoesNotThrow(()-> _controller.add(_newAppointments));
        _newAppointments.forEach(appointment -> assertTrue( _controller.get().contains(appointment) ));
    }


    @Test
    @DisplayName("add(ArrayList< Appointment > items) - Appointment passed already exists.")
    void add_all_erroneous() {
        assertThrows(AppointmentClashException.class, ()-> _controller.add(_appointments));
    }

    @Test
    @DisplayName("remove(Appointment item)")
    void remove() {
        _appointments.forEach(appointment -> {
                    assertDoesNotThrow((() ->_controller.remove(appointment)));
                    assertFalse(_controller.get().contains(appointment));
                }
        );
    }

    @Test
    @DisplayName("remove(Appointment item) - Empty repository.")
    void remove_boundary() {
        _controller.clear();

        _newAppointments.forEach(appointment -> assertThrows(ObjectNotFoundException.class, () ->_controller.remove(appointment)));
    }

    @Test
    @DisplayName("remove(Appointment item) - Appointment passed doesn't exist in repository.")
    void remove_erroneous() {
        _newAppointments.forEach(appointment -> assertThrows(ObjectNotFoundException.class, () ->_controller.remove(appointment)));
    }

    @Test
    @DisplayName("remove(ArrayList< Appointment > items)")
    void remove_all() {
        assertDoesNotThrow(()-> _controller.remove(_appointments));
        _appointments.forEach(appointment -> assertFalse( _controller.get().contains(appointment) ));
    }

    @Test
    @DisplayName("remove(ArrayList< Appointment > items) - Empty repository.")
    void remove_all_boundary() {
        _controller.clear();

        assertThrows(ObjectNotFoundException.class, ()-> _controller.remove(_newAppointments));
    }

    @Test
    @DisplayName("remove(ArrayList< Appointment > items) - Appointment passed doesn't exist in repository.")
    void remove_all_erroneous() {
        assertThrows(ObjectNotFoundException.class, ()-> _controller.remove(_newAppointments));
    }

    @Test
    @DisplayName("complete(Appointment appointment)")
    void complete() {
        RequestRepositoryController requestController = RequestRepositoryController.getInstance();
        Appointment appointment = _appointments.get(0);

        ArrayList< DrugStock > drugStocks = new ArrayList<>(
                Arrays.asList(
                        new DrugStock("Morphine", "Painkiller", new ArrayList<>(Arrays.asList("Hallucinations")), 25),
                        new DrugStock("Amoxicillin", "Antibiotic", new ArrayList<>(Arrays.asList("Nausea","Rash")), 200)
                )
        );

        appointment.setPrescriptions(new ArrayList<>(
                Arrays.asList(
                        new Prescription(drugStocks.get(0).getDrug(), 14, 7),
                        new Prescription(drugStocks.get(1).getDrug(), 28, 3)
                )
            )
        );

        assertDoesNotThrow(()->_controller.complete(appointment));

        ArrayList< Request > prescriptionRequests = requestController.get(RequestType.PRESCRIPTION);
        ArrayList< I_Prescription > prescriptionsInRequests = new ArrayList<>(
                prescriptionRequests.stream().map(
                        request -> ( (PrescriptionRequest) request).getPrescription()
                ).collect(Collectors.toList())
        );

        appointment.getPrescriptions().forEach( prescription -> assertTrue(prescriptionsInRequests.contains(prescription)));
    }

    @Test
    @DisplayName("complete(Appointment appointment) - Appointment passed doesn't exist in repository.")
    void complete_erroneous() {
        RequestRepositoryController requestController = RequestRepositoryController.getInstance();
        Appointment appointment = _newAppointments.get(0);

        ArrayList< DrugStock > drugStocks = new ArrayList<>(
                Arrays.asList(
                        new DrugStock("Morphine", "Painkiller", new ArrayList<>(Arrays.asList("Hallucinations")), 25),
                        new DrugStock("Amoxicillin", "Antibiotic", new ArrayList<>(Arrays.asList("Nausea","Rash")), 200)
                )
        );

        appointment.setPrescriptions(new ArrayList<>(
                        Arrays.asList(
                                new Prescription(drugStocks.get(0).getDrug(), 14, 7),
                                new Prescription(drugStocks.get(1).getDrug(), 28, 3)
                        )
                )
        );

        assertThrows(ObjectNotFoundException.class, ()->_controller.complete(appointment));
        assertTrue(requestController.get(RequestType.PRESCRIPTION).isEmpty());
    }
}