package com.sourcery.clinicapp.generateData;

import com.sourcery.clinicapp.user.mapper.UserMapper;
import com.sourcery.clinicapp.user.model.Type;
import com.sourcery.clinicapp.user.model.User;
import net.datafaker.Faker;
import net.datafaker.providers.base.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DataService {
    private final Faker faker = new Faker();

    @Autowired
    private UserMapper userMapper;

    public int generateData(int count) {
        if (count > 450) {
            return count;
        }

        String name;
        String surname;
        String email;
        String type = Type.PATIENT.type();
        String funnyName;

        int userCount = userMapper.getUserCount(type);
        for (int i = 0; i < count; i++) {
            funnyName = faker.funnyName().name();
            String[] arr = funnyName.split(" ");
            if (arr.length > 2) {
                surname = arr[2];
                name = arr[0].concat(" ").concat(arr[1]);
            } else {
                name = arr[0];
                surname = arr[1];
            }
            email = surname.concat("@gmail.com");

            try {
                Optional<User> byEmail = userMapper.findByEmail(email);
                byEmail.orElseThrow();
//                boolean b = userMapper.insertUser(newUser);
//                System.out.println("Inserted: "+ b);
            } catch (NoSuchElementException ignored) {
                User newUser = User.builder()
                        .id(UUID.randomUUID().toString())
                        .name(name)
                        .surname(surname)
                        .email(email)
                        .password(surname)
                        .type(type)
                        .build();
                userMapper.insertUser(newUser);
            }
        }
        userCount = userMapper.getUserCount(type);
        return userCount;
    }
}

