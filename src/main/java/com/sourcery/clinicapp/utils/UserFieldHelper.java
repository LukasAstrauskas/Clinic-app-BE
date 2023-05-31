package com.sourcery.clinicapp.utils;


import org.springframework.stereotype.Component;


@Component
public class UserFieldHelper {

    public String capitalizeFullName(String name) {
        String[] names = name.split(" ");
        String firstName = capitalizeFirstLetter(names[0]);
        String lastName = capitalizeFirstLetter(names[1]);
        return firstName.concat(" ").concat(lastName);
    }

    private String[] splintFullName(String fullName) {
        return fullName.split(" ");
    }

    public String capitalizeFirstLetter(String word) {
        return word.substring(0, 1).toUpperCase().concat(word.substring(1));
    }
}