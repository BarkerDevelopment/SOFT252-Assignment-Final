package models.requests;

import controllers.repository.UserRepositoryController;
import models.users.info.Address;
import models.users.info.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AccountCreationRequestTest {
    private UserRepositoryController _controller;
    private ArrayList< AccountCreationRequest > _requests;

    /**
     * All names generated for the users were generated from the sites:
     * - https://www.fantasynamegenerators.com/warhammer-40k-space-marine-names.php
     * - https://www.fantasynamegenerators.com/warhammer-40k-sisters-of-battle-names.php
     */
    @BeforeEach
    void setUp() {
        _controller = UserRepositoryController.getInstance();

        _requests = new ArrayList<>(
                Arrays.asList(
                        new AccountCreationRequest("Petiutius", "Sicallis", new Address(), "", LocalDate.now(), Gender.MALE),
                        new AccountCreationRequest("Archaxus", "Tigullis", new Address(), "", LocalDate.now(), Gender.MALE),
                        new AccountCreationRequest("Gribor", "Domiar", new Address(), "", LocalDate.now(), Gender.MALE),
                        new AccountCreationRequest("Sephena", "Olerine", new Address(), "", LocalDate.now(), Gender.FEMALE)
                )
        );
    }

    @Test
    @DisplayName("approveAction()")
    void approveAction() {
        for (int i = 0; i < _requests.size(); i++) {
            assertDoesNotThrow(_requests.get(i)::approveAction);
            assertEquals(i + 1, _controller.get().size());
        }
    }
}