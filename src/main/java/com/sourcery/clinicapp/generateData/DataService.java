package com.sourcery.clinicapp.generateData;

import com.sourcery.clinicapp.occupation.model.Occupation;
import com.sourcery.clinicapp.occupation.repository.OccupationMapper;
import com.sourcery.clinicapp.user.mapper.UserMapper;
import com.sourcery.clinicapp.user.model.Type;
import com.sourcery.clinicapp.user.model.User;
import com.sourcery.clinicapp.user.model.UserDTO;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DataService {
    private final Faker faker = new Faker();

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OccupationMapper occupationMapper;

    private String name;
    private String surname;
    private String email;
    private String type = Type.PHYSICIAN.type();
    private String funnyName;

    private int addedUsers = 0;


    public int generateData(int addCount) {
        if (addCount > 450) {
            return addCount;
        }
//        Occupation[] allOccupations = (Occupation[]) occupationMapper.getAllOccupations().toArray();
        ArrayList<Occupation> allOccupations = new ArrayList<>(occupationMapper.getAllOccupations());
        int size = allOccupations.size();
        Random random = new Random();


        do {
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
                int rndInt = random.nextInt(size);
                Optional<User> byEmail = userMapper.findByEmail(email);
//                byEmail.orElseThrow();
                byEmail.ifPresentOrElse(System.out::println, () -> {
                    User newUser = User.builder()
                            .id(UUID.randomUUID().toString())
                            .name(name)
                            .surname(surname)
                            .email(email)
                            .password(surname)
                            .type(type)
                            .occupationId(allOccupations.get(rndInt).getId())
                            .build();
                    boolean b = userMapper.insertUser(newUser);
                    if (b) {
                        addedUsers++;
                        System.out.println(newUser);
                    }
                });
            } catch (NoSuchElementException ignored) {
            }
        } while (addCount != addedUsers);
//        for (int i = 0; i < addCount; i++) {
//            funnyName = faker.funnyName().name();
//            String[] arr = funnyName.split(" ");
//            if (arr.length > 2) {
//                surname = arr[2];
//                name = arr[0].concat(" ").concat(arr[1]);
//            } else {
//                name = arr[0];
//                surname = arr[1];
//            }
//            email = surname.concat("@gmail.com");
//            try {
//                Optional<User> byEmail = userMapper.findByEmail(email);
//                byEmail.orElseThrow();
//                byEmail.ifPresentOrElse(System.out::println, () -> {
//
//                    User newUser = User.builder()
//                            .id(UUID.randomUUID().toString())
//                            .name(name)
//                            .surname(surname)
//                            .email(email)
//                            .password(surname)
//                            .type(type)
//                            .build();
//                    boolean b = userMapper.insertUser(newUser);
//                    if (b) {
//                        userCount++;
//                    }
//                });
//            } catch (NoSuchElementException ignored) {
//            }
//        }
        addedUsers = 0;
        return userMapper.getUserCount(type);
    }
}

