package com.dfchallenge.twitterclone.validators;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;


public class AccountValidatorTest {


    @Test
    @DisplayName("Should pass due to correct email input")
    public void testCheckEmailValid() {
        String email = "test@example.com";
        assertDoesNotThrow(() -> AccountValidators.checkEmail(email));
    }

    @Test
    @DisplayName("Should fail due to no @ in email")
    public void testCheckEmailInvalid() {
        String email = "invalidEmail.com";
        assertThrows(IllegalArgumentException.class, () -> AccountValidators.checkEmail(email));
    }

    @Test
    @DisplayName("Should pass due to correct username input")
    public void testCheckUsernameValid() {
        String username = "validUsername";
        assertDoesNotThrow(() -> AccountValidators.checkUsername(username));
    }

    @Test
    @DisplayName("Should fail due to empty username")
    public void testCheckUsernameInvalid() {
        String username = "";
        assertThrows(IllegalArgumentException.class, () -> AccountValidators.checkUsername(username));
    }

    @Test
    @DisplayName("Should pass due to valid name")
    public void testCheckFNameValid() {
        String fName = "ValidName";
        assertDoesNotThrow(() -> AccountValidators.checkFName(fName));
    }

    @Test
    @DisplayName("Should fail due to empty first name")
    public void testCheckFNameInvalid() {
        String fName = "";
        assertThrows(IllegalArgumentException.class, () -> AccountValidators.checkFName(fName));
    }

    @Test
    @DisplayName("Should pass due to valid first name")
    public void testCheckLNameValid() {
        String lName = "ValidName";
        assertDoesNotThrow(() -> AccountValidators.checkLName(lName));
    }

    @Test
    @DisplayName("Should fail due to empty last name")
    public void testCheckLNameInvalid() {
        String lName = "";
        assertThrows(IllegalArgumentException.class, () -> AccountValidators.checkLName(lName));
    }

    @Test
    @DisplayName("Should pass as valid password")
    public void testCheckPasswordValid() {
        String password = "ValidPassword1!";
        assertDoesNotThrow(() -> AccountValidators.checkPassword(password));
    }

    @Test
    @DisplayName("Should fail due to password too short")
    public void testCheckPasswordInvalid() {
        String password = "short";
        assertThrows(IllegalArgumentException.class, () -> AccountValidators.checkPassword(password));
    }

    @Test
    @DisplayName("Should fail due to no uppercase letter")
    public void testPasswordNoUppercase() {
        String password = "validpassword1!";
        assertThrows(IllegalArgumentException.class, () -> AccountValidators.checkPassword(password));
    }

    @Test
    @DisplayName("Should fail due to no lowercase letter")
    public void testPasswordNoLowercase() {
        String password = "VALIDPASSWORD1!";
        assertThrows(IllegalArgumentException.class, () -> AccountValidators.checkPassword(password));
    }

    @Test
    @DisplayName("Should fail due to no number")
    public void testPasswordNoNumber() {
        String password = "ValidPassword!";
        assertThrows(IllegalArgumentException.class, () -> AccountValidators.checkPassword(password));
    }

    @Test
    @DisplayName("Should fail due to no special character")
    public void testPasswordNoSpecialChar() {
        String password = "ValidPassword1";
        assertThrows(IllegalArgumentException.class, () -> AccountValidators.checkPassword(password));
    }

    @Test
    @DisplayName("Should fail as only uppercase")
    public void testPasswordOnlyUppercase() {
        String password = "VALID";
        assertThrows(IllegalArgumentException.class, () -> AccountValidators.checkPassword(password));
    }

}
