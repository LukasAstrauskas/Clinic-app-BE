package com.sourcery.clinicapp.generateData;

import com.sourcery.clinicapp.occupation.model.Occupation;
import com.sourcery.clinicapp.occupation.repository.OccupationMapper;
import com.sourcery.clinicapp.patientInfo.model.PatientInfo;
import com.sourcery.clinicapp.user.mapper.UserMapper;
import com.sourcery.clinicapp.user.model.Type;
import com.sourcery.clinicapp.user.model.User;
import com.sourcery.clinicapp.user.model.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataService {
    private final Faker faker = new Faker();

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OccupationMapper occupationMapper;

    //    private String name;
//    private String surname;
//    private String email;
    private final String admin = Type.ADMIN.type();
    private final String patient = Type.PATIENT.type();
    private final String physician = Type.PHYSICIAN.type();

    private final Random random = new Random();


    private final String[] emailEndings =
            {"@gmail.com", "@yahoo.com", "@hotmail.com", "@aol.com", "@msn.com", "@outlook.com"};

    private final String[] marks = {".", "-", "_", ":", ""};

    public List<User> generateData() {
        ArrayList<Occupation> allOccupations =
                new ArrayList<>(occupationMapper.getAllOccupations());
        Set<User> userSet = new HashSet<>();
        Set<String> funnyNames = generateArray();
        for (String funnyName : funnyNames) {
            String[] names = generateNameAndSurname(funnyName);
            String name = names[0];
            String surname = names[1];
            String email = generateEmail(name, surname);
            User user = User.builder()
                    .id(UUID.randomUUID().toString())
                    .name(name)
                    .surname(surname)
                    .email(email)
                    .password(names[1].toLowerCase())
                    .build();
            userSet.add(user);
        }
        ArrayList<User> list = new ArrayList<>(userSet
                .stream()
//                .sorted(Comparator.comparing(User::getSurname))
                .toList());
        int i = 0;
        for (; i < 30; i++) {
            int rndInt = random.nextInt(allOccupations.size());
            String occupationID = allOccupations.get(rndInt).getId();
            User user = list.get(i);
            user.setType(physician);
            user.setOccupationId(occupationID);
            list.set(i, user);
        }
        for (; i < 750; i++) {
            User user = list.get(i);
            user.setType(patient);
            list.set(i, user);
            PatientInfo patientInfo = PatientInfo.builder()
                    .userId(user.getId())
                    .build();
        }

        list.forEach(user -> {

//            boolean exists = userMapper.existsByEmail(user.getEmail());
//            if (exists) {
//                System.out.println(user.getName() + " " + user.getSurname() + " is already in DB.");
//            } else {
//                userMapper.insertUser(user);
//            }


        });

        System.out.println("Length: " + list.size());
        return list;
    }

    public User useRandom(String email) {
        System.out.println(userMapper.existsByEmail(email));
        Optional<User> byEmail = userMapper.findByEmail(email);
        return byEmail.orElse(new User());
    }

    public String existsByEmail(String email) {
        return userMapper.existsByEmail(email) ? "TRUE - exists." : "Not found.";
    }


    public String[] generateNameAndSurname(String nameToSplit) {
        String[] names = new String[2];
        String[] arr = nameToSplit.split(" ");
        if (arr.length > 2) {
            names[0] = arr[0].concat(" ").concat(arr[1]);
            names[1] = arr[2];
        } else {
            names[0] = arr[0];
            names[1] = arr[1];
        }
        return names;
    }

    public String generateEmail(String name, String surname) {
        String firstLetter = name.substring(0, 1).toLowerCase();
        String connect = marks[random.nextInt(marks.length)];
        String base = surname.toLowerCase();
        String ending = emailEndings[random.nextInt(emailEndings.length)];
        return firstLetter.concat(connect).concat(base).concat(ending);
    }


    public Set<String> generateArray() {
        Set<String> set = new HashSet<>();
        do {
            set.add(faker.funnyName().name());
        } while (set.size() != 750);
        return set;
    }

    public String reportData() {
        int patientCount = userMapper.getUserCount(patient);
        int physicianCount = userMapper.getUserCount(physician);
        int adminCount = userMapper.getUserCount(admin);

        return String.format("Patients: %s, physicians: %s, admins: %s.",
                patientCount, physicianCount, adminCount);
    }

    public void deleteData(){
        System.out.println(userMapper.deletePatients() ? "Deleted patients" : "some fail");
        System.out.println(userMapper.deletePhysicians() ? "Deleted physicians" : "some fail");
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    class FullName {
        private String name;
        private String surname;
        private String email;
    }
}

