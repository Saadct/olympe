package saad.projet.jo.service.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import saad.projet.jo.validator.StrongPasswordConstraintValidator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StrongPasswordConstraintValidatorTest {

    private StrongPasswordConstraintValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new StrongPasswordConstraintValidator();
        validator.initialize(null);
    }

    @Test
    public void testValidPassword() {
        String validPassword = "Test91350!eeee";
        assertTrue(validator.isValid(validPassword, null), "Password should be valid");
    }

    @Test
    public void testInvalidPasswordNoUpperCase() {
        String invalidPassword = "test91350!eeee";
        assertFalse(validator.isValid(invalidPassword, null), "Password without uppercase letter should be invalid");
    }

    @Test
    public void testInvalidPasswordNoLowerCase() {
        String invalidPassword = "TEST91350!EEEE";
        assertFalse(validator.isValid(invalidPassword, null), "Password without lowercase letter should be invalid");
    }

    @Test
    public void testInvalidPasswordNoDigit() {
        String invalidPassword = "Test!!!!!eeee";
        assertFalse(validator.isValid(invalidPassword, null), "Password without digit should be invalid");
    }

    @Test
    public void testInvalidPasswordNoSpecialCharacter() {
        String invalidPassword = "Test91350eeee";
        assertFalse(validator.isValid(invalidPassword, null), "Password without special character should be invalid");
    }

    @Test
    public void testInvalidPasswordTooShort() {
        String invalidPassword = "T1!e";
        assertFalse(validator.isValid(invalidPassword, null), "Password too short should be invalid");
    }

    @Test
    public void testInvalidPasswordTooLong() {
        String invalidPassword = "Test91350!eeeeeeeeeeeeeeeeeeee";
        assertFalse(validator.isValid(invalidPassword, null), "Password too long should be invalid");
    }

    @Test
    public void testInvalidPasswordWithSpaces() {
        String invalidPassword = "Test 91350!eeee";
        assertFalse(validator.isValid(invalidPassword, null), "Password with spaces should be invalid");
    }
}