package com.sourcery.clinicapp.generateData;

import net.datafaker.Faker;
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
    public int generateData(@RequestParam(required = false, defaultValue = "0") int addCount) {
//        Faker faker = new Faker();
//
//        for (int i = 0; i < count; i++) {
//            String characters = faker.f
////            String quotes = faker.rickAndMorty().quote();
////            String format = String.format("Hey Arnold: %s. Qoutes: %s", characters, quotes);
//            String format = String.format("Hey Arnold: %s.", characters);
//            System.out.println(format);
//        }

        return dataService.generateData(addCount);

    }
}
