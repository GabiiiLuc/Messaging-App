package com.example.proiectextins.domain.validators;

import com.example.proiectextins.domain.Account;
import com.example.proiectextins.domain.User;
import com.example.proiectextins.repository.Repository;

import java.util.Objects;

public class AccountValidator implements Validator<Account> {

    public void validate(String username, Repository<Long, Account> repo) throws ValidationException {
            String errors = "";
            for(Account a : repo.findAll()){
                if(Objects.equals(a.getUser().getUsername(), username)){
                    errors += "Account with username already exists!";
                }
            }

            if(errors.length()>1) {
                throw new ValidationException(errors);
            }

        }

    @Override
    public void validate(Account entity) throws ValidationException {

    }
}
