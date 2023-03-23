package com.sourcery.clinicapp.service;


import com.sourcery.clinicapp.model.User;
import com.sourcery.clinicapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public void addPatient(User user){
        User user1 = user.toBuilder().id(UUID.randomUUID()).build();
        User user2 = user1.toBuilder().type("patient").build();
        userRepository.InsertUsers(user2);
    }

    public void addPhysician(User user){
        User user1 = user.toBuilder().id(UUID.randomUUID()).build();
        User user2 = user1.toBuilder().type("physician").build();
        userRepository.InsertUsers(user2);
    }

    public List<User> getPatients(){
        return userRepository.getPatients();
    }

    public List<User> getPhysicians(){
        return userRepository.getPhysicians();
    }




}
