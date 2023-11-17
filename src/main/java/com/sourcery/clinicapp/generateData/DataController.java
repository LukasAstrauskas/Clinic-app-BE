package com.sourcery.clinicapp.generateData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("generate")
public class DataController {

    @Autowired
    private DataService dataService;

    @GetMapping("/user")
    public int generateData(@RequestParam(required = false, defaultValue = "0") int count) {
        return dataService.generateData(count);
    }
}
