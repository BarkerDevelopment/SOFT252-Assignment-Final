package project.controllers.repository;

import project.exceptions.DuplicateObjectException;
import project.exceptions.IdClashException;
import project.exceptions.ObjectNotFoundException;
import project.exceptions.OutOfRangeException;
import project.models.drugs.DrugStock;
import project.models.drugs.Prescription;
import project.models.requests.*;
import project.models.users.*;
import project.models.users.info.Address;
import project.models.users.info.Gender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.*;

class RequestRepositoryControllerTest {
    private RequestRepositoryController _controller;
    private Patient _patient;
    private Patient _altPatient;
    private Doctor _doctor;
    private Doctor _altDoctor;
    private EnumMap< RequestType, ArrayList< Request > > _requests;
    private ArrayList< Request > _newRequests;

    /**
     * All names generated for the users were generated from the sites:
     * - https://www.fantasynamegenerators.com/warhammer-40k-space-marine-names.php
     * - https://www.fantasynamegenerators.com/warhammer-40k-sisters-of-battle-names.php
     */
    @BeforeEach
    void setUp() {
        _controller = RequestRepositoryController.getInstance();
        try {
            _patient = new Patient("1111", "Reger", "Coghair", Gender.MALE);
            _altPatient = new Patient("2829", "Letima", "Ishoya", Gender.FEMALE);
            _doctor = new Doctor("9272","Oloya", "Eston");
            _altDoctor = new Doctor("2495", "K'nid", "Ferriel");

            UserRepositoryController.getInstance().add(_patient);

        }catch (OutOfRangeException e){
            fail("Added a user with ID greater than the ID length.");
        } catch (IdClashException e){
            fail("Added a user with an ID that already exists.");
        }

        DrugStock drugStock = new DrugStock("Paracetamol", "Painkiller", new ArrayList<>(), 100);
        DrugRepositoryController.getInstance().add(drugStock);

        // Create a list of users.
        _requests = new EnumMap< >(RequestType.class);
        _requests.put(RequestType.ACCOUNT_CREATION, new ArrayList<>(
                Arrays.asList(
                       new AccountCreationRequest("Reger", "Coghair", new Address(), "", LocalDate.now(), Gender.MALE)
                )
        ));

        _requests.put(RequestType.ACCOUNT_TERMINATION, new ArrayList<>(
                Arrays.asList(
                        new AccountTerminationRequest(_patient)
                )
        ));

        _requests.put(RequestType.APPOINTMENT, new ArrayList<>(
                Arrays.asList(
                        new AppointmentRequest(_patient, _doctor, LocalDateTime.now())
                )
        ));

        _requests.put(RequestType.NEW_DRUG, new ArrayList<>(
                Arrays.asList(
                        new DrugRequest(_doctor, "Ibuprofen")
                )
        ));

        _newRequests = new ArrayList<>(
                Arrays.asList(
                        new AccountCreationRequest("Julenta", "Araba", new Address(), "", LocalDate.now(), Gender.FEMALE),
                        new AccountTerminationRequest(_altPatient),
                        new AppointmentRequest(_altPatient, _altDoctor, LocalDateTime.now()),
                        new DrugRequest(_altDoctor, "Morphine")
                )
        );

        try {
            _requests.put(RequestType.PRESCRIPTION, new ArrayList<>());
            _requests.get(RequestType.PRESCRIPTION).add(new PrescriptionRequest(_patient, new Prescription(drugStock.getDrug(), 20, 10)));

            _newRequests.add(new PrescriptionRequest(_altPatient, new Prescription(drugStock.getDrug(), 20, 10)));

        }catch (ObjectNotFoundException e) {
            fail("Drug not found in repository.");
        }


        _requests.forEach((type, requests) -> {
            try {
                _controller.add(requests);

            }catch (DuplicateObjectException e){
                fail("Added user already exists in the repository.");

            }
        });
    }

    @AfterEach
    void tearDown() {
        _controller.clear();
        DrugRepositoryController.getInstance().clear();
    }

