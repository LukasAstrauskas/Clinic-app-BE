package com.sourcery.clinicapp.user.mapper;

import com.sourcery.clinicapp.login.model.Login;
import com.sourcery.clinicapp.login.model.LoginDto;
import com.sourcery.clinicapp.physicianInfo.model.Physician;
import com.sourcery.clinicapp.user.model.User;
import com.sourcery.clinicapp.user.model.UserDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users WHERE( LOWER(name) LIKE '%${search}%' OR LOWER(email) LIKE '%${search}%' )AND type='patient' ORDER BY name")
    List<UserDTO> getPatientSearch(@Param("search") String search);

    @Select("SELECT * FROM users WHERE( LOWER(name) LIKE '%${search}%' OR LOWER(email) LIKE '%${search}%' )AND type='admin' ORDER BY name")
    List<UserDTO> getAdminSearch(@Param("search") String search);

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
    List<UserDTO> getPatients();

    @Select("""
            SELECT DISTINCT u.id, u.name, u.email
            FROM users u
            INNER JOIN timeslot t ON u.id = t.patientId
            WHERE t.physicianId = #{physicianId}
            ORDER BY name
            LIMIT 7 OFFSET #{offset}
            """)
    List<UserDTO> getPatientsByPhysicianId(@Param("physicianId") UUID physicianId, @Param("offset") int offset);

    @Select("""
            SELECT COUNT(*)
            FROM users u
            INNER JOIN timeslot t ON u.id = t.patientId
            WHERE t.physicianId = #{physicianId}
            """)
    Short getPatientsByPhysicianIdAmount(@Param("physicianId") UUID physicianId);

    @Select("SELECT * FROM users WHERE type='admin' ORDER BY name LIMIT 7 ")
    List<UserDTO> getAdmins();

    @ResultMap("PhysicianResultMap")
    @Select("""
            SELECT u.id id, u.name name, u.email email, o.id occupation_id, o.name occupation_name
            FROM users u
                LEFT JOIN additional_physician_info i
                    ON u.id = i.user_id
                LEFT JOIN occupations o
                    ON i.occupation_id = o.id
                WHERE type='physician'
                 ORDER BY name LIMIT 12
            """)
    List<Physician> getPhysicians();

    @Select("SELECT * FROM users WHERE type='patient' ORDER BY name LIMIT 5 OFFSET #{offset}")
    List<UserDTO> getLimitedPatients(@Param("offset") Number offset);

    @Select("SELECT * FROM users WHERE type='admin' ORDER BY name LIMIT 5 OFFSET #{offset}")
    List<UserDTO> getLimitedAdmins(@Param("offset") Number offset);

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

    @Select("SELECT COUNT(*) FROM USERS WHERE type='patient'")
    int getPatientCount();

    @Select("SELECT COUNT(*) FROM USERS WHERE type='physician'")
    int getPhysicianCount();

    @Select("SELECT COUNT(*) FROM USERS WHERE type='admin'")
    int getAdminCount();

    @Select("SELECT COUNT(*) FROM USERS WHERE TYPE = #{type}")
    int getUserCount( String type);

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

    @Delete("DELETE FROM users WHERE id=#{uuid}")
    boolean deleteUserById(@Param("uuid") UUID uuid);

    @Select("SELECT * FROM users WHERE id=#{id}")
    Optional<UserDTO> getUserById(@Param("id") UUID id);

    @Insert("INSERT INTO users (id, name, email, password, type) VALUES" +
            " (#{user.id}, #{user.name}, #{user.email}, #{user.password}, #{user.type})")
    boolean saveUser(@Param("user") User user);

    @Update("UPDATE users SET name=#{user.name}, email=#{user.email} WHERE id=#{uuid}")
    void updateUserById(@Param("user") User user, @Param("uuid") UUID id);

    @Update("UPDATE users SET password=#{password} WHERE id=#{id} ")
    void updatePassword(@Param("password") String password, @Param("id") UUID id);

    @Select("SELECT * FROM users WHERE email=#{email}")
    Optional<User> findByEmail(@Param("email") String email);


}
