package com.example.proiectextins.domain.validators;


import com.example.proiectextins.domain.User;

public class UserValidator implements Validator<User> {
    @Override
    public void validate(User entity) throws ValidationException {
        String errors = "";
        boolean fhas = false;
        boolean lhas = false;
        char[] chars = entity.getFirstName().toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (Character.isDigit(c))
                fhas = true;

        }

        char[] charsl = entity.getLastName().toCharArray();
        StringBuilder sbl = new StringBuilder();
        for (char c : charsl) {
            if (Character.isDigit(c))
                lhas = true;
        }
        if(fhas)
            errors += " First name cannot have numbers ";
        if(lhas)
            errors += " Last name cannot have numbers ";

        if(errors.length()>1) {
            throw new ValidationException(errors);
        }
    }

}