    @Test
    @DisplayName("get()")
    void get() {
        ArrayList< Request > expected = new ArrayList<>();
        _requests.forEach((typeRole, requests) -> expected.addAll(requests));

        ArrayList< Request > actual = _controller.get();

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    @DisplayName("get() - Empty repository.")
    void get_boundary() {
        _controller.clear();

        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        ArrayList< Request > expected = new ArrayList<>();
        ArrayList< Request > actual = _controller.get();

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    @DisplayName("add(Request item) - AccountCreationRequest object.")
    void add0() {
        Request newRequest = new AccountCreationRequest("Reger", "Coghair", new Address(), "", LocalDate.now(), Gender.MALE);
        assertDoesNotThrow(()->_controller.add(newRequest));

        assertTrue(_controller.get().contains(newRequest));
    }

    @Test
    @DisplayName("add(Request item) - AccountTerminationRequest object.")
    void add1() {
        Request newRequest = new AccountTerminationRequest(_patient);
        assertDoesNotThrow(()->_controller.add(newRequest));

        assertTrue(_controller.get().contains(newRequest));
    }

    @Test
    @DisplayName("add(Request item) - AppointmentRequest object.")
    void add2() {
        Request newRequest = new AppointmentRequest(_patient, _doctor, LocalDateTime.now());
        assertDoesNotThrow(()->_controller.add(newRequest));

        assertTrue(_controller.get().contains(newRequest));
    }

    @Test
    @DisplayName("add(Request item) - DrugRequest object.")
    void add3() {
        Request newRequest = new DrugRequest(_doctor, "Paracetamol");
        assertDoesNotThrow(()->_controller.add(newRequest));

        assertTrue(_controller.get().contains(newRequest));
    }

    @Test
    @DisplayName("add(Request item) - Prescription object.")
    void add4() {
        DrugStock drugStock = new DrugStock("Amoxicillin", "Antibiotic", new ArrayList<>(Arrays.asList("Nausea","Rash")), 200);
        DrugRepositoryController.getInstance().add(drugStock);
        Prescription prescription = new Prescription(drugStock.getDrug(), 20, 10);

        try {
            Request newRequest = new PrescriptionRequest(_patient, prescription);
            assertDoesNotThrow(()->_controller.add(newRequest));

            assertTrue(_controller.get().contains(newRequest));

        }catch (ObjectNotFoundException e){
            fail("Drug not found in repository.");
        }
    }

    @Test
    @DisplayName("add(Request item) - Empty repository.")
    void add_boundary() {
        _controller.clear();
        _requests.forEach((type, requests) -> {
                    for (Request request : requests) {
                        assertDoesNotThrow(() -> _controller.add(request));
                        assertTrue(_controller.get().contains(request));
                    }
                }
        );
    }

    @Test
    @DisplayName("add(Request item) - Request passed already exists.")
    void add_erroneous0() {
        _requests.forEach((type, requests) -> {
                    for (Request request : requests) assertThrows(DuplicateObjectException.class, () -> _controller.add(request));
                }
        );
    }

    @Test
    @DisplayName("add(ArrayList< Request > items)")
    void add_all() {
        assertDoesNotThrow(()->_controller.add(_newRequests));
    }

    @Test
    @DisplayName("add(ArrayList< Request > items) -  Empty repository.")
    void add_all_boundary() {
        _controller.clear();
        _requests.forEach((type, requests) ->  {
                    assertDoesNotThrow(()->_controller.add(requests));
                    for (Request request : requests) assertTrue(_controller.get().contains(request));
                }
        );
    }

    @Test
    @DisplayName("add(ArrayList< Request > items) - Request in ArrayList already exists.")
    void add_all_erroneous() {
        _requests.forEach((type, requests) ->  assertThrows(DuplicateObjectException.class, ()->_controller.add(requests)));
    }

    @Test
    @DisplayName("remove(Request item)")
    void remove() {
        _requests.forEach((type, requests) -> {
                    for (Request request : requests) {
                        assertDoesNotThrow(() -> _controller.remove(request));
                        assertFalse(_controller.get().contains(request));
                    }
                }
        );
    }

    @Test
    @DisplayName("remove(Request item) - Empty repository.")
    void remove_boundary() {
        _controller.clear();

        _requests.forEach((type, requests) -> {
                    for (Request request : requests) {
                        assertThrows(ObjectNotFoundException.class, () -> _controller.remove(request));
                    }
                }
        );
    }

    @Test
    @DisplayName("remove(Request item) - Request does not exist in repository.")
    void remove_erroneous0() {
        _newRequests.forEach(request -> assertThrows(ObjectNotFoundException.class, () -> _controller.remove(request)));
    }

    @Test
    @DisplayName("remove(ArrayList< Request > items)")
    void remove_all() {
        _requests.forEach((type, requests) -> {
                    assertDoesNotThrow(() -> _controller.remove(requests));
                    requests.forEach( request -> assertFalse(_controller.get().contains(request)));
                }
        );
    }

    @Test
    @DisplayName("remove(ArrayList< Request > items) - Empty repository.")
    void remove_all_boundary() {
        _controller.clear();
        _requests.forEach((type, requests) -> assertThrows(ObjectNotFoundException.class, () -> _controller.remove(requests)));
    }

    @Test
    @DisplayName("remove(ArrayList< Request > items) - Request does not exist in repository.")
    void remove_all_erroneous0() {
        assertThrows(ObjectNotFoundException.class, () -> _controller.remove(_newRequests));
    }
}