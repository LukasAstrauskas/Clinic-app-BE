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
        User newUser = user.toBuilder().id(UUID.randomUUID()).type("patient").build();
        userRepository.InsertUsers(newUser);
    }

    public void addPhysician(User user){
        User newUser = user.toBuilder().id(UUID.randomUUID()).type("physician").build();
        userRepository.InsertUsers(newUser);
    }

    public List<User> getPatients(){
        return userRepository.getPatients();
    }

    public List<User> getPhysicians(){
        return userRepository.getPhysicians();
    }

    public void removeUser(UUID uuid){
        userRepository.deleteUser(uuid);
    }


}
