package com.sourcery.clinicapp.service;


import com.sourcery.clinicapp.dto.PhysicianDto;
import com.sourcery.clinicapp.model.AdditionalPhysicianInfo;
import com.sourcery.clinicapp.model.Login;
import com.sourcery.clinicapp.model.Physician;
import com.sourcery.clinicapp.model.User;
import com.sourcery.clinicapp.repository.AdditonalPhysicianInfoRepository;
import com.sourcery.clinicapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

//    private final OccupationRepository occupationRepository;
    private final AdditonalPhysicianInfoRepository additonalPhysicianInfoRepository;

    public void createPatient(User user){
        User newUser = user.toBuilder().id(UUID.randomUUID()).type("patient").build();
        userRepository.insertUser(newUser);
    }

//    public void addPhysician(Physician user) {
//
//        User newUser = user.toBuilder().id(UUID.randomUUID()).type("physician").build();
//        userRepository.insertUsers(newUser);
//
//        AdditionalPhysicianInfo info = AdditionalPhysicianInfo.builder()
//                .occupationId(user.getOccupation().getId())
//                .userId(user.getId()).build();
//        additonalPhysicianInfoRepository.setPhysicianInfo()
//    }

    public List<User> getAllUsers(){
        return userRepository.getAllUser();
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

    public UUID CheckLog(Login user){
      return userRepository.CheckLogIn(user);
    }

    public void createPhysician(PhysicianDto physicianDto) {
        User user = User.builder()
                .id(UUID.randomUUID())
                .name(physicianDto.getName())
                .email(physicianDto.getEmail())
                .password(physicianDto.getPassword())
                .type("physician")
                .build();
        userRepository.insertUser(user);

        AdditionalPhysicianInfo info = AdditionalPhysicianInfo.builder()
                .userId(user.getId())
                .occupationId(physicianDto.getOccupationId())
                .build();
        additonalPhysicianInfoRepository.insertInfo(info);

//        return userRepository.getPhysician(user.getId()).orElseThrow();
    }

    public List<Physician>getAllPhysiciansWithAdditionalInfo(){
        return userRepository.getAllPhysicians();
    }

    public Physician getPhysicianById(UUID id){
        return userRepository.getPhysician(id).orElseThrow();
    }
}
