//package com.dfchallenge.twitterclone.dao;
//import com.dfchallenge.twitterclone.dao.AccountRepository;
//
//import com.dfchallenge.twitterclone.entity.account.Account;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//
//import java.util.Optional;
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//public class AccountRepositoryTest {
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Autowired
//    private AccountRepository accountRepository;
//
//    @Test
//    @DisplayName("Should return account")
//    public void findAccountByEmailSuccess() {
//        Account account = new Account("testUser", "Test", "User", "test@example.com", "password123");
//        entityManager.persist(account);
//        entityManager.flush();
//
//        Optional<Account> found = accountRepository.findByEmail(account.getEmail());
//
//        assertTrue(found.isPresent());
//        assertEquals(account.getEmail(), found.get().getEmail());
//    }
//
//    @Test
//    @DisplayName("Should not return account")
//    public void findAccountByEmailFail() {
//        Optional<Account> found = accountRepository.findByEmail("nonexisting@example.com");
//
//        assertFalse(found.isPresent());
//    }
//}
