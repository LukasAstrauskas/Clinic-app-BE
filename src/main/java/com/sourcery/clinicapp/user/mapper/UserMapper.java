package com.sourcery.clinicapp.user.mapper;

import com.sourcery.clinicapp.login.model.Login;
import com.sourcery.clinicapp.login.model.LoginDto;
import com.sourcery.clinicapp.occupation.model.Occupation;
import com.sourcery.clinicapp.patientInfo.model.AdditionalPatientInfo;
import com.sourcery.clinicapp.physicianInfo.model.Physician;
import com.sourcery.clinicapp.timeslot.model.dto.AppointmentDTO;
import com.sourcery.clinicapp.user.model.LoggedUser;
import com.sourcery.clinicapp.user.model.User;
import com.sourcery.clinicapp.user.model.UserDTO;
import org.apache.ibatis.annotations.*;

import java.util.Collection;
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

    @ResultType(Occupation.class)
    @Select("SELECT * FROM occupations WHERE id=#{id} ")
    Occupation getOccupation(@Param("id") UUID occupation_id);

    @Select("SELECT user_id as userId, gender, birth_date as birthDate, phone, street, city, " +
            "postal_code as postalCode, country, emergency_name as emergencyName, " +
            "emergency_last_name as emergencyLastName, emergency_phone as emergencyPhone, " +
            "emergency_relation as emergencyRelation FROM additional_patient_info " +
            "WHERE user_id=#{id}")
    AdditionalPatientInfo getPatientInfo(@Param("id") UUID id);

    @Select("SELECT timeslot.id, date, users.name, users.surname, occupations.name as occupation FROM TIMESLOT " +
            "LEFT JOIN Users ON timeslot.physician_id = users.id " +
            "LEFT JOIN Occupations ON users.occupation_id = occupations.id " +
            "WHERE patient_id = #{id} and date > CURRENT_TIMESTAMP ORDER BY date asc")
    Collection<AppointmentDTO> getUpcomingAppointments(@Param("id") UUID patientID);

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "surname", column = "surname"),
            @Result(property = "email", column = "email"),
            @Result(property = "type", column = "type"),
            @Result(property = "occupation", column = "occupation_id", one = @One(select = "getOccupation")),
            @Result(property = "patientInfo", column = "id", one = @One(select = "getPatientInfo")),
            @Result(property = "upcomingAppointment", column = "id", many = @Many(select = "getUpcomingAppointments"))
    })
    @Select("SELECT id, name, surname, email, type, occupation_id FROM users WHERE email=#{email}")
    LoggedUser getLoggedUser(String email);
}
