package models.requests;

import controllers.repository.UserRepositoryController;
import exceptions.DuplicateObjectException;
import exceptions.OutOfRangeException;
import models.users.Patient;
import models.users.User;
import models.users.info.Gender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class AccountTerminationRequestTest {
    private UserRepositoryController _controller;
    private ArrayList< User > _patients;
    private ArrayList< AccountTerminationRequest > _requests;

    @BeforeEach
    void setUp() {
        _controller = UserRepositoryController.getInstance();

        try {
            _patients = new ArrayList<>(
                    Arrays.asList(
                            new Patient("9012", "Castiel", "Fatus", Gender.MALE),
                            new Patient("1164", "Gremenes", "Mordatus", Gender.MALE),
                            new Patient("3462", "Aegot", "Dragonmane", Gender.MALE),
                            new Patient("5352", "Sabrella", "Bles", Gender.FEMALE),
                            new Patient("1902", "Dissonya", "Inviel", Gender.FEMALE)
                    )
            );

            _controller.add(_patients);

        }catch (OutOfRangeException e) {
            fail("Added a user with ID greater than the ID length.");

        }catch (DuplicateObjectException e){
            fail("Added user already exists in the repository.");
        }

        _requests = new ArrayList<>(
          _patients.stream().map(AccountTerminationRequest::new).collect(Collectors.toList())
        );
    }

    @Test
    @DisplayName("approveAction()")
    void approveAction() {
        for (Request request : _requests){
            assertDoesNotThrow(request::approveAction);
            assertFalse(_controller.get().contains( ((AccountTerminationRequest) request).getRequester() ));
        }
    }

    @Test
    @DisplayName("denyAction()")
    void denyAction() {
        for (Request request : _requests){
            assertDoesNotThrow(request::denyAction);
            Patient patient = (Patient) ((AccountTerminationRequest) request).getRequester();
            assertTrue(_controller.get().contains(patient));
            assertEquals(1, patient.getMessages().size());
        }
    }
}