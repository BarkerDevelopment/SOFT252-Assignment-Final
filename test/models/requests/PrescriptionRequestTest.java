package models.requests;

import controllers.repository.DrugRepositoryController;
import exceptions.ObjectNotFoundException;
import exceptions.OutOfRangeException;
import exceptions.StockLevelException;
import models.drugs.DrugStock;
import models.drugs.I_Treatment;
import models.drugs.Prescription;
import models.users.Doctor;
import models.users.Patient;
import models.users.info.Gender;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PrescriptionRequestTest {
    private DrugRepositoryController _controller;
    private Patient _patient;
    private ArrayList< PrescriptionRequest > _prescriptionRequests;

    @BeforeEach
    void setUp() {
        _controller = DrugRepositoryController.getInstance();

        try {
            _patient = new Patient("9382", "Centulochus", "Corabius", Gender.MALE);

            _controller.add(new ArrayList<>(
                    Arrays.asList(
                            new DrugStock("Ibuprofen", "Anti-Inflammatory", new ArrayList<>(), 100),
                            new DrugStock("Paracetamol", "Painkiller", new ArrayList<>(), 12),
                            new DrugStock("Morphine",  "Painkiller", new ArrayList<>(), 50)
                    )
            ));

            _prescriptionRequests = new ArrayList<>(
                    Arrays.asList(
                            new PrescriptionRequest(_patient, new Prescription( _controller.get().get(0).getDrug(), 15, 5)),
                            new PrescriptionRequest(_patient, new Prescription( _controller.get().get(1).getDrug(), 12, 12)),
                            new PrescriptionRequest(_patient, new Prescription( _controller.get().get(2).getDrug(), 20, 10) )
                    )
            );

        }catch (OutOfRangeException e) {
            fail("Added a user with ID greater than the ID length.");

        }catch (ObjectNotFoundException e){
            fail("Drug not found in DrugRepositoryController.");
        }
    }

    @AfterEach
    void tearDown() {
        _controller.clear();
    }

    @Test
    @DisplayName("approveAction()")
    void approveAction() {
        for (int i = 0; i < _prescriptionRequests.size(); i++) {
            PrescriptionRequest request = _prescriptionRequests.get(i);

            try {
                request.approveAction();
                assertTrue(_patient.getPrescriptions().contains(request.getPrescription()));

            }catch (ObjectNotFoundException e){
                fail("Prescription drug does not exist in repository.");

            }catch (StockLevelException e){
                fail("Insufficient stock for prescription.");
            }
        }
    }

    @Test
    @DisplayName("approveAction() - Trigger a StockLevelException")
    void approveAction_erroneous0() {
        try {
            PrescriptionRequest request = new PrescriptionRequest(_patient, new Prescription(_controller.get().get(2).getDrug(), 55, 10));
            assertThrows(StockLevelException.class, request::approveAction);

        }catch (ObjectNotFoundException e){
            fail("Prescription drug does not exist in repository.");
        }
    }

    @Test
    @DisplayName("denyAction()")
    void denyAction() {
        for (int i = 0; i < _prescriptionRequests.size(); i++) {
            PrescriptionRequest request = _prescriptionRequests.get(i);
            request.denyAction();
            assertEquals(i + 1, _patient.getMessages().size());
        }
    }
}