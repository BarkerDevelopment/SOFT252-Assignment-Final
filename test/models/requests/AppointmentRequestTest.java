package models.requests;

import controllers.repository.AppointmentRepositoryController;
import exceptions.AppointmentClashException;
import exceptions.OutOfRangeException;
import models.appointments.Appointment;
import models.users.Doctor;
import models.users.Patient;
import models.users.info.Gender;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentRequestTest {
    private AppointmentRepositoryController _controller;
    private ArrayList< Doctor > _doctors;
    private ArrayList< Patient > _patients;
    private ArrayList< AppointmentRequest > _appointmentRequests;

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
        }

        _appointmentRequests = new ArrayList<>(
                Arrays.asList(
                        new AppointmentRequest(_patients.get(0), _doctors.get(2), LocalDateTime.of(2019, 10, 14, 12, 0)),
                        new AppointmentRequest(_patients.get(1), _doctors.get(1), LocalDateTime.of(2019, 1, 17, 10, 30)),
                        new AppointmentRequest(_patients.get(2), _doctors.get(1), LocalDateTime.of(2019, 2, 7, 9, 30)),
                        new AppointmentRequest(_patients.get(3), _doctors.get(0), LocalDateTime.of(2019, 7, 10, 13, 30)),
                        new AppointmentRequest(_patients.get(4), _doctors.get(0), LocalDateTime.of(2019, 7, 9, 11, 30))
                )
        );
    }

    @AfterEach
    void tearDown(){
        _controller.clear();
    }

    @Test
    @DisplayName("approveAction()")
    void approveAction() {
        for (int i = 0; i <_appointmentRequests.size() ; i++) {
            AppointmentRequest request = _appointmentRequests.get(i);
            assertDoesNotThrow(request::approveAction);
            assertEquals(i + 1, _controller.get().size());
            assertFalse(request.getPatient().getMessages().isEmpty());
        }
    }

    @Test
    @DisplayName("approveAction() - Trigger an AppointmentClashException")
    void approveAction_erroneous() {
        try {
            _controller.add(new Appointment(_appointmentRequests.get(3)));

        }catch (AppointmentClashException e){
            fail("Appointment resulted in a clash.");
        }

        Patient patient = _patients.get(2);
        AppointmentRequest clashRequest = new AppointmentRequest(patient, _doctors.get(0), LocalDateTime.of(2019, 7, 10, 13, 30));
        assertDoesNotThrow(clashRequest::approveAction);
        assertEquals(1, _controller.get().size()); // Shows an appointment hasn't been added.
        assertFalse(patient.getMessages().isEmpty());
    }

    @Test
    @DisplayName("denyAction()")
    void denyAction() {
        for (AppointmentRequest request : _appointmentRequests) {
            assertDoesNotThrow(request::denyAction);
            assertTrue(_controller.get().isEmpty()); // Shows an appointment hasn't been added.
            assertFalse(request.getPatient().getMessages().isEmpty());
        }
    }
}