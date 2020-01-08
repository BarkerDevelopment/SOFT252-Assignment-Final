package controllers.primary.login;

import controllers.repository.UserRepositoryController;
import exceptions.DuplicateObjectException;
import exceptions.IdClashException;
import exceptions.LoginException;
import exceptions.OutOfRangeException;
import models.users.*;
import models.users.info.Gender;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class LoggedOutStateTest {
    private LoginController _controller;
    private UserRepositoryController _repositoryController;
    private LoggedOutState _state;
    private ArrayList< User > _users;
    private ArrayList< String > _password;

    @BeforeEach
    void setUp() {
        _controller = LoginController.getInstance();
        _repositoryController = UserRepositoryController.getInstance();
        _state = new LoggedOutState();

        _password = new ArrayList<>(
                Arrays.asList(
                        "3t3rm1n@tus",
                        "b0lter",
                        "SalamandersRule!",
                        "Th3W@rp",
                        "EyeOfTerror"
                )
        );

        try{
            _users = new ArrayList<>(
                    Arrays.asList(
                            new Doctor("5102", "Kvyrll", "Ironhanded", _password.get(0)),
                            new Admin("4212", "Praeron", "Ortycos", _password.get(1)),
                            new Patient("3462", "Aegot", "Dragonmane", _password.get(2), Gender.MALE),
                            new Patient("5352", "Sabrella", "Bles", _password.get(3), Gender.FEMALE),
                            new Secretary("5739","Skatardova", "Beror", _password.get(4))
                    )
            );

            _repositoryController.add(_users);

        }catch (OutOfRangeException e){
            fail("Added a user with ID greater than the ID length.");

        }catch (DuplicateObjectException e){
            fail("Added user already exists in the repository.");
        } catch (IdClashException e){
            fail("Added a user with an ID that already exists.");
        }
    }

    @AfterEach
    void tearDown(){
        _controller.setState(new LoggedOutState());
    }

    @Test
    @DisplayName("login()")
    void login() {
        for (int i = 0; i < _password.size(); i++) {
            User user = _users.get(i);
            _state.setUsername(user.getId().toString());
            _state.setPassword(_password.get(i));

            assertDoesNotThrow(() -> _state.login(_controller));
            assertTrue(_controller.getState() instanceof LoggedInState);
            assertEquals(((LoggedInState)_controller.getState()).getUser().getId().toString(), user.getId().toString());

            _controller.setState(new LoggedOutState());
        }
    }

    @Test
    @DisplayName("login() - Empty fields.")
    void login_erroneous0() {
        assertThrows(LoginException.class, () ->_state.login(_controller));
    }

    @Test
    @DisplayName("login() - Incorrect user.")
    void login_erroneous1() {
        _state.setUsername("test");
        _state.setPassword(_password.get(0));
        assertThrows(LoginException.class, () ->_state.login(_controller));
    }

    @Test
    @DisplayName("login() - Incorrect password.")
    void login_erroneous2() {
        for (User user : _users) {
            _state.setUsername(user.getId().toString());
            _state.setPassword("password");

            assertThrows(LoginException.class, () ->_state.login(_controller));
        }
    }

    @Test
    @DisplayName("logout()")
    void logout() {
        assertThrows(LoginException.class, ()-> _state.login(_controller));
        assertEquals(_state, _controller.getState());
    }
}