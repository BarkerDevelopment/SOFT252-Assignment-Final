package project.models.drugs;

import project.exceptions.DuplicateObjectException;
import project.exceptions.IdClashException;
import project.exceptions.ObjectNotFoundException;
import project.exceptions.OutOfRangeException;
import project.models.I_Observer;
import project.models.requests.PrescriptionRequest;
import project.models.users.Patient;
import project.models.users.info.Gender;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DrugStockTest {
    private DrugStock _drugStock;
    private ArrayList< I_Observer< Integer > > _prescriptionRequests;
    private ArrayList< I_Observer< Integer > > _newPrescriptions;

    @BeforeEach
    void setUp() {
        try {
            Patient patient = new Patient("1111", "", "", Gender.MALE);
            Patient patient1 = new Patient("2222", "", "", Gender.FEMALE);

            _drugStock = new DrugStock("Paracetamol", "Painkiller", new ArrayList<>(), 100);
            I_Treatment drug = _drugStock.getDrug();

            _prescriptionRequests = new ArrayList<>(
                    Arrays.asList(
                            new PrescriptionRequest(patient, new Prescription(drug, 7, 7)),
                            new PrescriptionRequest(patient, new Prescription(drug, 10, 5)),
                            new PrescriptionRequest(patient, new Prescription(drug, 64, 16))
                    )
            );

            _newPrescriptions = new ArrayList<>(
                    Arrays.asList(
                            new PrescriptionRequest(patient1, new Prescription(drug, 16, 8)),
                            new PrescriptionRequest(patient1, new Prescription(drug, 8, 2)),
                            new PrescriptionRequest(patient1, new Prescription(drug, 100, 50))
                    )
            );

            for (I_Observer< Integer > p : _prescriptionRequests){
                _drugStock.subscribe(p);
            }

        }catch (OutOfRangeException e){
            fail("Added a user with ID greater than the ID length.");

        }catch (DuplicateObjectException e){
            fail("Subscriber already exists in observers.");

        } catch (ObjectNotFoundException e) {
            fail("Drug not found in repository.");
        } catch (IdClashException e){
            fail("Added a user with an ID that already exists.");
        }
    }

    @AfterEach
    void tearDown() {
        _prescriptionRequests.clear();
    }

    @Test
    @DisplayName("subscribe(I_Observer< ? > o)")
    void subscribe() {
        _newPrescriptions.forEach(o -> {
                    assertDoesNotThrow(() -> _drugStock.subscribe(o));
                }
        );

        ArrayList< I_Observer< Integer > > expected = new ArrayList<>(_prescriptionRequests);
        expected.addAll(_newPrescriptions);
        ArrayList< I_Observer< Integer > > actual = _drugStock.getObservers();

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    @DisplayName("subscribe(I_Observer< ? > o) - Observers empty.")
    void subscribe_boundary() {
        _drugStock.getObservers().clear();
        _prescriptionRequests.forEach(o -> {
            assertDoesNotThrow(() -> _drugStock.subscribe(o));
            }
        );

        ArrayList< I_Observer< Integer > > expected = _prescriptionRequests;
        ArrayList< I_Observer< Integer > > actual = _drugStock.getObservers();

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    @DisplayName("subscribe(I_Observer< ? > o) - Observer already in observers.")
    void subscribe_erroneous() {
        _prescriptionRequests.forEach(o -> {
            assertThrows(DuplicateObjectException.class, ()->_drugStock.subscribe(o));
            }
        );

        ArrayList< I_Observer< Integer > > expected = _prescriptionRequests;
        ArrayList< I_Observer< Integer > > actual = _drugStock.getObservers();

        assertArrayEquals(expected.toArray(), actual.toArray());

    }

    @Test
    @DisplayName("unsubscribe(I_Observer< ? > o)")
    void unsubscribe() {
        _prescriptionRequests.forEach(o -> {
                    assertDoesNotThrow(() -> _drugStock.unsubscribe(o));
                    assertFalse(_drugStock.getObservers().contains(o));
                }
        );
    }

    @Test
    @DisplayName("unsubscribe(I_Observer< ? > o) - Observers empty.")
    void unsubscribe_boundary() {
        _drugStock.getObservers().clear();
        _prescriptionRequests.forEach(o -> assertThrows(ObjectNotFoundException.class, () -> _drugStock.unsubscribe(o)));
        assertEquals(0, _drugStock.getObservers().size());
    }

    @Test
    @DisplayName("unsubscribe(I_Observer< ? > o) - Observer not in observers")
    void unsubscribe_erroneous() {
        _newPrescriptions.forEach( o ->{
                    assertThrows(ObjectNotFoundException.class, () -> _drugStock.unsubscribe(o));
                }
        );
    }

    @Test
    @DisplayName("updateObservers()")
    void updateObservers() {
        _drugStock.updateObservers();

        int expected = _drugStock.getStock();
        _prescriptionRequests.forEach(o -> assertEquals(expected, ((PrescriptionRequest)o).getDrugStock()));
    }

    @Test
    @DisplayName("updateObservers() - Observers empty.")
    void updateObservers_boundary() {
        _drugStock.getObservers().clear();
        _drugStock.updateObservers();

        _prescriptionRequests.forEach(o -> assertEquals(0, ((PrescriptionRequest)o).getDrugStock()));
    }
}