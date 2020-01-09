package project.controllers.repository;

import project.exceptions.DuplicateObjectException;
import project.exceptions.IdClashException;
import project.exceptions.ObjectNotFoundException;
import project.exceptions.OutOfRangeException;
import project.models.users.*;
import project.models.users.info.Gender;
import project.models.users.info.ID;
import project.models.users.info.UserRole;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryControllerTest {
    private UserRepositoryController _controller;
    private EnumMap< UserRole, ArrayList< User > > _users;
    private ArrayList< User > _newUsers;

    /**
     * All names generated for the users were generated from the sites:
     * - https://www.fantasynamegenerators.com/warhammer-40k-space-marine-names.php
     * - https://www.fantasynamegenerators.com/warhammer-40k-sisters-of-battle-names.php
     */
    @BeforeEach
    void setUp() {
        _controller = UserRepositoryController.getInstance();

        // Create a list of users.
        _users = new EnumMap< >(UserRole.class);
        try{
            _users.put(UserRole.ADMIN, new ArrayList<>(
                    Arrays.asList(
                            new Admin("4212", "Praeron", "Ortycos")
                    )
            ));

            _users.put(UserRole.SECRETARY, new ArrayList<>(
                    Arrays.asList(
                            new Secretary("2844", "Barex", "Matys"),
                            new Secretary("5739","Skatardova", "Beror")
                    )
            ));

            _users.put(UserRole.DOCTOR, new ArrayList<>(
                    Arrays.asList(
                            new Doctor("4891", "Raldun", "Deathseeker"),
                            new Doctor("5102", "Kvyrll", "Ironhanded"),
                            new Doctor("5024", "Nectohr", "Elgon")
                    )
            ));

            _users.put(UserRole.PATIENT, new ArrayList<>(
                    Arrays.asList(
                            new Patient("9012", "Castiel", "Fatus", Gender.MALE),
                            new Patient("1164", "Gremenes", "Mordatus", Gender.MALE),
                            new Patient("3462", "Aegot", "Dragonmane", Gender.MALE),
                            new Patient("5352", "Sabrella", "Bles", Gender.FEMALE),
                            new Patient("1902", "Dissonya", "Inviel", Gender.FEMALE)
                    )
            ));

            // Populate the RepositoryController with the users.
            _users.forEach((userRole, users) -> {
                try {
                    _controller.add(users);

                }catch (DuplicateObjectException e){
                    fail("Added user already exists in the repository.");

                }
            });

            _newUsers = new ArrayList<>(
                    Arrays.asList(
                            new Admin("6123", "Henine", "Phorikus"),
                            new Doctor("9382", "Centulochus", "Corabius"),
                            new Patient("6251", "Katanon", "Doruna", Gender.FEMALE),
                            new Secretary("3829", "Issapico", "Aqox")
                    )
            );

        }catch (OutOfRangeException e) {
            fail("Added a user with ID greater than the ID length.");
        } catch (IdClashException e){
            fail("Added a user with an ID that already exists.");
        }
    }

    @AfterEach
    void tearDown() {
        _controller.clear();
    }

    @Test
    @DisplayName("get()")
    void get() {
        ArrayList< User > expected = new ArrayList<>();
        _users.forEach((userRole, users) -> expected.addAll(users));

        ArrayList< User > actual = _controller.get();

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    @DisplayName("get() - Empty repository.")
    void get_boundary() {
        _controller.clear();

        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        ArrayList< User > expected = new ArrayList<>();
        ArrayList< User > actual = _controller.get();

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    @DisplayName("get(String identifier)")
    void get_identifier() {

        _users.forEach(
                (userRole, users) -> {
                    for (User expected : users){
                        User actual = null;
                        try {
                            actual = _controller.get(expected.getUnique());
                            assertEquals(expected, actual);

                        } catch (ObjectNotFoundException e) {
                            fail("Queried an ID that doesn't exist. ");
                        }
                    }
                }
        );
    }

    @Test
    @DisplayName("get(String identifier) - Empty repository.")
    void get_identifier_boundary() {
        _controller.clear();

        _users.forEach(
                (userRole, users) -> {
                    for (User user : users){
                        assertThrows(ObjectNotFoundException.class, () -> _controller.get(user.getUnique()));
                    }
                }
        );
    }

    @Test
    @DisplayName("get(String identifier) - Erroneous identifier passed.")
    void get_identifier_erroneous() {
        assertThrows(ObjectNotFoundException.class, () -> _controller.get("A6921"));
    }

    @Test
    @DisplayName("getIDs()")
    void getIDs() {
        ArrayList< ID > expected = new ArrayList<>();
        _users.forEach((userRole, users) -> {
                for (User user : users) expected.add(user.getId());
            }
        );

        ArrayList< ID > actual = _controller.getIDs();

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    @DisplayName("getIDs() - Empty repository.")
    void getIDs_boundary() {
        _controller.clear();

        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        ArrayList< ID > expected = new ArrayList<>();
        ArrayList< ID > actual = _controller.getIDs();

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    @DisplayName("getID(UserRole role)")
    void getIDs_role() {
        for (UserRole role : UserRole.values()){
            ArrayList< ID > expected = new ArrayList<>();
            _users.get(role).forEach(user -> expected.add(user.getId()));

            ArrayList< ID > actual = _controller.getIDs(role);

            assertArrayEquals(expected.toArray(), actual.toArray());
        }
    }

    @Test
    @DisplayName("getID(UserRole role) - Empty repository.")
    void getIDs_role_boundary() {
        _controller.clear();

        for (UserRole role : UserRole.values()){
            @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
            ArrayList< ID > expected = new ArrayList<>();
            ArrayList< ID > actual = _controller.getIDs(role);

            assertArrayEquals(expected.toArray(), actual.toArray());
        }
    }

    @Test
    @DisplayName("contains(String identifier)")
    void contains() {
        _users.forEach(
                (userRole, users) -> {
                    for (User user : users){
                        assertTrue(_controller.contains(user.getUnique()));
                    }
                }
        );
    }

    @Test
    @DisplayName("contains(String identifier) - Empty repository.")
    void contains_boundary() {
        _controller.clear();

        _users.forEach(
                (userRole, users) -> {
                    for (User user : users){
                        assertFalse(_controller.contains(user.getUnique()));
                    }
                }
        );
    }

    @Test
    @DisplayName("contains(String identifier) - Erroneous identifier passed.")
    void contains_erroneous() {
        assertFalse(_controller.contains("A6921"));
    }

    @Test
    @DisplayName("add(User item) - Admin object.")
    void add0() {
        try {
            Admin newUser = new Admin("6123", "Henine", "Phorikus");
            assertDoesNotThrow(()->_controller.add(newUser));

            assertTrue(_controller.get().contains(newUser));

        } catch (OutOfRangeException e){
            fail("Added a user with ID greater than the ID length.");
        } catch (IdClashException e){
            fail("Added a user with an ID that already exists.");
        }
    }

    @Test
    @DisplayName("add(User item) - Doctor object.")
    void add1() {
        try {
            Doctor newUser = new Doctor("9382", "Centulochus", "Corabius");
            assertDoesNotThrow(()->_controller.add(newUser));

            assertTrue(_controller.get().contains(newUser));

        } catch (OutOfRangeException e){
            fail("Added a user with ID greater than the ID length.");
        } catch (IdClashException e){
            fail("Added a user with an ID that already exists.");
        }
    }

    @Test
    @DisplayName("add(User item) - Patient object.")
    void add2() {
        try {
            Patient newUser = new Patient("6251", "Katanon", "Doruna", Gender.FEMALE);
            assertDoesNotThrow(()->_controller.add(newUser));

            assertTrue(_controller.get().contains(newUser));

        }catch (OutOfRangeException e){
            fail("Added a user with ID greater than the ID length.");
        } catch (IdClashException e){
            fail("Added a user with an ID that already exists.");
        }
    }

    @Test
    @DisplayName("add(User item) - Secretary object.")
    void add3() {
        try {
            Secretary newUser = new Secretary("3829", "Issapico", "Aqox");
            assertDoesNotThrow(()->_controller.add(newUser));

            assertTrue(_controller.get().contains(newUser));

        } catch (OutOfRangeException e){
            fail("Added a user with ID greater than the ID length.");
        } catch (IdClashException e){
            fail("Added a user with an ID that already exists.");
        }
    }

    @Test
    @DisplayName("add(User item) - Empty repository.")
    void add_boundary() {
        _controller.clear();
        _users.forEach((userRole, users) -> {
                    for (User user : users) {
                        assertDoesNotThrow(() -> _controller.add(user));
                        assertTrue(_controller.get().contains(user));
                    }
                }
        );
    }

    @Test
    @DisplayName("add(User item) - User passed already exists.")
    void add_erroneous0() {
        _users.forEach((userRole, users) -> {
                for (User user : users) assertThrows(DuplicateObjectException.class, () -> _controller.add(user));
            }
        );
    }

    @Test
    @DisplayName("add(ArrayList< User > items)")
    void add_all() {
        assertDoesNotThrow(()->_controller.add(_newUsers));
    }

    @Test
    @DisplayName("add(ArrayList< User > items) -  Empty repository.")
    void add_all_boundary() {
        _controller.clear();
        _users.forEach((userRole, users) ->  {
            assertDoesNotThrow(()->_controller.add(users));
            for (User user : users) assertTrue(_controller.get().contains(user));
            }
        );
    }

    @Test
    @DisplayName("add(ArrayList< User > items) - User in ArrayList already exists.")
    void add_all_erroneous() {
        _users.forEach((userRole, users) ->  assertThrows(DuplicateObjectException.class, ()->_controller.add(users)));
    }

    @Test
    @DisplayName("remove(User item)")
    void remove() {
        _users.forEach((userRole, users) -> {
                    for (User user : users) {
                        assertDoesNotThrow(() -> _controller.remove(user));
                        assertFalse(_controller.get().contains(user));
                    }
                }
        );
    }

    @Test
    @DisplayName("remove(User item) - Empty repository.")
    void remove_boundary() {
        _controller.clear();

        _users.forEach((userRole, users) -> {
                    for (User user : users) {
                        assertThrows(ObjectNotFoundException.class, () -> _controller.remove(user));
                    }
                }
        );
    }

    @Test
    @DisplayName("remove(User item) - User does not exist in repository.")
    void remove_erroneous0() {
        _newUsers.forEach(user -> assertThrows(ObjectNotFoundException.class, () -> _controller.remove(user)));
    }

    @Test
    @DisplayName("remove(ArrayList< User > items)")
    void remove_all() {
        _users.forEach((userRole, users) -> {
                    assertDoesNotThrow(() -> _controller.remove(users));
                    users.forEach( user -> assertFalse(_controller.get().contains(user)));
                }
        );
    }

    @Test
    @DisplayName("remove(ArrayList< User > items) - Empty repository.")
    void remove_all_boundary() {
        _controller.clear();
        _users.forEach((userRole, users) -> assertThrows(ObjectNotFoundException.class, () -> _controller.remove(users)));
    }

    @Test
    @DisplayName("remove(ArrayList< User > items) - User does not exist in repository.")
    void remove_all_erroneous0() {
        assertThrows(ObjectNotFoundException.class, () -> _controller.remove(_newUsers));
    }
}