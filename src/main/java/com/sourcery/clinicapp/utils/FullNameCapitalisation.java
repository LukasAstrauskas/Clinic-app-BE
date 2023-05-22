package com.sourcery.clinicapp.utils;


import com.sourcery.clinicapp.physician.model.PhysicianDto;
import com.sourcery.clinicapp.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;


@Component
public class FullNameCapitalisation {

    public <T> T capitalize(T user) {
        try {
            Class<?> userClass = user.getClass();
            String name = (String) userClass.getMethod("getName").invoke(user);
            if (name != null) {
                String[] names = name.split(" ");
                String firstName = names[0].substring(0, 1).toUpperCase() + names[0].substring(1);
                String lastName = names.length > 1 ? names[1].substring(0, 1).toUpperCase() + names[1].substring(1) : "";
                String fullName = firstName + " " + lastName;
                userClass.getMethod("setName", String.class).invoke(user, fullName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}