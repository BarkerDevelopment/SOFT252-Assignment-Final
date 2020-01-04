package models.users.info;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRoleTest {
    @Test
    @DisplayName("fromChar(char input) - a")
    void fromChar_a() {
        UserRole expected = UserRole.ADMIN;
        UserRole actual = UserRole.fromChar('a');

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("fromChar(char input) - A")
    void fromChar_A() {
        UserRole expected = UserRole.ADMIN;
        UserRole actual = UserRole.fromChar('A');

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("fromChar(char input) - d")
    void fromChar_d() {
        UserRole expected = UserRole.DOCTOR;
        UserRole actual = UserRole.fromChar('d');

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("fromChar(char input) - D")
    void fromChar_D() {
        UserRole expected = UserRole.DOCTOR;
        UserRole actual = UserRole.fromChar('D');

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("fromChar(char input) - p")
    void fromChar_p() {
        UserRole expected = UserRole.PATIENT;
        UserRole actual = UserRole.fromChar('p');

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("fromChar(char input) - P")
    void fromChar_P() {
        UserRole expected = UserRole.PATIENT;
        UserRole actual = UserRole.fromChar('P');

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("fromChar(char input) - s")
    void fromChar_s() {
        UserRole expected = UserRole.SECRETARY;
        UserRole actual = UserRole.fromChar('s');

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("fromChar(char input) - S")
    void fromChar_S() {
        UserRole expected = UserRole.SECRETARY;
        UserRole actual = UserRole.fromChar('S');

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("fromChar(char input) - Erroneous input")
    void fromChar_erroneous(){
        assertThrows(EnumConstantNotPresentException.class, () -> UserRole.fromChar('q'));
    }
}