package com.sourcery.clinicapp.user.repository;

import com.sourcery.clinicapp.login.model.Login;
import com.sourcery.clinicapp.physician.model.Physician;
import com.sourcery.clinicapp.user.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Mapper
public interface UserRepository extends JpaRepository<User, UUID> {

    @Select("SELECT * FROM users WHERE type='patient'")
    List<User> getPatients();

    @Select("SELECT * FROM users WHERE type='physician'")
    List<User> getPhysicians();

    @Select("SELECT * FROM users WHERE type='admin'")
    List<User> getAdmins();

    @Select("SELECT * FROM users WHERE email=#{user.email} AND password=#{user.password} ")
    UUID CheckLogIn(@Param("user") Login user);

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
