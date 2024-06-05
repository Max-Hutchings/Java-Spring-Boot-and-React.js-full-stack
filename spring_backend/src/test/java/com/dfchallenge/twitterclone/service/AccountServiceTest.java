package com.dfchallenge.twitterclone.service;

import com.dfchallenge.twitterclone.dao.AccountRepository;
import com.dfchallenge.twitterclone.entity.account.Account;

import static org.junit.jupiter.api.Assertions.*;

import com.dfchallenge.twitterclone.entity.account.Role;
import com.dfchallenge.twitterclone.exceptions.FailedToGetAccountException;
import com.dfchallenge.twitterclone.exceptions.InvalidAccountInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImplementation accountService;


    @Test
    @DisplayName("Test saving a valid account should be successful")
    public void testSaveAccount_Success() {
        Account validAccount = new Account( "validUsername", "FirstName", "LastName", "validEmail@example.com",
                "ValidPassword1!", "USER");
        when(accountRepository.save(any(Account.class))).thenReturn(validAccount);

        Account savedAccount = accountService.saveAccount(validAccount);

        assertNotNull(savedAccount);
        assertNotEquals("ValidPassword1!", savedAccount.getPassword());
        verify(accountRepository).save(validAccount);
    }

    @Test
    @DisplayName("Test saving an account with an invalid email should throw IllegalArgumentException")
    public void testSaveAccount_InvalidEmail() {
        Account invalidEmailAccount = new Account( "validUsername", "FirstName", "LastName", "invalidemail.com",
                "ValidPassword1!", "USER");

        Exception exception = assertThrows(InvalidAccountInputException.class,
                () -> accountService.saveAccount(invalidEmailAccount));
        assertEquals("Invalid email", exception.getMessage());
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    @DisplayName("Test saving an account with an invalid username should throw IllegalArgumentException")
    public void testSaveAccount_InvalidUsername() {
        Account invalidUsernameAccount = new Account( "", "FirstName", "LastName", "validEmail@example.com",
                "ValidPassword1!", "USER");


        Exception exception = assertThrows(InvalidAccountInputException.class,
                () -> accountService.saveAccount(invalidUsernameAccount));
        assertEquals("Invalid username", exception.getMessage());
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    @DisplayName("Test saving an account with an invalid first name should throw IllegalArgumentException")
    public void testSaveAccount_InvalidFirstName() {
        Account invalidFirstNameAccount = new Account( "validUsername", "", "LastName", "validEmail@example.com",
                "ValidPassword1!", "USER");

        Exception exception = assertThrows(InvalidAccountInputException.class,
                () -> accountService.saveAccount(invalidFirstNameAccount));
        assertEquals("Invalid fName", exception.getMessage());
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    @DisplayName("Test saving an account with an invalid last name should throw IllegalArgumentException")
    public void testSaveAccount_InvalidLastName() {
        Account invalidLastNameAccount = new Account( "validUsername", "FirstName", "", "validEmail@example.com",
                "ValidPassword1!", "USER");

        Exception exception = assertThrows(InvalidAccountInputException.class,
                () -> accountService.saveAccount(invalidLastNameAccount));
        assertEquals("Invalid lName", exception.getMessage());
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    @DisplayName("Test saving an account with an invalid password should throw IllegalArgumentException")
    public void testSaveAccount_InvalidPassword() {
        Account invalidPasswordAccount = new Account( "validUsername", "FirstName", "LastName", "validEmail@example" +
                ".com", "invalidPassword", "USER");

        Exception exception = assertThrows(InvalidAccountInputException.class,
                () -> accountService.saveAccount(invalidPasswordAccount));
        assertEquals("Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character", exception.getMessage());
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    @DisplayName("Test password hashing for a valid account save")
    public void testSaveAccount_PasswordHashing() {
        Account validAccount = new Account( "validUsername", "FirstName", "LastName", "validEmail@example.com",
                "ValidPassword1!", "USER");
        when(accountRepository.save(any(Account.class))).thenAnswer(i -> i.getArguments()[0]);

        Account savedAccount = accountService.saveAccount(validAccount);

        assertNotEquals("ValidPassword1!", savedAccount.getPassword());
    }

    @Test
    @DisplayName("Should pass due to correct Id")
    public void whenAccountByIdFound_thenReturnAccount() {
        Account mockAccount =  new Account( "validUsername", "FirstName", "LastName", "validEmail@example.com",
                "ValidPassword1!", "USER");
        when(accountRepository.findById(1)).thenReturn(Optional.of(mockAccount));

        Optional<Account> found = accountService.getAccountById(1);

        assertTrue(found.isPresent());
        assertEquals(mockAccount, found.get());
    }

    @Test
    @DisplayName("Should fail due to null id")
    public void whenAccountByIdNotFound_thenNull() {
        when(accountRepository.findById(anyInt())).thenReturn(Optional.empty());

        Optional<Account> found = accountService.getAccountById(1);

        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("Should get account by email")
    public void whenAccountByEmailFound_thenReturnAccount() {
        Account mockAccount =  new Account( "validUsername", "FirstName", "LastName", "validEmail@example.com",
                "ValidPassword1!", "USER");
        when(accountRepository.findByEmail("example@example.com")).thenReturn(Optional.of(mockAccount));

        Account found = accountService.getAccountByEmail("example@example.com");

        assertEquals(mockAccount, found);
    }

    @Test
    @DisplayName("Should get account by email and verify username")
    public void whenAccountByEmailFound_thenUsernameIsCorrect() {
        Account mockAccount = new Account("validUsername", "FirstName", "LastName", "validEmail@example.com",
                "ValidPassword1!", "USER");
        when(accountRepository.findByEmail("validEmail@example.com")).thenReturn(Optional.of(mockAccount));


        Account found = accountService.getAccountByEmail("validEmail@example.com");

        assertEquals("validUsername", found.getUsername());
    }

    @Test
    @DisplayName("Should get account by email and verify first name")
    public void whenAccountByEmailFound_thenFirstNameIsCorrect() {
        Account mockAccount = new Account("validUsername", "FirstName", "LastName", "validEmail@example.com",
                "ValidPassword1!", "USER");
        when(accountRepository.findByEmail("validEmail@example.com")).thenReturn(Optional.of(mockAccount));

        Account found = accountService.getAccountByEmail("validEmail@example.com");

        assertEquals("FirstName", found.getFName());
    }


    @Test
    @DisplayName("Should get account by email and verify last name")
    public void whenAccountByEmailFound_thenLastNameIsCorrect() {
        Account mockAccount = new Account("validUsername", "FirstName", "LastName", "validEmail@example.com",
                "ValidPassword1!", "USER");
        when(accountRepository.findByEmail("validEmail@example.com")).thenReturn(Optional.of(mockAccount));

        Account found = accountService.getAccountByEmail("validEmail@example.com");

        assertEquals("LastName", found.getLName());
    }

    @Test
    @DisplayName("Should get account by email and verify email")
    public void whenAccountByEmailFound_thenEmailIsCorrect() {
        Account mockAccount = new Account("validUsername", "FirstName", "LastName", "validEmail@example.com",
                "ValidPassword1!", "USER");
        when(accountRepository.findByEmail("validEmail@example.com")).thenReturn(Optional.of(mockAccount));

        Account found = accountService.getAccountByEmail("validEmail@example.com");

        assertEquals("validEmail@example.com", found.getEmail());
    }

    @Test
    @DisplayName("Should get account by email and verify role")
    public void whenAccountByEmailFound_thenRoleIsCorrect() {
        Account mockAccount = new Account("validUsername", "FirstName", "LastName", "validEmail@example.com",
                "ValidPassword1!", "USER");
        when(accountRepository.findByEmail("validEmail@example.com")).thenReturn(Optional.of(mockAccount));

        Account found = accountService.getAccountByEmail("validEmail@example.com");

        assertEquals(Role.USER, found.getRole());
    }



    @Test
    @DisplayName("Should fail due to email being null")
    public void whenAccountByEmailNotFound_thenNull() {
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(FailedToGetAccountException.class, () -> {
            accountService.getAccountByEmail("example@example.com");
        });


    }



}
