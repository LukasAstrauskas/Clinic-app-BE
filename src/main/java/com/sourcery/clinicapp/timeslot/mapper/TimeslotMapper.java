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

    @Results(id = "timeslot", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "physicianId", column = "physician_id"),
            @Result(property = "date", column = "date"),
            @Result(property = "patientId", column = "patient_id")
    })
    @Select("SELECT * FROM timeslot")
    Collection<Timeslot> getAltTimeslots();

    @ResultMap("timeslot")
    @Select("SELECT * FROM timeslot WHERE physician_id = #{physicianId} AND date BETWEEN #{begin} AND #{end} ORDER BY date ASC")
    Collection<Timeslot> getMonthsTimeslots(@Param("physicianId") UUID physicianId,
                                            @Param("begin") LocalDateTime begin,
                                            @Param("end") LocalDateTime end);

    @ResultMap("timeslot")
    @Select("SELECT * FROM timeslot WHERE id = #{timeslotId}")
    Optional<Timeslot> getTimeslot(UUID timeslotId);

    @Select("SELECT COUNT(*) FROM timeslot WHERE patient_id = #{patientId} AND physician_id = #{physicianId} AND date >= now()")
    int countUpcomingTimeslotsWithPhysician(@Param("physicianId") UUID physicianId, @Param("patientId") UUID patientId);

    @Select("SELECT timeslot.id, date, users.name as name, users.surname, occupations.name as occupation FROM TIMESLOT " +
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

    @Insert("INSERT INTO timeslot (id, physician_id, date) VALUES(#{id}, #{physicianId}, #{date})")
    boolean addTimeslot(Timeslot timeslot);

    @Update("UPDATE timeslot SET patient_id = #{patientId} WHERE id = #{timeslotId} AND patient_id IS NULL")
    boolean updateTimeslotSetPatientID(UUID timeslotId, UUID patientId);

    @Update("UPDATE timeslot SET patientid = NULL WHERE id = #{id} AND date >= now()")
    boolean cancelAppointment(UUID id);


    @Delete("DELETE FROM timeslot WHERE id = #{id} AND patient_id IS NULL")
    boolean deleteTimeslot(UUID id);


}