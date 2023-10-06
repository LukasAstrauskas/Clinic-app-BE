package com.sourcery.clinicapp.timeslot.mapper;

import com.sourcery.clinicapp.timeslot.model.Timeslot;
import com.sourcery.clinicapp.timeslot.model.dto.AppointmentDTO;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface TimeslotMapper {

    @Select("SELECT * FROM timeslot")
    Collection<Timeslot> getAltTimeslots();

    @Select("SELECT * FROM timeslot WHERE physicianid = #{physicianId} AND date BETWEEN #{begin} AND #{end}")
    Collection<Timeslot> getPhysicianTimeslots(@Param("physicianId") UUID physicianId,
                                               @Param("begin") LocalDateTime begin,
                                               @Param("end") LocalDateTime end);

    @Select("SELECT * FROM timeslot WHERE physicianid = #{physicianId} AND date BETWEEN #{begin} AND #{end} ORDER BY date ASC")
    Collection<Timeslot> getMonthsTimeslots(@Param("physicianId") UUID physicianId,
                                            @Param("begin") LocalDateTime begin,
                                            @Param("end") LocalDateTime end);

    @Select("SELECT * FROM timeslot WHERE physicianid = #{physicianId} AND date = #{dateTime}")
    Optional<Timeslot> getTimeslot(UUID physicianId, LocalDateTime dateTime);

    @Select("SELECT COUNT(*) FROM timeslot WHERE patientid = #{patientId} AND physicianid = #{physicianId} AND date >= now()")
    Short countUpcomingTimeslotsWithPhysician(@Param("physicianId") UUID physicianId, @Param("patientId") UUID patientId);

    @Select("SELECT timeslot.id, date, users.name as name, occupations.name as occupation FROM TIMESLOT " +
            "LEFT JOIN Users ON timeslot.physician_id = users.id " +
            "LEFT JOIN Occupations ON users.occupation_id = occupations.id " +
            "WHERE patient_id = #{id} and date > CURRENT_TIMESTAMP ORDER BY date asc")
    Collection<AppointmentDTO> getPatientUpcomingAppointments(@Param("id") UUID patientID);

    @Select("SELECT timeslot.id, date, users.name, users.surname, occupations.name as occupation FROM timeslot" +
            " LEFT JOIN users ON timeslot.physician_id = users.id" +
            " LEFT JOIN occupations ON users.occupation_id = occupations.id" +
            " WHERE patient_id = #{id} and date < CURRENT_TIMESTAMP ORDER BY date desc limit 5 offset #{offset}")
    Collection<AppointmentDTO> getPatientPastAppointments(@Param("id") UUID patientID, @Param("offset") int offset);

    @Select("SELECT COUNT(*) FROM timeslot WHERE patient_id =#{patientID} AND date < CURRENT_TIMESTAMP")
    int getPastAppointmentAmount(@Param("patientID") UUID patientID);

    @Insert("INSERT INTO timeslot (physicianId, date) VALUES(#{physicianId}, #{date})")
    boolean addTimeslot(Timeslot timeslot);

    @Update("UPDATE timeslot SET patientid = #{patientId}" + " WHERE physicianid = #{physicianId} AND date = #{date} AND patientid IS NULL")
    boolean updateTimeslotSetPatientID(Timeslot timeslot);

    @Update("UPDATE timeslot SET patientid = NULL WHERE physicianid = #{physicianId} AND patientid = #{patientId} AND date >= now()")
    boolean removePatientFromUpcomingTimeslot(@Param("physicianId") UUID physicianId, @Param("patientId") UUID patientId);

    @Update("UPDATE timeslot SET patientid = NULL " + " WHERE physicianid = #{physicianId} AND date = #{date} AND patientid = #{patientId}")
    boolean removePatientFromTimeslot(Timeslot timeslot);

    @Delete("DELETE FROM timeslot WHERE physicianid = #{physicianId} AND date=#{date} AND patientid IS NULL")
    boolean deleteTimeslot(Timeslot timeslot);


}