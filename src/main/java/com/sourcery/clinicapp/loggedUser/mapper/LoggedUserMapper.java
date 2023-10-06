package com.sourcery.clinicapp.loggedUser.mapper;

import com.sourcery.clinicapp.loggedUser.model.LoggedUser;
import com.sourcery.clinicapp.occupation.model.Occupation;
import com.sourcery.clinicapp.patientInfo.model.PatientInfo;
import com.sourcery.clinicapp.timeslot.model.dto.AppointmentDTO;
import org.apache.ibatis.annotations.*;

import java.util.Collection;
import java.util.UUID;

@Mapper
public interface LoggedUserMapper {

    @ResultType(Occupation.class)
    @Select("SELECT * FROM occupations WHERE id=#{id} ")
    Occupation getOccupation(@Param("id") UUID occupation_id);

    @Select("SELECT user_id as userId, gender, birth_date as birthDate, phone, street, city, " +
            "postal_code as postalCode, country, emergency_name as emergencyName, " +
            "emergency_last_name as emergencyLastName, emergency_phone as emergencyPhone, " +
            "emergency_relation as emergencyRelation FROM additional_patient_info " +
            "WHERE user_id=#{id}")
    PatientInfo getPatientInfo(@Param("id") UUID id);

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

    @Select("SELECT id FROM users WHERE email = #{email}")
    UUID getId(String email);
}
