package com.sourcery.clinicapp.user.repository;

import com.sourcery.clinicapp.login.model.Login;
import com.sourcery.clinicapp.login.model.LoginDto;
import com.sourcery.clinicapp.physician.model.Physician;
import com.sourcery.clinicapp.user.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Mapper
public interface UserRepository {

    @Select("SELECT * FROM users WHERE type='patient'")
    List<User> getPatients();

    @Select("SELECT * FROM users WHERE type='physician'")
    List<User> getPhysicians();

    @Select("SELECT * FROM users WHERE type='admin'")
    List<User> getAdmins();
    @Select("SELECT id, type FROM users WHERE email=#{user.email} AND password=#{user.password} ")
    Optional<LoginDto> checkLogIn(@Param("user") Login user);

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


    @Select("SELECT * FROM users")
    List<User> getUsers();

    @Delete("DELETE FROM users WHERE id=#{uuid} AND type='patient'")
    void deletePatientById(@Param("uuid")UUID uuid);

    @Delete("DELETE FROM users WHERE id=#{uuid} AND type='admin'")
    void deleteAdminById(@Param("uuid") UUID uuid );

    @Select("SELECT * FROM users WHERE id=#{id}")
    User findById(@Param("id") UUID id);

    @Insert("INSERT INTO users (id, name, email, password, type) VALUES (#{user.id}, #{user.name}, #{user.email}, #{user.password}, #{user.type})")
    void save(@Param("user") User user);


}
