package com.sourcery.clinicapp.user.mapper;

import com.sourcery.clinicapp.user.model.Physician;
import com.sourcery.clinicapp.user.model.User;
import com.sourcery.clinicapp.user.model.UserDTO;
import org.apache.ibatis.annotations.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface UserMapper {

    @Select("SELECT COUNT(*) FROM USERS WHERE TYPE = #{type}")
    int getUserCount(String type);

    @Select("SELECT users.id as id, users.name as name, surname, email, type, occupations.name as occupation" +
            "  FROM users LEFT JOIN occupations ON occupation_id = occupations.id WHERE TYPE = #{userType} LIMIT 5 OFFSET #{offset}")
    Collection<UserDTO> getUsers(int offset, String userType);

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


    @Select(" SELECT DISTINCT users.id, users.name, users.surname, users.email, users.type FROM timeslot " +
            "LEFT JOIN users ON users.id = timeslot.patient_id WHERE timeslot.physician_id = #{physicianId}" +
            " ORDER BY surname LIMIT 7 OFFSET #{offset}")
    List<UserDTO> getPhysicianPatients(@Param("physicianId") UUID physicianId, @Param("offset") int offset);

    @Select("SELECT COUNT(DISTINCT patient_id ) FROM timeslot WHERE physician_id = #{physicianId}")
    int amountOfPhysicianPatients(@Param("physicianId") UUID physicianId);

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
