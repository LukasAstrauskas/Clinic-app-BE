package com.sourcery.clinicapp.repository;

import com.sourcery.clinicapp.dto.LoginDto;
import com.sourcery.clinicapp.model.Login;
import com.sourcery.clinicapp.model.Physician;
import com.sourcery.clinicapp.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
@Mapper
public interface UserRepository {

    @Insert("INSERT INTO users (id, name, email, password, type) VALUES (#{user.id}, #{user.name}, #{user.email}, #{user.password}, #{user.type})")
    void insertUser(@Param("user") User user);

    @Select("SELECT * FROM users")
    List<User> getAllUser();

    @Select("SELECT * FROM users WHERE type='patient'")
    List<User> getPatients();

    @Select("SELECT * FROM users WHERE type='physician'")
    List<User> getPhysicians();

    @Select("SELECT * FROM users WHERE type='admin'")
    List<User> getAdmins();

//
//    @Select("SELECT email, password, IF (email=#{user.email} AND password=#{user.password} , 'TRUE', 'FALSE ') FROM users")
//    boolean CheckLogIn(@Param("user") Login users);

    @Select("SELECT * FROM users WHERE email=#{user.email} AND password=#{user.password} ")
    UUID CheckLogIn(@Param("user") Login user);

    @Delete("DELETE FROM users WHERE id=#{uuid}")
    void deleteUser(@Param("uuid")UUID uuid);

    @Select("SELECT * FROM users WHERE id=#{id} ")
    Optional<User> getUserById(@Param("id") UUID id);

    @ResultMap("PhysicianResultMap")
    @Select("""
            SELECT u.id id, u.name name, u.email email, o.id occupation_id, o.name occupation_name
            FROM users u
                LEFT JOIN additional_physician_info i
                    ON u.id = i.user_id
                LEFT JOIN occupations o
                    ON i.occupation_id = o.id
                WHERE type='physician' AND u.id=#{id}
            """)
    Optional<Physician> getPhysician(UUID id);


    @ResultMap("PhysicianResultMap")
    @Select("""
            SELECT u.id id, u.name name, u.email email, o.id occupation_id, o.name occupation_name
            FROM users u
                LEFT JOIN additional_physician_info i
                    ON u.id = i.user_id
                LEFT JOIN occupations o
                    ON i.occupation_id = o.id
                WHERE type='physician'
            """)
    List<Physician> getAllPhysicians();








}
