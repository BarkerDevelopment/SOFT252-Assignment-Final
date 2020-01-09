package project.models.requests;

import project.controllers.repository.DrugRepositoryController;
import project.exceptions.IdClashException;
import project.exceptions.OutOfRangeException;
import project.models.drugs.DrugStock;
import project.models.users.Doctor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DrugRequestTest {
    private DrugRepositoryController _controller;
    private ArrayList< DrugRequest > _drugRequests;

    @BeforeEach
    void setUp() {
        _controller = DrugRepositoryController.getInstance();

        try {
            Doctor doctor = new Doctor("9382", "Centulochus", "Corabius");

            _drugRequests = new ArrayList<>(
                    Arrays.asList(
                            new DrugRequest(doctor, "Ibuprofen", "Anti-Inflammatory", new ArrayList<>(), 100),
                            new DrugRequest(doctor, "Paracetamol", "Painkiller", new ArrayList<>(), 12),
                            new DrugRequest(doctor, "Morphine",  "Painkiller", new ArrayList<>(), 50)
                    )
            );

        }catch (OutOfRangeException e) {
            fail("Added a user with ID greater than the ID length.");
        } catch (IdClashException e){
            fail("Added a user with an ID that already exists.");
        }
    }

    @AfterEach
    void tearDown(){
        _controller.clear();
    }

    @Test
    @DisplayName("approveAction()")
    void approveAction() {
        for (int i = 0; i < _drugRequests.size(); i++) {
            DrugRequest request = _drugRequests.get(i);
            request.approveAction();
            assertEquals(i + 1, _controller.get().size());
            assertEquals(i + 1, request.getDoctor().getMessages().size());
        }
    }

    @Test
    @DisplayName("approveAction() - Trigger a DuplicateObjectException")
    void approveAction_erroneous() {
        DrugRepositoryController.getInstance().add(
                new DrugStock("Ibuprofen", "Anti-Inflammatory", new ArrayList<>(Arrays.asList("Stomach ulcers")), 500)
        );

        DrugRequest request = _drugRequests.get(0);
        request.approveAction();
        assertEquals( 1, _controller.get().size());
        assertEquals(1, request.getDoctor().getMessages().size());
    }

    @Test
    @DisplayName("denyAction()")
    void denyAction() {
        for (int i = 0; i < _drugRequests.size(); i++) {
            DrugRequest request = _drugRequests.get(i);
            request.denyAction();
            assertEquals(i + 1, request.getDoctor().getMessages().size());
        }
    }
}