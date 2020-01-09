package project.controllers.primary.login;

import project.exceptions.IdClashException;
import project.exceptions.LoginException;
import project.exceptions.OutOfRangeException;
import project.models.users.Patient;
import project.models.users.info.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoggedInStateTest {
    private LoginController _controller;
    private LoggedInState _state;

    @BeforeEach
    void setUp() {
        _controller = LoginController.getInstance();

        try {
            Patient patient = new Patient("1164", "Gremenes", "Mordatus", Gender.MALE);
            _state = new LoggedInState(patient);

        }catch (OutOfRangeException e) {
            fail();
        } catch (IdClashException e){
            fail("Added a user with an ID that already exists.");
        }
    }

    @Test
    @DisplayName("login()")
    void login() {
        assertThrows(LoginException.class, ()-> _state.login(_controller));
        assertEquals(_state, _controller.getState());
    }

    @Test
    @DisplayName("logout()")
    void logout() {
        assertDoesNotThrow( ()->_state.logout(_controller) );
        assertTrue(_controller.getState() instanceof LoggedOutState);
    }
}