package com.sourcery.clinicapp.user.repository;

import com.sourcery.clinicapp.login.model.Login;
import com.sourcery.clinicapp.login.model.LoginDto;
import com.sourcery.clinicapp.physician.model.Physician;
import com.sourcery.clinicapp.physician.model.PhysicianDto;
import com.sourcery.clinicapp.user.model.User;
import com.sourcery.clinicapp.user.model.UserDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Mapper
public interface UserRepository {


    @Select("SELECT * FROM users WHERE( LOWER(name) LIKE '%${search}%' OR LOWER(email) LIKE '%${search}%' )AND type='patient' ORDER BY name")
    List<User> getPatientSearch(@Param("search") String search);

    @Select("SELECT * FROM users WHERE( LOWER(name) LIKE '%${search}%' OR LOWER(email) LIKE '%${search}%' )AND type='admin' ORDER BY name")
    List<User> getAdminSearch(@Param("search") String search);




   @ResultMap("PhysicianResultMap")
   @Select("""
    SELECT u.id id, u.name name, u.email email, o.id occupation_id, o.name occupation_name
    FROM users u
    LEFT JOIN additional_physician_info i ON u.id = i.user_id
    LEFT JOIN occupations o ON i.occupation_id = o.id
    WHERE type = 'physician'
    AND (
        #{search} IS NULL OR 
        #{search} = '' OR 
        (
            LOWER(u.name) LIKE CONCAT('%', #{search}, '%') OR 
            LOWER(o.name) LIKE CONCAT('%', #{search}, '%')
        )
    ) 
    AND (#{occupation} IS NULL OR #{occupation} = '' OR LOWER(o.name) LIKE CONCAT('%', #{occupation}, '%'))
    ORDER BY name
""")
   List<Physician> getPhysicianSearch(@Param("search") String search, @Param("occupation") String occupation);



    @Select("SELECT * FROM users WHERE type='patient' ORDER BY name LIMIT 7")
     List<User> getPatients();

    @Select("""
               SELECT DISTINCT u.id, u.name, u.email
               FROM users u
               INNER JOIN timeslot t ON u.id = t.patientId
               WHERE t.physicianId = #{physicianId} LIMIT 7 OFFSET #{offset}
               """)
    List<UserDTO> getPatientsByPhysicianId(@Param("physicianId") UUID physicianId, @Param("offset") Number offset);

    @Select("""
               SELECT COUNT(*)
               FROM users u
               INNER JOIN timeslot t ON u.id = t.patientId
               WHERE t.physicianId = #{physicianId}
               """)
    Short getPatientsByPhysicianIdAmount(@Param("physicianId") UUID physicianId);

    @Select("SELECT * FROM users WHERE type='admin' ORDER BY name LIMIT 7 ")
    List<User> getAdmins();

    @ResultMap("PhysicianResultMap")
    @Select("""
            SELECT u.id id, u.name name, u.email email, o.id occupation_id, o.name occupation_name
            FROM users u
                LEFT JOIN additional_physician_info i
                    ON u.id = i.user_id
                LEFT JOIN occupations o
                    ON i.occupation_id = o.id
                WHERE type='physician'
                 ORDER BY name LIMIT 7
            """)
    List<Physician> getPhysicians();

    @Select("SELECT * FROM users WHERE type='patient' ORDER BY name LIMIT 5 OFFSET #{offset}")
    List<User> getLimitedPatients(@Param("offset") Number offset);

    @Select("SELECT * FROM users WHERE type='admin' ORDER BY name LIMIT 5 OFFSET #{offset}")
    List<User> getLimitedAdmins(@Param("offset") Number offset);

    @ResultMap("PhysicianResultMap")
    @Select("""
            SELECT u.id id, u.name name, u.email email, o.id occupation_id, o.name occupation_name
            FROM users u
                LEFT JOIN additional_physician_info i
                    ON u.id = i.user_id
                LEFT JOIN occupations o
                    ON i.occupation_id = o.id
                WHERE type='physician'
                ORDER BY name LIMIT 5 OFFSET #{offset}
            """)
    List<Physician> getLimitedPhysicians(@Param("offset") Number offset);


    @Select("SELECT COUNT(*) FROM users WHERE type='patient' ")
    Long getAmountOfPatients();

    @Select("SELECT COUNT(*) FROM users WHERE type='admin' ")
    Long getAmountOfAdmins();

    @Select("SELECT COUNT(*) FROM users WHERE type='physician' ")
    Long getAmountOfPhysicians();

    @Select("SELECT * FROM users WHERE type='physician'")
    List<User> getPhysiciansType();


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

    @Select("SELECT * FROM users")
    List<User> getUsers();

    @Delete("DELETE FROM users WHERE id=#{uuid} AND type='patient'")
    void deletePatientById(@Param("uuid") UUID uuid);

    @Delete("DELETE FROM users WHERE id=#{uuid} AND type='admin'")
    void deleteAdminById(@Param("uuid") UUID uuid);

    @Select("SELECT * FROM users WHERE id=#{id}")
    User findById(@Param("id") UUID id);

    @Insert("INSERT INTO users (id, name, email, password, type) VALUES (#{user.id}, #{user.name}, #{user.email}, #{user.password}, #{user.type})")
    void save(@Param("user") User user);

    @Update("UPDATE users SET name=#{user.name}, email=#{user.email}, password=#{user.password}, type=#{user.type} WHERE id=#{uuid}")
    void updateUserById(@Param("user") User user, @Param("uuid") UUID id);

    @Update("UPDATE users SET name=#{user.name}, email=#{user.email}, password=#{user.password} WHERE id=#{id} ")
    void updatePhysicianDtoUserById(@Param("user") PhysicianDto user, @Param("id") UUID id);

}
