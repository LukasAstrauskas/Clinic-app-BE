package com.sourcery.clinicapp.generateData;

import com.sourcery.clinicapp.user.model.User;
import com.sourcery.clinicapp.user.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("generate")
public class DataController {

    @Autowired
    private DataService dataService;

    @GetMapping("/user")
    public List<User> generateData() {
        return dataService.generateData();
    }

    @GetMapping("report")
    public String reportData() {
        return dataService.reportData();
    }

    @GetMapping("test")
    public User dataTest(@RequestParam String email) {
        return dataService.useRandom(email);
    }

    @GetMapping("exits")
    public String exits(@RequestParam String email) {
        return dataService.existsByEmail(email);
    }

    @GetMapping("delete")
    public void deleteData() {
        dataService.deleteData();
    }
}
