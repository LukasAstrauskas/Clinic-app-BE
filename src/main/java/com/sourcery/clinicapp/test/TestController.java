package com.sourcery.clinicapp.test;

import com.sourcery.clinicapp.physicianInfo.model.Physician;
import com.sourcery.clinicapp.physicianInfo.model.PhysicianTest;
import com.sourcery.clinicapp.user.mapper.UserMapper;
import com.sourcery.clinicapp.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private UserMapper userMapper;

    private final UUID uuid = UUID.fromString("c4d695d2-cc18-11ed-afa1-0242ac120002");

    @GetMapping("t1")
    public Physician resultMapTest() {

        Optional<Physician> patrick = userMapper.getPhysician(uuid);
        return patrick.orElseThrow();
    }

    @GetMapping("t2")
    public PhysicianTest resultMapTest2() {
        Optional<PhysicianTest> patrick2 = userMapper.getPhysicianTest(uuid);
        return patrick2.orElseThrow();
    }

}
