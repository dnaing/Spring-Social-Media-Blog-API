package com.example.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
@Transactional
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<Account> registerAccount(Account account) {
        Optional<Account> optionalAccount = accountRepository.findAccountByUsername(account.getUsername());
        if (optionalAccount.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(null);
        }
        if (account.getUsername().length() == 0 || account.getPassword().length() < 4) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(accountRepository.save(account));
    }

    public ResponseEntity<Account> loginAccount(Account account) {

        Optional<Account> optionalAccount = accountRepository.findAccountByUsernameAndPassword(account.getUsername(),account.getPassword());
        if (optionalAccount.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(optionalAccount.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }
    }



    





}
