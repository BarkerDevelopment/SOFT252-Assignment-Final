package controllers.repository;

import exceptions.DuplicateObjectException;
import exceptions.ObjectNotFoundException;
import exceptions.StockLevelException;
import models.drugs.DrugStock;
import models.drugs.I_Treatment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DrugRepositoryControllerTest {
    private DrugRepositoryController _controller;
    private ArrayList< DrugStock > _drugStocks;
    private ArrayList< DrugStock > _newDrugStocks;

    @BeforeEach
    void setUp() {
        _controller = DrugRepositoryController.getInstance();
        _drugStocks = new ArrayList<>(
                Arrays.asList(
                        new DrugStock("Paracetamol", "Painkiller", new ArrayList<>(), 100),
                        new DrugStock("Morphine", "Painkiller", new ArrayList<>(Arrays.asList("Hallucinations")), 25),
                        new DrugStock("Amoxicillin", "Antibiotic", new ArrayList<>(Arrays.asList("Nausea","Rash")), 200)
                )
        );

        _newDrugStocks = new ArrayList<>(
                Arrays.asList(
                        new DrugStock("Diazepam", "Vallium", new ArrayList<>(), 50),
                        new DrugStock("Ibuprofen", "Anti-Inflammatory", new ArrayList<>(Arrays.asList("Stomach ulcers")), 500)
                )
        );

        _controller.add(_drugStocks);
    }

    @AfterEach
    void tearDown() {
        _controller.clear();
    }

    void get() {
        ArrayList< DrugStock > expected = new ArrayList<>(_drugStocks);

        ArrayList< DrugStock > actual = _controller.get();

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    @DisplayName("get() - Empty repository.")
    void get_boundary() {
        _controller.clear();

        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        ArrayList< DrugStock > expected = new ArrayList<>();
        ArrayList< DrugStock > actual = _controller.get();

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    @DisplayName("get(I_Treatment identifier)")
    void get_identifier() {
        _drugStocks.forEach( expected -> {
                    DrugStock actual = null;
                    try {
                        actual = _controller.get(expected.getUnique());
                        assertEquals(expected, actual);

                    } catch (ObjectNotFoundException e) {
                        fail("Queried an ID that doesn't exist. ");
                    }
                }
        );
    }

    @Test
    @DisplayName("get(I_Treatment identifier) - Empty repository.")
    void get_identifier_boundary() {
        _controller.clear();

        _drugStocks.forEach( drugStock -> assertThrows(ObjectNotFoundException.class, () -> _controller.get(drugStock.getUnique())));
    }

    @Test
    @DisplayName("get(I_Treatment identifier) - Erroneous identifier passed.")
    void get_identifier_erroneous() {
        DrugStock drugStock = new DrugStock("Ibuprofen", "Anti-Inflammatory", new ArrayList<>(), 10);
        assertThrows(ObjectNotFoundException.class, () -> _controller.get(drugStock.getUnique()));
    }

    @Test
    @DisplayName("contains(I_Treatment identifier)")
    void contains() {
        _drugStocks.forEach(drugStock ->  assertTrue(_controller.contains(drugStock.getUnique())));
    }

    @Test
    @DisplayName("contains(I_Treatment identifier) - Empty repository.")
    void contains_boundary() {
        _controller.clear();
        _drugStocks.forEach(drugStock ->  assertFalse(_controller.contains(drugStock.getUnique())));
    }

    @Test
    @DisplayName("contains(I_Treatment identifier) - Erroneous identifier passed.")
    void contains_erroneous() {
        DrugStock drugStock = new DrugStock("Ibuprofen", "Anti-Inflammatory", new ArrayList<>(), 10);
        assertFalse(_controller.contains(drugStock.getUnique()));
    }

    @Test
    @DisplayName("add(DrugStock item)")
    void add() {
        _newDrugStocks.forEach(drugStock -> {
                assertDoesNotThrow(()-> _controller.add(drugStock));
                assertTrue(_controller.get().contains(drugStock));
            }
        );
    }

    @Test
    @DisplayName("add(DrugStock item) - Empty repository.")
    void add_boundary() {
        _controller.clear();

        _newDrugStocks.forEach(drugStock -> {
                    assertDoesNotThrow(()-> _controller.add(drugStock));
                    assertTrue(_controller.get().contains(drugStock));
                }
        );
    }

    @Test
    @DisplayName("add(DrugStock item) - DrugStock passed already exists.")
    void add_erroneous() {
        _drugStocks.forEach(drugStock -> assertThrows(DuplicateObjectException.class, ()-> _controller.add(drugStock)));
    }

    @Test
    @DisplayName("add(ArrayList< DrugStock > items)")
    void add_all() {
        assertDoesNotThrow(()-> _controller.add(_newDrugStocks));
        _newDrugStocks.forEach(drugStock -> assertTrue( _controller.get().contains(drugStock) ));
    }

    @Test
    @DisplayName("add(ArrayList< DrugStock > items) - Empty repository.")
    void add_all_boundary() {
        _controller.clear();

        assertDoesNotThrow(()-> _controller.add(_drugStocks));
        _drugStocks.forEach(drugStock -> assertTrue( _controller.get().contains(drugStock) ));
    }

    @Test
    @DisplayName("add(ArrayList< DrugStock > items) - DrugStock passed already exists.")
    void add_all_erroneous() {
        assertThrows(DuplicateObjectException.class, ()-> _controller.add(_drugStocks));
    }

    @Test
    @DisplayName("updateStock(I_Treatment drug, int stockChange) - Negative stockChange passed.")
    void updateStock0() {
        DrugStock drugStock = _drugStocks.get(0);
        I_Treatment drug = drugStock.getDrug();
        int initialStock = drugStock.getStock();
        int stockChange = -10;

        assertDoesNotThrow(() -> _controller.updateStock(drug, stockChange));

        try {
            int expected = initialStock + stockChange;
            int actual = _controller.get(drug).getStock();
            assertEquals(expected, actual);

        }catch (ObjectNotFoundException e){
            fail("Drug does not exist.");
        }
    }

    @Test
    @DisplayName("updateStock(I_Treatment drug, int stockChange) - Positive stockChange passed.")
    void updateStock1() {
        DrugStock drugStock = _drugStocks.get(0);
        I_Treatment drug = drugStock.getDrug();
        int initialStock = drugStock.getStock();
        int stockChange = 10;

        assertDoesNotThrow(() -> _controller.updateStock(drug, stockChange));

        try {
            int expected = initialStock + stockChange;
            int actual = _controller.get(drug).getStock();
            assertEquals(expected, actual);

        }catch (ObjectNotFoundException e){
            fail("Drug does not exist.");
        }
    }

    @Test
    @DisplayName("updateStock(I_Treatment drug, int stockChange) - Boundary stockChange passed.")
    void updateStock_boundary0() {
        DrugStock drugStock = _drugStocks.get(0);
        I_Treatment drug = drugStock.getDrug();
        int initialStock = drugStock.getStock();
        int stockChange = -100;

        assertDoesNotThrow(() -> _controller.updateStock(drug, stockChange));

        try {
            int expected = initialStock + stockChange;
            int actual = _controller.get(drug).getStock();
            assertEquals(expected, actual);

        }catch (ObjectNotFoundException e){
            fail("Drug does not exist.");
        }
    }

    @Test
    @DisplayName("updateStock(I_Treatment drug, int stockChange) - Erroneous drug passed.")
    void updateStock_erroneous0() {
        DrugStock drugStock = _newDrugStocks.get(0);
        I_Treatment drug = drugStock.getDrug();
        int stockChange = -10;

        assertThrows(ObjectNotFoundException.class, () -> _controller.updateStock(drug, stockChange));
    }

    @Test
    @DisplayName("updateStock(I_Treatment drug, int stockChange) - Erroneous stockChange passed.")
    void updateStock_erroneous1() {
        DrugStock drugStock = _drugStocks.get(0);
        I_Treatment drug = drugStock.getDrug();
        int stockChange = -200;

        assertThrows(StockLevelException.class, () -> _controller.updateStock(drug, stockChange));
    }

    @Test
    @DisplayName("remove(DrugStock item)")
    void remove() {
        _drugStocks.forEach(drugStock -> {
                    assertDoesNotThrow(()-> _controller.remove(drugStock));
                    assertFalse(_controller.get().contains(drugStock));
                }
        );
    }

    @Test
    @DisplayName("remove(DrugStock item) - Empty repository.")
    void remove_boundary() {
        _controller.clear();

        _drugStocks.forEach(drugStock -> assertThrows(ObjectNotFoundException.class, ()-> _controller.remove(drugStock)));
    }

    @Test
    @DisplayName("remove(DrugStock item) - DrugStock passed doesn't exist in repository.")
    void remove_erroneous() {
        _newDrugStocks.forEach(drugStock -> assertThrows(ObjectNotFoundException.class, ()-> _controller.remove(drugStock)));
    }

    @Test
    @DisplayName("remove(ArrayList< DrugStock > items)")
    void remove_all() {
        assertDoesNotThrow(()-> _controller.remove(_drugStocks));
        _drugStocks.forEach(drugStock -> assertFalse( _controller.get().contains(drugStock) ));
    }

    @Test
    @DisplayName("remove(ArrayList< DrugStock > items) - Empty repository.")
    void remove_all_boundary() {
        _controller.clear();

        assertThrows(ObjectNotFoundException.class, ()-> _controller.remove(_drugStocks));
    }

    @Test
    @DisplayName("remove(ArrayList< DrugStock > items) - DrugStock passed doesn't exist in repository.")
    void remove_all_erroneous() {
        assertThrows(ObjectNotFoundException.class, ()-> _controller.remove(_newDrugStocks));
    }
}