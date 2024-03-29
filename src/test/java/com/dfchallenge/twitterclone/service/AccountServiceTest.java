package com.dfchallenge.twitterclone.service;

import com.dfchallenge.twitterclone.dao.AccountRepository;
import com.dfchallenge.twitterclone.entity.Account;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImplementation accountService;

    @Test
    @DisplayName("Should pass account validation")
    public void testSaveAccount(){
        Account account = new Account("my_username", "Dave", "Tomlin", "dave@gmail.com", "PASSword!!!766");

        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account createdAccount = accountService.saveAccount(account);
        assertNotNull(createdAccount);

    }

}
