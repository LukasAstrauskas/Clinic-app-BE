package com.sourcery.clinicapp.utils;


import org.springframework.stereotype.Component;


@Component
public class UserFieldHelper {

    public String capitalizeFirstLetter(String word) {
        return word.substring(0, 1).toUpperCase().concat(word.substring(1));
    }
}