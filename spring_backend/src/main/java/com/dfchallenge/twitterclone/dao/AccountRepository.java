package com.dfchallenge.twitterclone.dao;

import com.dfchallenge.twitterclone.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

//    ---- Methods that come with JPA -------
//    save(Account instance): Saves a given entity.
//    findById(ID id): Retrieves an entity by its ID.
//    findAll(): Returns all entities.
//    delete(T entity): Deletes a given entity.
//    deleteById(ID id): Deletes the entity with the given ID.
//    count(): Returns the number of entities.
//    existsById(ID id): Indicates whether an entity with the given ID exists.



//    ---- Custom Methods -----
    Optional<Account> findByEmail(String email);


}
